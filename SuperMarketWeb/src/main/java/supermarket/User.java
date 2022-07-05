package supermarket;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class User {
	private String UserId;
	private LocalDate createdDate;
	private String mobile;
	private String name;
	public User(String userId,LocalDate date,String mobile,String name) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.createdDate = date;
		this.UserId = userId;
		this.mobile = mobile;
		this.name = name;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public String getName() {
		return name;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
