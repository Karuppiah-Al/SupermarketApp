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

import supermarket.*;
@MultipartConfig
@WebServlet("/api/category/*")
public class Category extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if(pathInfo == null) {
			RequestDispatcher requestDispatcher = req.getRequestDispatcher("/category.html");
			requestDispatcher.forward(req, resp);
			
		}
		else {
			pathInfo = pathInfo.substring(1);
			if(pathInfo.equals("*")) {
				resp.getWriter().print(SuperMarketStorage.getCategories());
				return;
			}
		}
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
		try(PrintWriter out = response.getWriter()){
			
			if(request.getParameter("ptype") !=null && request.getParameter("pgst") != "" ){
				String ptype = request.getParameter("ptype");
				String pgst = request.getParameter("pgst");
				System.out.println(ptype+""+pgst);
				double gst = Double.parseDouble(pgst);
				JSONObject responJsonObject = SuperMarketStorage.insertTocategory(ptype, gst);
				if(responJsonObject.get("status").equals("OK")) {
					out.write("ok");
				}
				else {
					if (responJsonObject.get("Error") != null) {
						String error = (String)responJsonObject.get("Error");
						if(error.contains("Duplicate") || error.contains("duplicate")) {
							out.write("This Category is Already Present");
						}
					}
					else {
						out.write("Unable to add");
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		PrintWriter out = resp.getWriter();

		if(pathInfo!=null && pathInfo.substring(1).length()!=0) {
			if(!Validate.isNumber(pathInfo.substring(1))) {
				out.print("Invalid requests");
			}
			else {
				String id = pathInfo.substring(1);
				String ptype = req.getParameter("ptype");
				String pgst = req.getParameter("pgst");
				
				if(ptype != null && pgst != null) {
				JSONObject jsonResponse = SuperMarketStorage.updateCategory(id, ptype, pgst);
				out.println(jsonResponse);
				}
			}
		}
	}

}
