

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.json.JSONObject;

import supermarket.*;
/**
 * Servlet implementation class Login
 */
@MultipartConfig
@WebServlet("/login/*")
public class Login extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mobile = req.getParameter("mobile");
		String password = req.getParameter("password");
		if(password == null){
			return;
		}
		
		if(mobile == null ) {
			return;
		}
		String pathInfo = req.getPathInfo();
		if(pathInfo == null) {
			return;
		}
		pathInfo = pathInfo.substring(1);
		if(pathInfo.equals("worker")) {
			
			JSONObject responseJsonObject = Authentication.isWorkerFound(mobile, password);
			if(responseJsonObject.get("status").equals("ok")){
				resp.getWriter().write((String)responseJsonObject.get("status"));
			}
			else{
				resp.getWriter().write((String)responseJsonObject.get("info"));
			}
		}
		else if(pathInfo.equals("customer")) {
			Customer c = Authentication.isCustomerExist(mobile, password);
			JSONObject responseJsonObject = new JSONObject();
			if(c == null) {
				responseJsonObject.put("status", "failed");
				responseJsonObject.put("info", "Account not found");
			}
			else {
				//session
				req.getSession().setAttribute("cId",c.getCustomerId());
				
				responseJsonObject.put("status", "ok");
				responseJsonObject.put("info", "Acoount found");
				responseJsonObject.put("name", c.getName());
			}
			
			if(responseJsonObject.get("status").equals("ok")){
				resp.getWriter().write((String)responseJsonObject.get("status"));
			}
			else{
				resp.getWriter().write((String)responseJsonObject.get("info"));
			}
			
			
		}
	}

}
