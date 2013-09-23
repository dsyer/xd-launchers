package dirt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.xd.dirt.container.XDContainer;
import org.springframework.xd.dirt.rest.RestConfiguration;

@Configuration
@EnableAutoConfiguration
public class AdminServerApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication("classpath:"
				+ XDContainer.XD_INTERNAL_CONFIG_ROOT + "xd-global-beans.xml");
		application.setWebEnvironment(false);
		SpringApplication child = new SpringApplication(AdminServerApplication.class,
				RestConfiguration.class, "classpath:"
						+ XDContainer.XD_INTERNAL_CONFIG_ROOT + "admin-server.xml");
		GenericApplicationContext parent = (GenericApplicationContext) application
				.run(args);
		AnnotationConfigEmbeddedWebApplicationContext context = new AnnotationConfigEmbeddedWebApplicationContext();
		context.setParent(parent);
		child.setApplicationContext(context);
		child.run(args);
	}

}
