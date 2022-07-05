

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import supermarket.Validate;
import supermarket.SuperMarketStorage;

@MultipartConfig
@WebServlet("/api/supplier/*")
public class Supplier extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if(pathInfo == null) {
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("../supplier.html");
			requestDispatcher.forward(req, resp);
		}
		else {
			pathInfo = pathInfo.substring(1);
			
			if(Validate.isNumber(pathInfo)){
				resp.getWriter().print(SuperMarketStorage.getSupplier(pathInfo));
				return;
			}
		}
	}
		
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String categoryId = req.getParameter("catId");
		JSONObject jsonResponse;
		if(categoryId!= null ) {
			String mail = req.getParameter("mail");
			
			String supplierName = req.getParameter("name");
			String website = req.getParameter("website");
			jsonResponse =  SuperMarketStorage.insertToSuppliers(mail, supplierName, website, categoryId);
			if(jsonResponse.get("status") != null) {
				out.print(jsonResponse.get("status"));
			}
			else {
				out.print(jsonResponse.get("reason"));
			}
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String pathInfo = req.getPathInfo().substring(1);
		PrintWriter out = resp.getWriter();
		out.print(pathInfo);

		if(pathInfo.length()!=0) {
			if(!Validate.isNumber(pathInfo)) {
				out.print("Invalid requets");
			}
			else {
				String id = pathInfo;
				String mail = req.getParameter("mail");
				String supplierName = req.getParameter("name");
				String website = req.getParameter("website");
				String categoryId = req.getParameter("catId");
				
				JSONObject jsonResponse = SuperMarketStorage.updateSuppliers(id, mail, supplierName, website, categoryId);
				out.println(jsonResponse);
			}
		}
	}

}
