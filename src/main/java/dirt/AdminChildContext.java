package dirt;

import org.springframework.beans.factory.HierarchicalBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.xd.dirt.container.XDContainer;

@Configuration
@EnableAutoConfiguration(exclude = { EmbeddedServletContainerAutoConfiguration.class })
@ImportResource("classpath:" + XDContainer.XD_INTERNAL_CONFIG_ROOT + "admin-server.xml")
public class AdminChildContext implements CommandLineRunner {

	@Autowired
	private HierarchicalBeanFactory beanFactory;

	@Autowired
	private DispatcherServlet servlet;

	@Override
	public void run(String... args) throws Exception {
		if (beanFactory.getParentBeanFactory() != null) {
			DelegatingServlet wrapper = beanFactory.getParentBeanFactory().getBean(
					"dispatcherServlet", DelegatingServlet.class);
			wrapper.setDelegate(servlet);
			servlet.init(wrapper.getServletConfig());
		}
	}

}
