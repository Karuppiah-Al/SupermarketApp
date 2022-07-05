package supermarket;

public class Discounts {
	private String discountId;
	private String discountName;
	private double discountPercent;
	private double totalAmount;
	public Discounts(String discountId, String discountName, double discountPercent, double totalAmount) {
		this.discountId = discountId;
		this.discountName = discountName;
		this.discountPercent = discountPercent;
		this.totalAmount = totalAmount;
	}
	
	public String getDiscountId() {
		return discountId;
	}
	
	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}
	
	public String getDiscountName() {
		return discountName;
	}
	
	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}
	
	public double getDiscountPercent() {
		return discountPercent;
	}
	
	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}
	
	public double getTotalAmount() {
		return totalAmount;
	}
	
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
}
