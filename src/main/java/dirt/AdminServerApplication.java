package dirt;

import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.xd.dirt.container.XDContainer;

@Configuration
@ImportResource("classpath:" + XDContainer.XD_INTERNAL_CONFIG_ROOT
		+ "xd-global-beans.xml")
public class AdminServerApplication implements ApplicationContextAware, CommandLineRunner {

	private ApplicationContext applicationContext;

	@Override
	public void run(String... args) throws Exception {
		SpringApplication child = new SpringApplication(AdminChildContext.class);
		AnnotationConfigEmbeddedWebApplicationContext context = new AnnotationConfigEmbeddedWebApplicationContext();
		GenericApplicationContext parent = (GenericApplicationContext) applicationContext;
		context.setParent(parent);
		child.setApplicationContext(context);
		child.run(args);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(AdminServerApplication.class);
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		application.setApplicationContext(context);
		application.run(args);
	}

}
