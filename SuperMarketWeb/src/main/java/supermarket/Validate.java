package supermarket;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {
	
	static boolean checkName(String s) {
		Pattern p = Pattern.compile("^[a-zA-Z\\s]+");
		Matcher m = p.matcher(s);
		if(m.matches()) {
			return true;
		}
		return false;
	}
	
	public static boolean checkPassword(String s) {
		if(s.length() < 8) {
			System.out.println("Length shoul greater that 8");
			return false;
		}
		return true;
	}
	
	static boolean isDouble(String num) {
		try {
			Double.parseDouble(num);
			return true;
		} catch (Exception e) {
			System.out.println("..plz  Enter a Number..");
			return false;
		}
	}
	public static boolean isNumber(String num) {
		try {
			Integer.parseInt(num);
			return true;
		} catch (Exception e) {
//			System.out.println("..plz  Enter a Number..");
			return false;
		}
	}
	
	static boolean isMobileNumber(String mobile) {
		Pattern p = Pattern.compile("^[6-9]\\d{9}$");
		Matcher m = p.matcher(mobile);
		if(m.matches()) {
			return true;
		}
		System.out.println("It is not a mobile number....");
		return false;
	}
	
	public static boolean checkMail(String s) {
		Pattern p = Pattern.compile("^(.+)@(.+)\\.(.+)");
		Matcher m = p.matcher(s);
		if(m.matches()) {
			return true;
		}
		System.out.println("It is not a Mail Id....");
		return false;
	}
	
	public static boolean checkWebsite(String s) {
		Pattern p = Pattern.compile("^(.+)\\.(.+)");
		Matcher m = p.matcher(s);
		if(m.matches()) {
			return true;
		}
		System.out.println("It is not a WEB SITE....");
		return false;
	}
	
	static boolean isMonth(String s) {
		try {
			Integer.parseInt(s);
		} catch (Exception e) {
			System.out.println("Enter a Number");
			return false;
		}
		int n = Integer.parseInt(s);
		if(n>0 && n<13) {
			return true;
		}
		return false;
	}
}
