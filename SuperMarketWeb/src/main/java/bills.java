

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mysql.cj.xdevapi.JsonArray;

import supermarket.*;
@MultipartConfig
@WebServlet("/api/bills/*")
public class bills extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pathInfo = request.getPathInfo();
		PrintWriter out = response.getWriter();
		if(pathInfo == null || pathInfo.length() == 1) {
			JSONArray allBills = SuperMarketStorage.allBills();
			System.out.println(allBills);
			out.println(allBills);
			return;
		}
		else{
			pathInfo = pathInfo.substring(1);
			if(!Validate.isNumber(pathInfo)) {
				System.out.println(pathInfo);
				out.print("Invalid Id");	
			}
			else {
				// getBill by ID
				int id = Integer.parseInt(pathInfo);
				JSONObject bill =  SuperMarketStorage.getMatchedBill(id+"");
				System.out.println(bill);
				out.println(bill);
			}
		}
//		if(pathInfo.equals("customerid")) {
//			String custId = request.getParameter(pathInfo); 
//			if(!Validate.isNumber(custId)) {
//				out.print("Invalid Id");
//			}
//			else {
//				int id  = Integer.parseInt(custId);
//				JSONArray customerBills = SuperMarketStorage.getBillForCustomer(id);
//				out.print(customerBills);
//			}
//		}
		
	}
}
