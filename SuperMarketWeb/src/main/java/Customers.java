

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

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
import supermarket.SuperMarketStorage;
import supermarket.Validate;

/**
 * Servlet implementation class Customer
 */

@MultipartConfig
@WebServlet("/api/Customer/*")
public class Customers extends HttpServlet {
    
	@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String mobile = req.getParameter("mobile");
			String name = req.getParameter("name");
			System.out.println(mobile+name);
			if(name == null || name == "") {
				// check customer Exist
				Customer customer = Authentication.isCustomerExist(mobile);
				if(customer == null) {
					System.out.println("in name null if");
					resp.getWriter().print("new Customer");
					return;
				}
				else {
					JSONObject customerDetails = new JSONObject();
					customerDetails.put("mobile", customer.getMobile());
					customerDetails.put("name",customer.getName() );
					customerDetails.put("id", customer.getCustomerId());
					System.out.println("in name null else");
					resp.getWriter().print(customerDetails);
				}
			}
			else {
				System.out.println("else");
				Customer customer = SuperMarketStorage.addCustomer(mobile, name);
				JSONObject customerDetails = new JSONObject();
				customerDetails.put("mobile", customer.getMobile());
				customerDetails.put("name",customer.getName() );
				customerDetails.put("id", customer.getCustomerId());
				resp.getWriter().print(customerDetails);
			}
		}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		PrintWriter out = response.getWriter();
		if(pathInfo == null) {
			System.out.println("null in customer ");
			return;
		}
		System.out.println(pathInfo);
		String url[] = pathInfo.split("/");
		if(url.length >= 2 && url[1].length() != 0 ) {
			if(url.length==2) {
				// display customer details
				System.out.println("000");
				JSONObject details = SuperMarketStorage.getCustomerDetails(url[1]);
				out.print(details);
			}
			else if(url.length==3 && url[2].equals("bills") ) {
				// display all Matched bills for a customer
				System.out.println(Arrays.toString(url));
				String mobile = url[1].trim();
				int id = SuperMarketStorage.getId(mobile);
				System.out.println(id+"000000000000000000000000000000000000000");
				JSONArray responseArray = SuperMarketStorage.getBillForCustomer(id);
				out.print(responseArray);
			}
			else if(url.length == 4 && url[2].equals("bills")){
				System.out.println("999");
				if(Validate.isNumber(url[3])) {
					// get this bill
					int custid = Integer.parseInt(url[1]);
					int billId = Integer.parseInt(url[3]);
					JSONObject responseObject = SuperMarketStorage.getBillForCustomer(custid, billId);
					out.print(responseObject);
				}
			}
			
		}
	}
}
