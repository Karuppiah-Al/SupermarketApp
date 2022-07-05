package supermarket;

import org.json.JSONObject;

public class Product {
	private String productId;
	private String productName;
	private double cost;
	private double price;
	
	private String supplierId;
	private String categoryId;
	
	// private Category category;
	// private Category category;
	
	public Product(String productId, String productName, double cost, double price, String supplierId, String categoryId) {
		this.productId = productId;
		this.productName = productName;
		this.cost = cost;
		this.price = price;
		this.supplierId = supplierId;
		this.categoryId = categoryId;
	}
	
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	
	public JSONObject getJSON() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("product_Id", this.getProductId());
		jsonObject.put("product_Name", this.getProductName());
		jsonObject.put("category_Id", this.getCategoryId());
		jsonObject.put("supplier_Id", this.getSupplierId());
		jsonObject.put("cost", this.getCost());
		jsonObject.put("selling_Price", this.getPrice());
		return jsonObject;
	}
	
}


