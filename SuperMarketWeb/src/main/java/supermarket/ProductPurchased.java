package supermarket;

public class ProductPurchased {
	private String productPurchaseId;
	private String productId;
	private int quantity;
	private double cost;
	private double gst;
	private double total;
	
	public ProductPurchased(String productPurchaseId, String productId, int quantity, double cost, double gst, double total) {
		this.productPurchaseId = productPurchaseId;
		this.productId = productId;
		this.quantity = quantity;
		this.cost = cost;
		this.gst = gst;
		this.total = total;
	}
	
	public String getProductPurchaseId() {
		return productPurchaseId;
	}
	public void setProductPurchaseId(String productPurchaseId) {
		this.productPurchaseId = productPurchaseId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public double getGst() {
		return gst;
	}
	public void setGst(double gst) {
		this.gst = gst;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
}
