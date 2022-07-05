package supermarket;

import java.time.LocalDate;

public class Customer  extends User{
	private String customerId;
	private int visitedCount;
	private String password;
	
	public Customer(String userId, LocalDate date,String customerId,String mobile,String customerName) {
		super(userId, date, mobile, customerName);
		this.customerId = customerId; 
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCustomerId() {
		return customerId;
	}
	public int getVisitedCount() {
		return visitedCount;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public void setVisitedCount(int visitedCount) {
		this.visitedCount = visitedCount;
	}

}
