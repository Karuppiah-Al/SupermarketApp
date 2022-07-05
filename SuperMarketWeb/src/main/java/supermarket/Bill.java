package supermarket;

import java.time.LocalDate;
import java.util.ArrayList;

import org.json.JSONObject;

public class Bill {
	private String billId;
	private String custId;
	private LocalDate purchasedDate;
	private double cost;
	private double gst;
	private String discountId;
	private double total;
	
	private ArrayList<ProductPurchased> myProducts;

	public Bill(String billId, String custId,LocalDate purchasedDate, double cost, double gst, String discountId, double total) {
		this.billId = billId;
		this.purchasedDate = purchasedDate;
		this.cost = cost;
		this.gst = gst;
		this.discountId = discountId;
		this.total = total;
		this.custId = custId;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public LocalDate getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(LocalDate purchasedDate) {
		this.purchasedDate = purchasedDate;
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

	public String getDiscountId() {
		return discountId;
	}

	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public ArrayList<ProductPurchased> getMyProducts() {
		return myProducts;
	}

	public void setMyProducts(ArrayList<ProductPurchased> myProducts) {
		this.myProducts = myProducts;
	}
	
	public String getCustId() {
		return custId;
	}
	
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public JSONObject getJson() {
		JSONObject jsonBill = new JSONObject();
		
		jsonBill.put("Bill Id",billId);
		jsonBill.put("PurchaseDate",purchasedDate);
		jsonBill.put("Cost",cost);
		jsonBill.put("Gst",gst);
		jsonBill.put("Discount",discountId);
		
		return jsonBill;
	}
}	
	
	

	