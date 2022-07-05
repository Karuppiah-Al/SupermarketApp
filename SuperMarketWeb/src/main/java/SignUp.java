

import java.io.IOException;

import java.io.PrintWriter;

import supermarket.SuperMarketStorage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import supermarket.Authentication;
import supermarket.Customer;
@MultipartConfig
@WebServlet("/signup/*")
public class SignUp extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException  {
		PrintWriter out = resp.getWriter();
		String mobile = req.getParameter("mobile");
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		String pathInfo = req.getPathInfo();
		if(pathInfo != null) {
			pathInfo = pathInfo.substring(1);
			if(pathInfo.equals("worker")) {
				JSONObject workerstatus = Authentication.isWorkerFound(mobile);
				if(workerstatus.get("status").equals("not-found")) {
					JSONObject responseobj =   SuperMarketStorage.addWorkers(mobile, name, password);
					out.write((String)responseobj.get("status"));
				}
				else {
					out.write((String)workerstatus.get("status"));;
				}
			}
			else if(pathInfo.equals("customer")) {
				if(Authentication.isCustomerExist(mobile) == null) {
					Customer c = SuperMarketStorage.addCustomer(mobile, name, password);
					
					req.getSession().setAttribute("cId", c.getCustomerId());
					out.print("Welcome "+c.getName());
				}
				else {
					out.print("Already an user plz login");
				}
			}
		}
		
	}
}
