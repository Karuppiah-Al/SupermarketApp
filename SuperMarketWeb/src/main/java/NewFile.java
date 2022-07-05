

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NewFile
 */
@WebServlet("/NewFile")
public class NewFile extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException,ServletException
    {
		String user = request.getParameter("user");
        String password = request.getParameter("password");

        System.out.println("---->" + user);
        System.out.println("---->" + password);

		response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("fa");
    }

}
