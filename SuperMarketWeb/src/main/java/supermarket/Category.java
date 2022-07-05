package supermarket;

public class Category {
	private String categoryId;
	private String productType;
	private double gst;
	public Category(String categoryId, String productType, double gst) {
		this.categoryId = categoryId;
		this.productType = productType;
		this.gst = gst;
	}
	
	public String getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getProductType() {
		return productType;
	}
	
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public double getGst() {
		return gst;
	}
	
	public void setGst(double gst) {
		this.gst = gst;
	}
	
}
