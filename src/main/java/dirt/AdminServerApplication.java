package dirt;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.xd.dirt.container.XDContainer;
import org.springframework.xd.dirt.rest.RestConfiguration;

@Configuration
@EnableAutoConfiguration
@ImportResource("classpath:" + XDContainer.XD_INTERNAL_CONFIG_ROOT + "admin-server.xml")
@Import(RestConfiguration.class)
public class AdminServerApplication {

	public static void main(String[] args) {
		SpringApplicationBuilder application = new SpringApplicationBuilder(
				AdminServerApplication.class).defaultArgs(
				"--spring.profiles.active=adminServer", "--transport=redis");
		application.parent("classpath:" + XDContainer.XD_INTERNAL_CONFIG_ROOT
				+ "xd-global-beans.xml");
		application.run(args);
	}
}
