

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
 * Servlet implementation class Reports
 */
@MultipartConfig
@WebServlet("/api/reports/*")
public class Reports extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		PrintWriter out = response.getWriter();
		if(pathInfo != null && pathInfo.length() > 1) {
			pathInfo = pathInfo.substring(1);
			if(pathInfo.startsWith("most-buys")) {
				System.out.println(pathInfo);
				String dateString = request.getParameter("date");
				System.out.println(dateString);
				if(dateString == null) {
					return;
				}
				JSONArray dates = new JSONArray(dateString);
				JSONObject date = dates.getJSONObject(0);
				System.out.println(date);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String s = date.getString("startDate");
				String e = date.getString("endDate");
				LocalDate startDate = LocalDate.parse(s, formatter);
				LocalDate endDate = LocalDate.parse(e, formatter);
				JSONArray mostBuys =SuperMarketStorage.mostBuys(startDate,endDate);
				System.out.println(mostBuys);
				out.print(mostBuys);
			}
			else if(pathInfo.startsWith("all-buys")) {
				JSONArray allBuys = SuperMarketStorage.allProducts();
				System.out.println(allBuys);
				out.print(allBuys);
			}
			
		}
	}


}
