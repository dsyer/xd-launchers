package dirt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.xd.dirt.container.XDContainer;

@Configuration
@EnableAutoConfiguration
@ImportResource({"classpath:" + XDContainer.XD_INTERNAL_CONFIG_ROOT + "launcher.xml", "classpath*:" + XDContainer.XD_CONFIG_ROOT + "plugins/*.xml"})
public class LauncherApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(LauncherApplication.class);
		application.setWebEnvironment(false);
		application.setDefaultArgs("--spring.profiles.active=node");
		application.run(args);
	}

}
