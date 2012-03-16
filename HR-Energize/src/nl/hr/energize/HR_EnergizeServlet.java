package nl.hr.energize;

import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class HR_EnergizeServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		resp.getWriter().println("Werkt bij jordy!");
	}
}
