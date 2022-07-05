

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import supermarket.Bill;
import supermarket.Category;
import supermarket.Product;
//import supermarket.ProductPurchased;
import supermarket.SuperMarketStorage;
import supermarket.ProductPurchased;
@MultipartConfig
@WebServlet("/api/purchase/*")
public class purchase extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getPathInfo() == null) {
			RequestDispatcher req = request.getRequestDispatcher("../purchase.html");
			req.forward(request, response);
		}
		else if(request.getPathInfo().substring(1).startsWith("customer")) {
			System.out.println("calling customer purchase");
			RequestDispatcher req = request.getRequestDispatcher("/customerPurchase.html");
			req.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String myProducts = request.getParameter("myProducts");
		HashMap<Product, Integer> choosedProducts = new HashMap<Product, Integer>();
		String custid="";
		if(myProducts != null) {
			
			 custid = request.getParameter("id");
			JSONArray jsonProducts = new JSONArray(myProducts);
			System.out.println(jsonProducts);
			System.out.println(myProducts);
			
			for(int i = 0;i<jsonProducts.length();i++) {
				JSONObject jsonObject = jsonProducts.getJSONObject(i);
				String pid = jsonObject.getString("id");
				String catid = jsonObject.getInt("catId")+"";
				String sid = jsonObject.getInt("sId")+"";
				double cost =  jsonObject.getDouble("cost");
				double price =( jsonObject.getDouble("price"));
				int q = Integer.parseInt(jsonObject.getString("quantity"));
				String name = (jsonObject.getString("productName"));
				supermarket.Product p = new supermarket.Product(pid, name, cost, price, sid, catid);
				choosedProducts.put(p,q);
			}
		}
		
		else if(request.getSession().getAttribute("cId") != null) {
			custid = (String)request.getSession().getAttribute("cId");
			JSONArray jsonProducts = SuperMarketStorage.getCartProductDetails(Integer.parseInt(custid));
			
			for(int i = 0;i<jsonProducts.length();i++) {
				JSONObject jsonObject = jsonProducts.getJSONObject(i);
				String pid = jsonObject.getInt("id")+"";
				String catid = jsonObject.getInt("catId")+"";
				String sid = jsonObject.getInt("sId")+"";
				double cost =  jsonObject.getDouble("cost");
				double price =( jsonObject.getDouble("price"));
				int q = (jsonObject.getInt("quantity"));
				String name = (jsonObject.getString("productName"));
				supermarket.Product p = new supermarket.Product(pid, name, cost, price, sid, catid);
				choosedProducts.put(p,q);
			}
			
			// delete from my cart
			boolean deletResult = SuperMarketStorage.removeFromcart(Integer.parseInt(custid));
			if(deletResult) {
				System.out.println("CART CLEARED");
			}
			else {
				System.out.println("NOT DELETED");
			}
		}
		
		// bill calculations
		Bill b = new Bill(null, null, null, 0, 0, null, 0);
		ArrayList<ProductPurchased> purchased = new ArrayList<>();
		double total = 0;
		double cost = 0;
		double gst = 0;
		double discountPercent = 0; 
		
		
		// calculate total cost of each Product
		HashMap<String, Category> allCategories = SuperMarketStorage.getCategoreiesMap();
		for(Product product1:choosedProducts.keySet()) {
			String catId = product1.getCategoryId();
			double  pGstPercent = allCategories.get(catId).getGst();
			double pCost = product1.getPrice();
			double pGst = (pGstPercent*pCost)/100;
			pCost = pCost - pGst;
			int qnt = choosedProducts.get(product1);
			double pTotal = qnt * product1.getPrice();
			
			ProductPurchased productPurchased = new ProductPurchased("", product1.getProductId(),qnt , pCost, pGst, pTotal);
			
			
//			productPurchased.setProductPurchaseId(purchaseId);
			purchased.add(productPurchased);
			
			cost = cost + pCost*qnt;
			gst  = gst + pGst*qnt;
			total = total + pTotal; 
		}
		
		String discounts = SuperMarketStorage.getMatchedDiscount(total);
		String disId = "";
		if(discounts != null) {
		 disId = discounts.split(" ")[1];
		discountPercent = Double.parseDouble(discounts.split(" ")[0]);
		}
		total = total - (discountPercent * total) / 100;
		JSONObject bill =  SuperMarketStorage.generateBill(custid,cost,gst,disId,total,purchased);
		
		if(bill == null) {
			JSONObject reJsonObject = new JSONObject();
			reJsonObject.put("status","failed");
			System.out.println("cant bill");
			response.getWriter().print(reJsonObject);
			return;
		}
		SuperMarketStorage.reduceQuantity(choosedProducts);
		response.getWriter().print(bill);
	}

}
