package supermarket;

import org.json.JSONObject;

public class Authentication {
	public static JSONObject isWorkerFound(String mobile,String password) {
		JSONObject workerJsonObject = new JSONObject();
		
		if(!SuperMarketStorage.isWorkerExist(mobile)) {
			workerJsonObject.put("status", "failed");
			workerJsonObject.put("info", "Account not found");
			return workerJsonObject;
		}
		if(SuperMarketStorage.isWorkerExist(mobile, password)) {
			workerJsonObject.put("status", "ok");
			workerJsonObject.put("info", "Account found - PasswrodMatched");
			System.out.println("ACCOUNT FOUND");
			return workerJsonObject;
		}
		System.out.println();
		System.out.println("PASSWORD MISMATCH....");
		workerJsonObject.put("status", "failed");
		workerJsonObject.put("info", "password mismatch");
		return workerJsonObject;
	}
	
	public static JSONObject isWorkerFound(String mobile) {
		JSONObject workerJsonObject = new JSONObject();
		if(SuperMarketStorage.isWorkerExist(mobile)) {
			workerJsonObject.put("status", "You are already an User");
			return workerJsonObject;
		}
		workerJsonObject.put("status","not-found");
		return workerJsonObject;
	}
	
	
	public static Customer isCustomerExist(String mobile) {		
		return SuperMarketStorage.isCustomerExist(mobile);
	}
	public static Customer isCustomerExist(String mobile,String password) {
		return SuperMarketStorage.isCustomerExist(mobile, password);
	}
}
