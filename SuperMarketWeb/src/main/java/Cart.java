
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

import supermarket.SuperMarketStorage;

/**
 * Servlet implementation class Cart
 */
@MultipartConfig
@WebServlet("/api/cart/*")
public class Cart extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		System.out.println(pathInfo);
		PrintWriter out  = response.getWriter();
		if(request.getSession().getAttribute("cId") == null) {
			return;
		}
		String id = (String)request.getSession().getAttribute("cId");
		int CustId = Integer.parseInt(id);
		{
			
			// get Cart details for this customer
			JSONArray cartData = SuperMarketStorage.getCart(CustId);
			System.out.println("CART "+cartData);
			out.print(cartData);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String custId = (String)request.getSession().getAttribute("cId");
		String products = request.getParameter("myProducts");
		PrintWriter out = response.getWriter();
		String pathInfo = request.getPathInfo();
		System.out.println("path"+pathInfo);
		if(custId != null && pathInfo!=null) {
			System.out.println("in uupdate1");
			if(pathInfo.substring(1).startsWith("update")) {
				System.out.println("in update2");
				int q = Integer.parseInt(request.getParameter("quantity"));
				int itemId = Integer.parseInt(request.getParameter("cartItemId"));
				if(!SuperMarketStorage.updateQuantity(itemId, q)) {
					out.print("limit");
				}
			}
		}
		
		if(custId != null && products != null) {
		
			int cid = Integer.parseInt(custId);
			JSONArray productsArray = new JSONArray(products);
			for(int i = 0;i<productsArray.length();i++) {
				JSONObject jsonObject = productsArray.getJSONObject(i);
				int pid = (jsonObject.getInt("id"));
				int q = jsonObject.getInt("quantity");
				JSONObject responseObject = SuperMarketStorage.addToCart(cid, pid, q);
			}
			System.out.println("ok");
			response.getWriter().print("ok");
		}
		else {
			System.out.println("NULL");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "failed");
			jsonObject.put("info", "parameter null");
			out.print(jsonObject);
		}
	}
}
