package dirt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.web.context.support.StandardServletEnvironment;
import org.springframework.xd.dirt.container.XDContainer;
import org.springframework.xd.dirt.rest.RestConfiguration;

@Configuration
@EnableAutoConfiguration
@ImportResource("classpath:" + XDContainer.XD_INTERNAL_CONFIG_ROOT + "admin-server.xml")
@Import(RestConfiguration.class)
public class AdminServerApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(AdminServerApplication.class);
		ConfigurableEnvironment environment = new StandardServletEnvironment();
		environment.addActiveProfile("adminServer");
		application.setEnvironment(environment );
		application.run(args);
	}

}
