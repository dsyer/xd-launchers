package dirt;

import javax.servlet.Servlet;

import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.xd.dirt.container.XDContainer;

@Configuration
@Import({ EmbeddedServletContainerAutoConfiguration.class,
		PropertyPlaceholderAutoConfiguration.class })
@ImportResource("classpath:" + XDContainer.XD_INTERNAL_CONFIG_ROOT
		+ "xd-global-beans.xml")
public class AdminServerApplication implements ApplicationContextAware, CommandLineRunner {

	private ApplicationContext applicationContext;

	@Override
	public void run(String... args) throws Exception {
		SpringApplication child = new SpringApplication(AdminChildContext.class);
		GenericWebApplicationContext context = new GenericWebApplicationContext();
		GenericWebApplicationContext parent = (GenericWebApplicationContext) applicationContext;
		context.setParent(parent);
		context.setServletConfig(parent.getServletConfig());
		context.setServletContext(parent.getServletContext());
		child.setApplicationContext(context);
		child.run(args);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@Bean
	public Servlet dispatcherServlet() {
		return new DelegatingServlet();
	}

	public static void main(String[] args) {
		SpringApplication.run(AdminServerApplication.class, args);
	}

}
