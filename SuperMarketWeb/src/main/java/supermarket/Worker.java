package supermarket;

import java.time.LocalDate;

public class Worker extends User {
	
	private String password;
	private String workerId;
	public Worker(String userId, LocalDate date, String mobile, String workerId,String name,String password) {
		super(userId, date, mobile, name);
		this.password = password;
		this.workerId = workerId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getWorkerId() {
		return workerId;
	};
	
	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}
	
}
