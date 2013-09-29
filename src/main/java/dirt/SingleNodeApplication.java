package dirt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.SubscribableChannel;
import org.springframework.integration.handler.BridgeHandler;

@Configuration
@Import(AdminServerApplication.class)
public class SingleNodeApplication implements CommandLineRunner {
	
	@Autowired
	private ApplicationContext admin;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO: maybe move some of this off to a convenience API in Boot? Sibling contexts?
		SpringApplication application = new SpringApplication(LauncherApplication.class, "classpath:/META-INF/spring-xd/internal/container.xml");
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.setParent(admin.getParent());
		application.setApplicationContext(context);
		application.setDefaultArgs("--spring.profiles.active=node", "--management.port=0");
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
		SpringApplication.run(new Object[] { SingleNodeApplication.class },
				new String[] { "--spring.profiles.active=adminServer" }, args);
	}

}
