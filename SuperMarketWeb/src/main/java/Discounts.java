

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.json.JSONObject;

import supermarket.SuperMarketStorage;
import supermarket.Validate;

/**
 * Servlet implementation class Discounts
 */
@MultipartConfig
@WebServlet("/api/discounts/*")
public class Discounts extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if(pathInfo == null) {
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("../discount.html");
			requestDispatcher.forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		if(request.getParameter("discountName") !=null){
			String discountName = request.getParameter("discountName");
			String amountLimit = request.getParameter("amountLimit");
			String discountPercent = request.getParameter("discountPercent");
			double amt = Double.parseDouble(amountLimit);
			double dpercent = Double.parseDouble(discountPercent);
			JSONObject responseJsonObject =  SuperMarketStorage.addToDiscount(discountName, dpercent, amt);
			if(responseJsonObject.get("Error") != null) {
				out.print((String)responseJsonObject.get("Error"));
			}
			else {
				out.print(responseJsonObject.get("status"));
			}
		}
	}
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		String pathinfo = request.getPathInfo();
		PrintWriter out = resp.getWriter();
//		out.print(pathinfo);
		pathinfo = pathinfo.substring(1);
		if(Validate.isNumber(pathinfo)) {
			String discountName = request.getParameter("discountName");
			String amountLimit = request.getParameter("amountLimit");
			String discountPercent = request.getParameter("discountPercent");
			double amt = Double.parseDouble(amountLimit);
			double dpercent = Double.parseDouble(discountPercent);
			JSONObject responseObject = SuperMarketStorage.updateDiscounts(pathinfo, discountName, dpercent, amt);
			out.print(responseObject);
		}
	}

}
