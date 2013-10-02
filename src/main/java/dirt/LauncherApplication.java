package dirt;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.xd.dirt.container.XDContainer;

@Configuration
@EnableAutoConfiguration
@ImportResource({
		"classpath:" + XDContainer.XD_INTERNAL_CONFIG_ROOT + "launcher.xml",
		"classpath:" + XDContainer.XD_INTERNAL_CONFIG_ROOT + "/container.xml",
		"classpath*:" + XDContainer.XD_CONFIG_ROOT + "plugins/*.xml" })
public class LauncherApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder("classpath:"
				+ XDContainer.XD_INTERNAL_CONFIG_ROOT + "xd-global-beans.xml")
				.profiles("node").defaultArgs("--transport=redis")
				.child(LauncherApplication.class).web(false).run(args);
	}
}
