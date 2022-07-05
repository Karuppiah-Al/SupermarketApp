package supermarket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.mysql.cj.xdevapi.JsonArray;
import com.mysql.cj.xdevapi.JsonValue;

import java.sql.*;
import org.json.*;

public class SuperMarketStorage {

	static Connection connection = GetConnection.getConnection("saravana", "mysql2001");

	public static JSONObject insertTocategory(String productType, Double gst) {
		// DB CODE

		String id = "";
		JSONObject response = new JSONObject();
		try {
			String insert = "INSERT INTO category VALUES(?,?,?)";
			PreparedStatement p = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			p.setString(1, null);
			p.setString(2, productType);
			p.setDouble(3, gst);

			p.execute();
			ResultSet rs = p.getGeneratedKeys();
			if (rs.next()) {
				id = id + rs.getInt(1);
			}
		} catch (SQLException e) {
			response.put("status", "failed");
			response.put("Error", e.getMessage());
			return response;
		}
		response.put("category_id", id);
		response.put("Product type", productType);
		response.put("gst", gst);
		response.put("createdAt", LocalDateTime.now());
		response.put("status", "OK");
		return response;

	}

	public static JSONObject updateCategory(String id, String productType, String gst) {
		JSONObject response = new JSONObject();
		try {
			String insert = "UPDATE category SET ProductType = ?, GST= ?  WHERE CategoryId = ?;";
			PreparedStatement p = connection.prepareStatement(insert);
			p.setString(1, productType);
			p.setDouble(2, Double.parseDouble(gst));
			p.setInt(3, Integer.parseInt(id));

			p.execute();
		} catch (SQLException e) {
			response.put("Error", e.getMessage());
			return response;
		}
		response.put("categoryId", id);
		response.put("productType", productType);
		response.put("gst", gst);
		response.put("updatedAt", LocalDateTime.now());
		return response;
	}

	public static JSONObject insertToSuppliers(String mail, String supplierName, String website, String categoryId) {

		// DB CODE
		JSONObject jObject = new JSONObject();
		String id = "";
		try {
			String insert = "INSERT INTO supplier VALUES(?,?,?,?,?)";
			PreparedStatement p = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			p.setString(1, null);
			p.setString(2, mail);
			p.setString(3, supplierName);
			p.setString(4, website);
			p.setString(5, categoryId);
			p.execute();

			ResultSet rs = p.getGeneratedKeys();
			if (rs.next()) {
				id = id + rs.getInt(1);

			}
		} catch (SQLException e) {
			jObject.put("Error", e.getMessage());

			if (e.getMessage().contains("Duplicate entry ")) {
				if (e.getMessage().contains("mail"))
					jObject.put("reason", "Mail already found");
				if (e.getMessage().contains("website"))
					jObject.put("reason", "Website already found");
			}
			return jObject;
		}
		jObject.put("supplier_name", supplierName);
		jObject.put("mail", mail);
		jObject.put("website", website);
		jObject.put("categoryId", categoryId);
		jObject.put("supplierId", id);
		jObject.put("createdAt", LocalDateTime.now());
		jObject.put("status", "ok");
		return jObject;
	}

	public static JSONObject updateSuppliers(String id, String mail, String supplierName, String website,
			String categoryId) {

		// DB CODE
		JSONObject jObject = new JSONObject();
		try {
			String insert = "UPDATE supplier SET supplier_name = ?, mail= ? ,website=?,categoryId = ? WHERE SupplierI_d = ?;";
			PreparedStatement p = connection.prepareStatement(insert);

			p.setString(1, supplierName);
			p.setString(2, mail);
			p.setString(3, website);
			p.setInt(4, Integer.parseInt(categoryId));
			p.setInt(5, Integer.parseInt(id));
			p.execute();

		} catch (SQLException e) {
			jObject.put("Error", e.getMessage());
			e.printStackTrace();
			return jObject;
		}
		jObject.put("supplier_name", supplierName);
		jObject.put("mail", mail);
		jObject.put("website", website);
		jObject.put("categoryId", categoryId);
		jObject.put("supplierId", id);
		jObject.put("updatedAt", LocalDateTime.now());
		return jObject;
	}

	public static JSONObject insertToProducts(String name, double cost, double price, String supplierId,
			String categoryId) {
		Product product = new Product("", name, cost, price, supplierId, categoryId);
		System.out.println("in products");
		JSONObject responseObject = new JSONObject();
		String id = "";
		try {
			String insert = "INSERT INTO Products VALUES(?,?,?,?,?,?)";
			PreparedStatement p = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			p.setString(1, null);
			p.setString(2, name);
			p.setString(3, categoryId);
			p.setDouble(4, cost);
			p.setDouble(5, price);
			p.setString(6, supplierId);
			p.execute();

			ResultSet rs = p.getGeneratedKeys();
			if (rs.next()) {
				id = id + rs.getInt(1);

			}
		} catch (SQLException e) {
			responseObject.put("Error", e.getMessage());
			responseObject.put("status", "failed");
			return responseObject;
		}
		product.setProductId(id);
		responseObject = product.getJSON();
		responseObject.put("status", "ok");
		responseObject.put("createdtedAt", LocalDateTime.now());
		return responseObject;
	}

	public static JSONObject insertToStore(LocalDate addedDate, LocalDate expDate, int quantity, String productId) {
		System.out.println("in store");
		JSONObject jsonObject = new JSONObject();
		// DB CODE
		String id = "";
		try {
			String insert = "INSERT INTO store VALUES(?,?,?,?,?)";
			PreparedStatement p = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			p.setString(1, null);
			p.setString(2, productId);
			p.setDate(3, java.sql.Date.valueOf(addedDate));
			if (expDate != null) {
				p.setDate(4, java.sql.Date.valueOf(expDate));
			} else {
				p.setNull(4, Types.DATE);
			}
			p.setInt(5, quantity);
			p.execute();
			ResultSet rs = p.getGeneratedKeys();
			if (rs.next()) {
				id = id + rs.getInt(1);

			}

		} catch (SQLException e) {
//			e.printStackTrace();
			jsonObject.put("Error", e.getMessage());
			return jsonObject;
		}
		jsonObject.put("storeId", id);
		jsonObject.put("productId", productId);
		jsonObject.put("qunatity", quantity);
		jsonObject.put("addedDate", LocalDateTime.now());
		jsonObject.put("expDate", expDate);
		return jsonObject;
	}

	public static JSONObject updateProducts(String id, String name, double cost, double price, String supplierId,
			String categoryId) {
		JSONObject responseObject = new JSONObject();
		Product product = new Product("", name, cost, price, supplierId, categoryId);
		// DB CODE
		try {
			String update = "UPDATE products SET productName = ?, cost= ?,price = ?  WHERE Product_Id = ?";
			PreparedStatement p = connection.prepareStatement(update);
			p.setString(1, name);
			p.setDouble(2, cost);
			p.setDouble(3, price);
			p.execute();

		} catch (SQLException e) {
			responseObject.put("Error", e.getMessage());
		}
		responseObject = product.getJSON();
		responseObject.put("updatedAt", LocalDateTime.now());
		return responseObject;
	}

	public static JSONObject generateBill(String custId, double cost, double gst, String disId, double total,
			ArrayList<ProductPurchased> purchased) {
		Bill bill = new Bill("", custId, LocalDate.now(), cost, gst, disId, total);
		bill.setMyProducts(purchased);

		// DB CODE
		String id = "";
		try {
			String insert = "INSERT INTO bill VALUES(?,?,?,?,?,?,?)";
			String updateVisit = "update customer set visited_Count=visited_Count+1 where CustomerId=?";
			PreparedStatement p = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			p.setString(1, null);
			p.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
			p.setString(3, custId);
			p.setDouble(4, cost);
			p.setDouble(5, gst);
			p.setString(6, disId);
			p.setDouble(7, total);
			p.execute();

			ResultSet rs = p.getGeneratedKeys();
			if (rs.next()) {
				id = id + rs.getInt(1);
			}

			p = connection.prepareStatement(updateVisit);
			p.setString(1, custId);
			p.execute();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		for (ProductPurchased p : purchased) {
			addToPurchased(id, p.getProductPurchaseId(), p.getProductId(), p.getQuantity(), p.getCost(), p.getGst(),
					p.getTotal());
		}

		return getMatchedBill(id);
	}

	public static void addToPurchased(String billId, String ProductPID, String productId, int Quantity, Double cost,
			Double gst, Double total) {

		try {
			String insert = "INSERT INTO productspurchased VALUES(?,?,?,?,?,?,?)";
			PreparedStatement p = connection.prepareStatement(insert);

			p.setString(1, null);
			p.setString(2, billId);
			p.setString(3, productId);
			p.setInt(4, Quantity);
			p.setDouble(5, cost);
			p.setDouble(6, gst);
			p.setDouble(7, total);
			p.execute();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JSONObject addToDiscount(String name, double percent, double amt) {
		String id = "";
		JSONObject jsonObject = new JSONObject();
		try {
			String insert = "INSERT INTO discounts VALUES(?,?,?,?)";
			PreparedStatement p = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			p.setString(1, null);
			p.setString(2, name);
			p.setDouble(3, percent);
			p.setDouble(4, amt);
			p.execute();

			ResultSet rs = p.getGeneratedKeys();
			if (rs.next()) {
				id = id + rs.getInt(1);
			}
		} catch (Exception e) {
			jsonObject.put("Error ", e.getMessage());
			return jsonObject;

		}
		jsonObject.put("discount_Id", id);
		jsonObject.put("discount_Name", name);
		jsonObject.put("discount_percent", percent);
		jsonObject.put("discount_AmountLimit", amt);
		jsonObject.put("createdAt", LocalDateTime.now());
		jsonObject.put("status", "ok");

		return jsonObject;
	}

	public static JSONObject updateDiscounts(String id, String name, double percent, double amt) {
		JSONObject jsonObject = new JSONObject();
		try {
			String update = "UPDATE discounts SET discount_Name = ?, Discount= ?,totalAmount = ? WHERE discount_Id = ?;";
			PreparedStatement p = connection.prepareStatement(update);
			p.setString(1, name);
			p.setDouble(2, percent);
			p.setDouble(3, amt);
			p.setString(4, id);
			p.execute();

		} catch (Exception e) {
			jsonObject.put("Error ", e.getMessage());
			return jsonObject;
		}
		jsonObject.put("discount_Id", id);
		jsonObject.put("discount_Name", name);
		jsonObject.put("discount_percent", percent);
		jsonObject.put("discount_AmountLimit", amt);
		jsonObject.put("UpdatedAt", LocalDateTime.now());

		return jsonObject;
	}

	// to show products in web
	public static JSONArray getProducts() {
		HashMap<String, Product> allProducts = new HashMap<>();
		JSONArray products = new JSONArray();
		try {
			LocalDate current = LocalDate.now();
//			DateTimeFormatter d = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			String get = "select products.product_Id,products.productName,products.categoryId,products.cost,products.price,products.supplier_Id from products join store on products.Product_Id = store.Product_Id where store.quantity > 0 order by (select ProductType from category where products.categoryId = category.categoryId order by ProductType)";
			String getProducts = "select products.Product_Id,products.productName,products.price,products.cost, SUM(store.Quantity),store.expDate,products.categoryId,products.supplier_Id from store join products on products.Product_Id = store.product_Id where (store.Quantity) >0 && (date(store.expDate) >= ? || date(store.expDate) is null) group by products.Product_Id;";
			PreparedStatement p = connection.prepareStatement(getProducts);
			p.setDate(1, Date.valueOf(current));
			ResultSet r = p.executeQuery();
			while (r.next()) {
				String productId = r.getString(1);
				String productName = r.getString(2);
				double price = r.getDouble(3);
				double cost = r.getDouble(4);
				int quantity = r.getInt(5);
				int catId = r.getInt(7);
				int sId = r.getInt(8);
				// to JSON
				JSONObject thisProduct = new JSONObject();
				thisProduct.put("productId", productId);
				thisProduct.put("productName", productName);
				thisProduct.put("cost", cost);
				thisProduct.put("price", price);
				thisProduct.put("quantity", quantity);
				thisProduct.put("catId", catId);
				thisProduct.put("sId", sId);

				products.put(thisProduct);
			}

		} catch (SQLException e) {
			JSONObject error = new JSONObject();
			error.put("error", e.getMessage());
			products.put(error);
			return products;
		}

		return products;
	}

	public static JSONArray getCategories() {
		HashMap<String, Category> allCategories = new HashMap<>();
		JSONArray categories = new JSONArray();
		try {
			String get = "SELECT * FROM category";
			PreparedStatement p = connection.prepareStatement(get);

			ResultSet r = p.executeQuery();
			while (r.next()) {
				Category c = new Category(r.getString(1), r.getString(2), r.getDouble(3));
				allCategories.put(c.getCategoryId(), c);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", c.getCategoryId());
				jsonObject.put("type", c.getProductType());
				jsonObject.put("gst", c.getGst());
				categories.put(jsonObject);
			}

		} catch (SQLException e) {

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("error", e.getMessage());
			categories.put(jsonObject);
			return categories;
		}
		return categories;
	}

	public static HashMap<String, Category> getCategoreiesMap() {
		HashMap<String, Category> allCategories = new HashMap<>();
		JSONArray categories = new JSONArray();
		try {
			String get = "SELECT * FROM category";
			PreparedStatement p = connection.prepareStatement(get);

			ResultSet r = p.executeQuery();
			while (r.next()) {
				Category c = new Category(r.getString(1), r.getString(2), r.getDouble(3));
				allCategories.put(c.getCategoryId(), c);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", c.getCategoryId());
				jsonObject.put("type", c.getProductType());
				jsonObject.put("gst", c.getGst());
				categories.put(jsonObject);
			}

		} catch (SQLException e) {

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("error", e.getMessage());
			categories.put(jsonObject);
			return null;
		}
		return allCategories;
	}

	public static HashMap<String, Discounts> getDiscounts() {
		HashMap<String, Discounts> allDiscounts = new HashMap<>();
		try {
			String get = "SELECT * FROM discounts";
			PreparedStatement p = connection.prepareStatement(get);

			ResultSet r = p.executeQuery();
			while (r.next()) {
				Discounts d = new Discounts(r.getString(1), r.getString(2), r.getDouble(3), r.getDouble(4));
				allDiscounts.put(d.getDiscountId(), d);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allDiscounts;
	}

	public static JSONArray getSupplier(String cat) {
		HashMap<String, Supplier> allSupplier = new HashMap<>();
		JSONArray suppliers = new JSONArray();
		try {
			String get = "SELECT * FROM supplier where categoryId = ?";
			if (cat == "") {
				get = "SELECT * FROM supplier";
				PreparedStatement p = connection.prepareStatement(get);
				ResultSet r = p.executeQuery();
				while (r.next()) {
					Supplier s = new Supplier(r.getString(1), r.getString(2), r.getString(3), r.getString(4),
							r.getString(5));
					allSupplier.put(s.getSupplierId(), s);
				}
//				return allSupplier;
			}
			PreparedStatement p = connection.prepareStatement(get);
			p.setString(1, cat);
			ResultSet r = p.executeQuery();
			while (r.next()) {
				Supplier s = new Supplier(r.getString(1), r.getString(2), r.getString(3), r.getString(4),
						r.getString(5));
				allSupplier.put(s.getSupplierId(), s);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("supplierId", s.getSupplierId());
				jsonObject.put("categoryId", s.getCategoryId());
				jsonObject.put("name", s.getName());
				jsonObject.put("website", s.getWebsite());
				jsonObject.put("mail", s.getMail());

				suppliers.put(jsonObject);
			}

		} catch (SQLException e) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("error", e.getMessage());
			suppliers.put(jsonObject);
		}
		return suppliers;
	}

	public static HashMap<String, Store> getStore() {
		HashMap<String, Store> allStore = new HashMap<>();
		try {
			String get = "SELECT * FROM store";
			PreparedStatement p = connection.prepareStatement(get);

			ResultSet r = p.executeQuery();
			while (r.next()) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String addDate = r.getDate(3).toString();
				String expDate = "";
				LocalDate add = LocalDate.parse(addDate, formatter);
				LocalDate exp;
				if (r.getDate(4) != null) {
					expDate = r.getDate(4).toString();
					exp = LocalDate.parse(expDate, formatter);
				} else {
					exp = null;
				}

				Store s = new Store(r.getString(1), add, exp, r.getInt(5), r.getString(2));

				allStore.put(s.getProductId(), s);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allStore;
	}

	public static String getMatchedDiscount(double total) {
		String get = "select discount_id, discount from discounts where totalAmount <= ? order by totalAmount desc limit 1;";
		try {

			PreparedStatement p = connection.prepareStatement(get);
			p.setDouble(1, total);
			ResultSet rs = p.executeQuery();
			if (rs.next()) {
				String id = rs.getString(1);
				double discount = rs.getDouble(2);

				return discount + " " + id;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static JSONObject addWorkers(String mobile, String name, String password) {

		JSONObject jsonObject = new JSONObject();
		int id = 0;
		try {
			String insertUser = "INSERT INTO users VALUES(?,?)";
			PreparedStatement prep = connection.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
			prep.setString(1, null);
			prep.setDate(2, Date.valueOf(LocalDate.now()));
			prep.execute();
			ResultSet rs = prep.getGeneratedKeys();
			while (rs.next()) {
				id = (rs.getInt(1));
			}
		} catch (Exception e) {
			jsonObject.put("error", e.getMessage());
			return jsonObject;
		}

		try {

			String insertWorker = "INSERT INTO worker VALUES(?,?,?,?,?)";
			PreparedStatement prep = connection.prepareStatement(insertWorker);
			prep.setString(1, null);
			prep.setInt(2, id);
			prep.setString(3, name);
			prep.setString(4, mobile);
			prep.setString(5, password);
			prep.execute();

			jsonObject.put("status", "ok");
			return jsonObject;
		} catch (SQLException e) {
			jsonObject.put("error", e.getMessage());
		}
		return jsonObject;
	}

	public static Customer addCustomer(String mobile, String name) {

		String id = "";
		try {
			String insertUser = "INSERT INTO users VALUES(?,?)";
			PreparedStatement prep = connection.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
			prep.setString(1, null);
			prep.setDate(2, Date.valueOf(LocalDate.now()));
			prep.execute();

			ResultSet rs = prep.getGeneratedKeys();
			if (rs.next()) {
				id = id + rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

		try {
			String custid = "";
			String insertCustomer = "INSERT INTO customer VALUES(?,?,?,?,?)";
			PreparedStatement prep = connection.prepareStatement(insertCustomer, Statement.RETURN_GENERATED_KEYS);
			prep.setString(1, null);
			prep.setString(2, id);
			prep.setString(3, name);
			prep.setString(4, mobile);
			prep.setInt(5, 0);

			prep.execute();

			ResultSet rs = prep.getGeneratedKeys();
			if (rs.next()) {
				custid = "" + rs.getInt(1);
			}

			Customer customer = new Customer(id, LocalDate.now(), custid, mobile, name);
			return customer;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public static Customer addCustomer(String mobile, String name,String password) {

		String id = "";
		try {
			String insertUser = "INSERT INTO users VALUES(?,?,?)";
			PreparedStatement prep = connection.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
			prep.setString(1, null);
			prep.setDate(2, Date.valueOf(LocalDate.now()));
			prep.execute();

			ResultSet rs = prep.getGeneratedKeys();
			if (rs.next()) {
				id = id + rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}

		try {
			String custid = "";
			String insertCustomer = "INSERT INTO customer VALUES(?,?,?,?,?,?)";
			PreparedStatement prep = connection.prepareStatement(insertCustomer, Statement.RETURN_GENERATED_KEYS);
			prep.setString(1, null);
			prep.setString(2, id);
			prep.setString(3, name);
			prep.setString(4, mobile);
			prep.setInt(5, 0);
			prep.setString(6, password);

			prep.execute();

			ResultSet rs = prep.getGeneratedKeys();
			if (rs.next()) {
				custid = "" + rs.getInt(1);
			}
			
			// creating cart
			String createCart = "INSERT INTO CART VALUES(?,?)";
			prep = connection.prepareStatement(createCart);
			prep.setString(1, null);
			prep.setInt(2, Integer.parseInt(custid));
			prep.execute();
			
			Customer customer = new Customer(id, LocalDate.now(), custid, mobile, name);
			return customer;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
		
	}

	public static boolean isWorkerExist(String mobile) {
		System.out.println(mobile);
		try {
			if (connection == null) {
				System.out.println("Nullllll");
			}
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM worker where mobile = ?");
			preparedStatement.setString(1, mobile);
			ResultSet r = preparedStatement.executeQuery();
			if (r.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static boolean isWorkerExist(String mobile, String password) {
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM worker where mobile = ? AND password = ?");
			preparedStatement.setString(1, mobile);
			preparedStatement.setString(2, password);
			ResultSet r = preparedStatement.executeQuery();
			if (r.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static Customer isCustomerExist(String mobile) {

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(
					"select Users.create_date,customerId,mobile,Customer_Name,Users.userId from  customer join  Users on Users.userId = customer.userId and customer.mobile = ?");
			preparedStatement.setString(1, mobile);
			ResultSet r = preparedStatement.executeQuery();

			if (r.next()) {
				String userid = r.getString(5);
				String custId = r.getString(2);
				String name = r.getString(4);
				mobile = r.getString(3);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate d = LocalDate.parse(r.getDate(1).toString(), formatter);
				return new Customer(userid, d, custId, mobile, name);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public static Customer isCustomerExist(String mobile, String password) {
		System.out.println(password+" "+mobile);
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("select Users.create_date,customerId,mobile,Customer_Name,Users.userId,password from  customer join  Users on Users.userId = customer.userId and customer.mobile = ? where customer.password = ?");
			preparedStatement.setString(1, mobile);
			preparedStatement.setString(2, password);
			ResultSet r = preparedStatement.executeQuery();

			if (r.next()) {
				String userid = r.getString(5);
				String custId = r.getString(2);
				String name = r.getString(4);
				mobile = r.getString(3);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate d = LocalDate.parse(r.getDate(1).toString(), formatter);
				Customer c = new Customer(userid, d, custId, mobile, name);
				c.setPassword(r.getString(6));
				System.out.println(c.getName()+password);
				return c;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public static JSONObject getMatchedBill(String bill_Id) {

		JSONObject jsonBill = new JSONObject();
		JSONArray allProducts = new JSONArray();
		try {
			String getBill = "SELECT bill.purchasedate,Customer.Customer_Name from bill join customer on customer.CustomerId =bill.CustomerId where bill_Id= ?";
			String getProducts = "select products.productName,productspurchased.Quantity,productspurchased.cost,productspurchased.gst,productspurchased.total from productspurchased join products on products.Product_Id=productspurchased.productId where bill_Id=?";
			String getTotal = "select  bill.cost,bill.gst,bill.total from bill where bill_Id =?";

			PreparedStatement prep = connection.prepareStatement(getBill);
			prep.setString(1, bill_Id);
			ResultSet rs = prep.executeQuery();
			boolean flag = false;
			if (rs.next()) {
				double cost = 0;
				double gst = 0;
				double discount = 0;
				double total = 0;
				Date purchasedate = rs.getDate(1);
				String name = rs.getString(2);
				{
					jsonBill.put("bill", bill_Id);
					jsonBill.put("purchaseDate", purchasedate);
					jsonBill.put("Name", name);

				}
				flag = true;
				System.out.println(
						"BILL ID :  " + bill_Id + "      DATE : " + rs.getDate(1) + "    Name :" + rs.getString(2));
				PreparedStatement prep2 = connection.prepareStatement(getProducts);
				prep2.setString(1, bill_Id);
				ResultSet rs2 = prep2.executeQuery();
				System.out.printf("%10s %10s %10s %10s %10s\n", "Product Name", "Quantity", "Cost", "Gst", "Total");
				int i = 0;
				while (rs2.next()) {
					String products = rs2.getString(1);
					int quantity = rs2.getInt(2);
					double pcost = rs2.getDouble(3);
					double pgst = rs2.getDouble(4);
					double ptotal = rs2.getDouble(5);
					JSONObject productsJson = new JSONObject();

					productsJson.put("productName", products);
					productsJson.put("quantity", quantity);
					productsJson.put("cost", pcost);
					productsJson.put("gst", pgst);
					productsJson.put("total", ptotal);
					allProducts.put(productsJson);

					System.out.printf("%8s %10d %13.2f %13.2f %13.2f\n", rs2.getString(1), rs2.getInt(2),
							rs2.getDouble(3), rs2.getDouble(4), rs2.getDouble(5));

				}
				jsonBill.put("productsPurchased", allProducts);
				PreparedStatement p3 = connection.prepareStatement(getTotal);
				p3.setString(1, bill_Id);
				ResultSet rs3 = p3.executeQuery();
				if (rs3.next()) {

					cost = rs3.getDouble(1);
					gst = rs3.getDouble(2);
					total = rs3.getDouble(3);
					discount = cost + gst - total;
					System.out.printf("COST     : %.2f\n", cost);
					System.out.printf("GST      : %.2f\n", gst);
					System.out.printf("DISCOUNT : %.2f\n", discount);
					System.out.printf("TOTAL    : %.2f\n\n\n", total);
				}

				{

					jsonBill.put("cost", cost);
					jsonBill.put("gst", gst);
					jsonBill.put("discount", discount);
					jsonBill.put("total", total);

				}
				System.out.println(jsonBill);
			}
			if (!flag) {
				System.out.println("NO BILL FOUND");
				jsonBill.put("status", "failed");
				jsonBill.put("info", "Bill Not Found");
				return jsonBill;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return jsonBill;
		}
		return jsonBill;
	}

	public static JSONArray getBillForCustomer(String mobile) {
		JSONArray myBills = new JSONArray();
		try {
			String getCustomer = "select bill.bill_Id from bill where bill.CustomerId =(select customer.CustomerId from customer where customer.mobile =?)";
			PreparedStatement p = connection.prepareStatement(getCustomer);
			p.setString(1, mobile);
			ResultSet rs = p.executeQuery();
			if (!rs.next()) {
				JSONObject j = new JSONObject();
				j.put("info", "no customer found");
				j.put("status", "failed");
				myBills.put(j);
				return myBills;
			}
			do {
				myBills.put(getMatchedBill(rs.getString(1)));
			} while (rs.next());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myBills;
	}

	public static JSONArray getBillForCustomer(int id) {
		JSONArray customerBills = new JSONArray();
		try {
			String getCustomer = "select bill.bill_Id from bill where bill.CustomerId = ?";
			PreparedStatement p = connection.prepareStatement(getCustomer);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			if (!rs.next()) {
				JSONObject j = new JSONObject();
				j.put("info", "no customer found");
				j.put("status", "failed");
				customerBills.put(j);
				return customerBills;
			}
			do {
				customerBills.put(getMatchedBill(rs.getString(1)));
			} while (rs.next());
		} catch (Exception e) {
			e.printStackTrace();
			return customerBills;
		}
		return customerBills;
	}

	public static JSONObject getBillForCustomer(int id, int bill_id) {
		JSONObject jsonObject = new JSONObject();
		try {
			String getCustomer = "select bill.bill_Id from bill where bill.CustomerId = ? and bill.bill_Id = ?";
			PreparedStatement p = connection.prepareStatement(getCustomer);
			p.setInt(1, id);
			p.setInt(2, bill_id);
			ResultSet rs = p.executeQuery();
			if (rs.next()) {
				return getMatchedBill(bill_id + "");
			}

		} catch (Exception e) {
			jsonObject.put("Error", e.getMessage());
			return jsonObject;
		}
		jsonObject.put("info", "Bill not found");
		return jsonObject;
	}

	public static void reduceQuantity(HashMap<Product, Integer> choosed) {

		for (Product p : choosed.keySet()) {
			try {
				String get = "update store set Quantity=Quantity-? where product_Id=?";
				PreparedStatement prep = connection.prepareStatement(get);
				prep.setInt(1, choosed.get(p));
				prep.setString(2, p.getProductId());
				prep.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static JSONArray allBills() {

		JSONArray bills = new JSONArray();

		try {
			String query = "select bill_Id from bill";
			PreparedStatement p = connection.prepareStatement(query);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				bills.put(getMatchedBill(rs.getString(1)));
			}
		} catch (Exception e) {
			bills.put(new JSONObject().put("Error", e.getMessage()));
			return bills;
		}
		return bills;
	}

	public static JSONObject getCustomerDetails(String id) {
		JSONObject details = new JSONObject();
		try {
			String query = "select Customer_Name,mobile from customer where CustomerId = ?";
			PreparedStatement p = connection.prepareStatement(query);
			p.setString(1, id);
			ResultSet rs = p.executeQuery();
			if (rs.next()) {
				details.put("name", rs.getString(1));
				details.put("mobile", rs.getString(2));
			} else {

				details.put("info", "Customer Not found");
			}
		} catch (Exception e) {
			details.put("Error Message", e.getMessage());
			return details;
		}
		return details;
	}

	// most buys btn two dates
	public static JSONArray mostBuys(LocalDate start, LocalDate end) {
		JSONArray mostBuys = new JSONArray();
		try {
			String mostbuy = "select products.Product_Id,SUM(productspurchased.total),sum(Quantity),products.productName from bill  join productspurchased on productspurchased.bill_Id = bill.bill_Id join products on products.Product_Id = ProductsPurchased.productId where date(purchaseDate) between ? and ? group by productId order by sum(Quantity) desc limit 3 ";

			PreparedStatement pre = connection.prepareStatement(mostbuy);
			pre.setDate(1, Date.valueOf(start));
			pre.setDate(2, Date.valueOf(end));
			ResultSet rs = pre.executeQuery();
			System.out.printf("%5s %10s %10s %10s\n", "Product ID", "Total Price", "Quantity", "ProductName");
			System.out.println("");
			if (!rs.next()) {
				JSONObject report = new JSONObject();
				report.put("status", "failed");
				report.put("info", "No Buys");
				mostBuys.put(report);
			}
			do {
				JSONObject buy1 = new JSONObject();
				buy1.put("productId", rs.getInt(1));
				buy1.put("totalPrice", rs.getInt(2));
				buy1.put("quantity", rs.getInt(3));
				buy1.put("productName", rs.getString(4));
				buy1.put("status", "succes");
				mostBuys.put(buy1);
				System.out.printf("%5d %10d %10d %10s\n", rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4));
			} while (rs.next());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			JSONObject report = new JSONObject();
			report.put("status", "failed");
			report.put("info", e.getMessage());
			mostBuys.put(report);
			return mostBuys;

		}
		return mostBuys;
	}

	// all products bought
	public static JSONArray allProducts() {
		JSONArray allProducts = new JSONArray();
		try {
			String get = "select sum(Quantity) as \"Quantity\" ,SUM(productspurchased.total) as \"Total Amount \",products.productName,customer.customerId , customer.Customer_Name from customer join bill on customer.customerId = bill.customerId join productspurchased on productspurchased.bill_Id = bill.bill_Id  join products on products.Product_Id = ProductsPurchased.productId group by customer.customerId,productId order by Quantity desc; ";
			PreparedStatement prep = connection.prepareStatement(get);
			ResultSet rs = prep.executeQuery();
			System.out.printf("%10s %15s %15s %15s\n\n", "Quantity", "Total Amount", "Product Name", "Customer Name");
			if (!rs.next()) {
				JSONObject report = new JSONObject();
				report.put("status", "failed");
				report.put("info", "No Buys");
				allProducts.put(report);

			}
			do {
				JSONObject products = new JSONObject();
				products.put("quantity", rs.getInt(1));
				products.put("totalAmount", rs.getInt(2));
				products.put("productName", rs.getString(3));
				products.put("customerName", rs.getString(5));
				allProducts.put(products);
				System.out.printf("%10s %15s %15s %15s\n", rs.getInt(1), rs.getInt(2), rs.getString(3),
						rs.getString(5));
			} while (rs.next());
		} catch (Exception e) {
			System.out.println(e);
			JSONObject report = new JSONObject();
			report.put("status", "failed");
			report.put("info", e.getMessage());
			allProducts.put(report);
			return allProducts;
		}
		return allProducts;
	}

	public static Integer getId(String mobile) {
		int id = 0;
		try {
			String getId = "SELECT CustomerId from customer where mobile = ?";
			PreparedStatement p = connection.prepareStatement(getId);
			p.setString(1, mobile);
			ResultSet rs = p.executeQuery();
			if (!rs.next()) {
				return null;
			} else {
				id = rs.getInt(1);
				System.out.println(mobile + " ID IS :" + id);
				return id;
			}
		} catch (Exception e) {
			return null;
		}
	}

	public static JSONArray getProductsForUpdate() {
		JSONArray products = new JSONArray();
		try {
			String getPtoducts = "select store.StoreId,store.addedDate,store.Quantity,supplier.supplier_name,products.productName from store join products on products.Product_Id = store.Product_Id  join supplier on supplier.SupplierI_d = products.supplier_Id where Quantity > 0 ";
			PreparedStatement preparedStatement = connection.prepareStatement(getPtoducts);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				JSONObject empty = new JSONObject();
				empty.put("info", "NO PRODUCTS IN STORE");
				empty.put("status", "null");
				products.put(empty);
				return products;
			}
			do {
				JSONObject myJsonObject = new JSONObject();
				myJsonObject.put("storedId", resultSet.getInt(1));
				myJsonObject.put("addedDate", resultSet.getDate(2));
				myJsonObject.put("quantity", resultSet.getInt(3));
				myJsonObject.put("supplierName", resultSet.getString(4));
				myJsonObject.put("productName", resultSet.getString(5));
				products.put(myJsonObject);

			} while (resultSet.next());
		} catch (Exception e) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("status", "failed");
			jsonObject.put("info", e.getMessage());
			products.put(jsonObject);
			System.out.println(products + " " + e.getMessage());
			e.printStackTrace();
			return products;
		}
		System.out.println(products);
		return products;
	}

	public static JSONObject updateInStore(int id, int quantity) {
		JSONObject update = new JSONObject();
		try {
			String updateQuery = "update store set Quantity=? where storeId=?";
			PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
			preparedStatement.setInt(1, quantity);
			preparedStatement.setInt(2, id);
			int result = preparedStatement.executeUpdate();
			if (result > 0) {
				update.put("status", "ok");
				update.put("count", result);
				return update;
			}
		} catch (Exception e) {
			update.put("status", "failed");
			update.put("info", e.getMessage());
			return update;
		}
		update.put("status", "ok");
		update.put("count", 0);
		return update;
	}

	public static JSONArray getCart(int id) {
		JSONArray cart = new JSONArray();
		String getCartQuery = "select items.cart_ItemId,products.productName,items.quantity from items join cart on cart.customerId = ? join products on products.Product_Id = items.productid; ";

		try {
			PreparedStatement p = connection.prepareStatement(getCartQuery);
			p.setInt(1, id);
			ResultSet resultSet = p.executeQuery();
			if (!resultSet.next()) {
				JSONObject empty = new JSONObject();
				empty.put("status", "ok");
				empty.put("count", "0");
				empty.put("info", "empty");
				cart.put(empty);
				return cart;
			}

			do {
				JSONObject product = new JSONObject();
				product.put("itemId", resultSet.getInt(1));
				product.put("productName", resultSet.getString(2));
				product.put("quantity", resultSet.getInt(3));
				cart.put(product);
			} while (resultSet.next());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			JSONObject cartError = new JSONObject();
			cartError.put("status", "failed");
			cartError.put("info", e.getMessage());
			cart.put(cartError);
			return cart;
		}
		return cart;
	}

	public static JSONObject addToCart(int custId, int productId, int quantity) {
		int cartId = -1;
		JSONObject response = new JSONObject();
		try {
			String getCartId = "Select cartId from cart where customerId = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(getCartId);
			preparedStatement.setInt(1, custId);
			ResultSet rs = preparedStatement.executeQuery();
			if(rs.next()) {
				
				cartId = rs.getInt(1);
			}
			else {
				return response;
			}
			
			System.out.println("cartId : "+cartId);
		} catch (Exception e) {
			System.out.println(e.getMessage()+" "+1211);
		}
		
		// If    : Cart id and product Id are found update the quantity
		String check = "select * from items where productid = ? && cartId = ?;";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(check);
			preparedStatement.setInt(1, productId);
			preparedStatement.setInt(2, cartId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				String update = "update items set items.quantity = items.quantity+? where cartId = ? && items.productid = ?";
				preparedStatement = connection.prepareStatement(update);
				preparedStatement.setInt(1, quantity);
				preparedStatement.setInt(2, cartId);
				preparedStatement.setInt(3, productId);
				
				preparedStatement.execute();
				response.put("status", "ok");
				response.put("info", "Succesfully Inserted");
				return response;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage()+" "+1234);
			return response;
		}
		
		// Else : insert new row
		String putQuery = "insert into items values(?,?,?,?)";
		try {
			PreparedStatement preparedStatement;
			if (cartId > 0) {
				preparedStatement = connection.prepareStatement(putQuery);
				preparedStatement.setString(1, null);
				preparedStatement.setInt(2, cartId);
				preparedStatement.setInt(3, productId);
				preparedStatement.setInt(4, quantity);
				preparedStatement.execute();

				response.put("status", "ok");
				response.put("info", "Succesfully Inserted");
				return response;
			} else {

				response.put("status", "failed");
				response.put("info", "Invalid custid");
				return response;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JSONObject error = new JSONObject();
			error.put("status", "failed");
			error.put("info", e.getMessage());
			return error;
		}
	}
	
	public static JSONArray getCartProductDetails(int custId) {
		JSONArray productDetails = new JSONArray();
		try {
			String get = "select items.productid,products.cost,products.price,items.quantity,products.productName, products.supplier_Id,products.categoryId from items join cart on cart.customerId = ? join products on products.Product_Id = items.productid ;";
			PreparedStatement preparedStatement = connection.prepareStatement(get);
			preparedStatement.setInt(1, custId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				JSONObject product = new JSONObject();
				product.put("id", resultSet.getInt(1));
				product.put("cost", resultSet.getInt(2));
				product.put("price", resultSet.getInt(3));
				product.put("quantity", resultSet.getInt(4));
				product.put("productName", resultSet.getString(5));
				product.put("sId",resultSet.getInt(6));
				product.put("catId",resultSet.getInt(7));
				
				
				productDetails.put(product);
			}
			return productDetails;
		} catch (Exception e) {
			JSONObject error = new JSONObject();
			error.put("status", "failed");
			error.put("info", e.getMessage());
			
			System.out.println(e.getMessage());
			productDetails.put(error);
			return productDetails;
		}
	}
	public static boolean removeFromcart(int custId) {
		String cartIdQuery = "select cartId from cart where customerid = ?";
		String deleteQuery = "delete from items where cartId = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(cartIdQuery);
			preparedStatement.setInt(1, custId);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				int cartId = resultSet.getInt(1);
				preparedStatement = connection.prepareStatement(deleteQuery);
				preparedStatement.setInt(1, cartId);
				if(preparedStatement.executeUpdate() > 0) {
					return true;
				}
				else {
					return false;
				}
			}
			return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public static boolean updateQuantity(int itemid,int quantity) {
		try {
			String query = "select store.Quantity from store join items on store.product_Id = items.productid where items.cart_ItemId = ?;";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, itemid);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				if(resultSet.getInt(1) < quantity) {
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			String getCartId = "update items set items.quantity = ? where items.cart_ItemId = ?;";
			PreparedStatement preparedStatement = connection.prepareStatement(getCartId);
			preparedStatement.setInt(1, quantity);
			preparedStatement.setInt(2, itemid);
			int c = preparedStatement.executeUpdate();
			if(c>0) {
				
				return true;
			}
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage()+" "+1352);
		}
		
		return true;
	}
}