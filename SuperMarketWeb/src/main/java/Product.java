
	
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import supermarket.*;
@MultipartConfig
@WebServlet("/api/product/*")
public class Product extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		System.out.println(pathInfo);
		if(pathInfo == null) {
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("../products.html");
			requestDispatcher.forward(req, resp);
		}
		else {
			pathInfo = pathInfo.substring(1);
			System.out.println(pathInfo+"else");
			if(pathInfo.equals("customer")) {
				
				if(req.getSession().getAttribute("cId") !=null){
					System.out.println("cid found");
				JSONArray products = SuperMarketStorage.getProducts();
				resp.getWriter().print(products);
				System.out.println(products);
				return;
				}
				else {
					System.out.println("iiii");
//					RequestDispatcher requestDispatcher = req.getRequestDispatcher("customerLogin.html");
//					requestDispatcher.forward(req, resp);
					resp.sendRedirect("customerLogin.html");
					
				}
			}
				
			if(pathInfo.length() == 0) {
				//all Products
				JSONArray products = SuperMarketStorage.getProducts();
				resp.getWriter().print(products);
				System.out.println(products);
			}
			else if(pathInfo.equals("store")) {
				JSONArray products = SuperMarketStorage.getProductsForUpdate();
				resp.getWriter().print(products);
			}
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	String sid = (request.getParameter("supplierId"));
	String cid =( request.getParameter("catId"));
		if(sid!=null && cid != null)
		{
			String productName = request.getParameter("product-name");
			String c = request.getParameter("cost");
			double cost = Double.parseDouble(c);
			String p = request.getParameter("price");
			double price = Double.parseDouble(p);
			String expdate = request.getParameter("expdate");
			String q = request.getParameter("quantity");
			int quantity = Integer.parseInt(q);
			
			JSONObject allResponse = new JSONObject();
			JSONObject respJsonObject = SuperMarketStorage.insertToProducts(productName, cost, price, sid, cid);
			allResponse.put("product", respJsonObject);
			
			LocalDate exDate = null;
			if(expdate != null && expdate.length() != 0) {
				DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				System.out.println(expdate);
				exDate = LocalDate.parse(expdate,df);
			}
			
			JSONObject respJsonObject2 = SuperMarketStorage.insertToStore(LocalDate.now(), exDate, quantity, (String)respJsonObject.get("product_Id"));
			allResponse.put("store", respJsonObject2);
			response.getWriter().print(allResponse);
		}
		
	}
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		PrintWriter out = response.getWriter();
		pathInfo = pathInfo.substring(1);
		if(pathInfo.length()!=0) {
			if(pathInfo.startsWith("update")) {
				System.out.println(pathInfo);
				String storedId = pathInfo.split("/")[1];
				int sid = Integer.parseInt(storedId);
				String q = request.getParameter("quantity");
				if(q== null){
					System.out.println("q null");
					return;
				}
				int quantity = Integer.parseInt(q);
				JSONObject respJsonObject = SuperMarketStorage.updateInStore(sid, quantity);
				out.print(respJsonObject);
				return;
			}
			if(!Validate.isNumber(pathInfo)) {
				out.print("Invalid requets");
			}
			else {
				String id = pathInfo;
				if(Validate.isNumber(id)) {
					
					String sid = (request.getParameter("supplierId"));
					String cid =( request.getParameter("catId"));
						if(sid!=null && cid != null)
						{
							String productName = request.getParameter("product-name");
							String c = request.getParameter("cost");
							double cost = Double.parseDouble(c);
							String p = request.getParameter("price");
							double price = Double.parseDouble(p);
							String expdate = request.getParameter("expdate");
							String addedDate = request.getParameter("addDate");
							String q = request.getParameter("quantity");
							int quantity = Integer.parseInt(q);
							
							JSONObject allResponse = new JSONObject();
							JSONObject respJsonObject = SuperMarketStorage.updateProducts(id,productName, cost, price,sid,cid);
							allResponse.put("product", respJsonObject);
							response.getWriter().print(allResponse);
					}
				}
			}
		}
	}
}
