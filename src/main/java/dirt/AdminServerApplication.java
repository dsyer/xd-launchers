package dirt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.xd.dirt.container.XDContainer;
import org.springframework.xd.dirt.rest.RestConfiguration;

@Configuration
@EnableAutoConfiguration
@ImportResource("classpath:" + XDContainer.XD_INTERNAL_CONFIG_ROOT + "admin-server.xml")
@Import(RestConfiguration.class)
public class AdminServerApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication("classpath:" + XDContainer.XD_INTERNAL_CONFIG_ROOT
				+ "xd-global-beans.xml");
		application.setWebEnvironment(false);
		SpringApplication child = new SpringApplication(AdminServerApplication.class);
		GenericApplicationContext parent = (GenericApplicationContext) application.run(args);
		AnnotationConfigEmbeddedWebApplicationContext context = new AnnotationConfigEmbeddedWebApplicationContext();
		context.setParent(parent);
		child.setApplicationContext(context);
		child.run(args);
	}

}
