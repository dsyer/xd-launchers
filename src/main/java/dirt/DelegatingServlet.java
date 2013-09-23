package dirt;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@SuppressWarnings("serial")
public class DelegatingServlet extends GenericServlet {

	private Servlet delegate;

	public void setDelegate(Servlet delegate) {
		this.delegate = delegate;
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException,
			IOException {
		delegate.service(req, res);
	}

}
