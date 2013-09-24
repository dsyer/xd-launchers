package dirt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.SubscribableChannel;
import org.springframework.integration.handler.BridgeHandler;
import org.springframework.web.context.support.StandardServletEnvironment;

@Configuration
@Import(AdminServerApplication.class)
public class SingleNodeApplication implements CommandLineRunner {
	
	@Autowired
	private ApplicationContext admin;
	
	@Override
	public void run(String... args) throws Exception {
		SpringApplication application = new SpringApplication(LauncherApplication.class, "classpath:/META-INF/spring-xd/internal/container.xml");
		application.setWebEnvironment(false);
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.setParent(admin.getParent());
		application.setApplicationContext(context);
		ConfigurableEnvironment environment = new StandardEnvironment();
		environment.addActiveProfile("node");
		application.setEnvironment(environment);
		setUpControlChannels(admin, application.run(args));
	}

	private void setUpControlChannels(ApplicationContext adminContext, ApplicationContext containerContext) {

		MessageChannel containerControlChannel = containerContext.getBean("containerControlChannel", MessageChannel.class);
		SubscribableChannel deployChannel = adminContext.getBean("deployChannel", SubscribableChannel.class);
		SubscribableChannel undeployChannel = adminContext.getBean("undeployChannel", SubscribableChannel.class);

		BridgeHandler handler = new BridgeHandler();
		handler.setOutputChannel(containerControlChannel);
		handler.setComponentName("xd.local.control.bridge");
		deployChannel.subscribe(handler);
		undeployChannel.subscribe(handler);

	}

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SingleNodeApplication.class);
		ConfigurableEnvironment environment = new StandardServletEnvironment();
		environment.setActiveProfiles("adminServer");
		application.setEnvironment(environment);
		application.run(args);
	}

}
