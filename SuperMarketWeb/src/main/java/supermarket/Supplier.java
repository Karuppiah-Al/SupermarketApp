package supermarket;

public class Supplier {
	private String supplierId;
	private String mail;
	private String name;
	private String website;
	private String categoryId;
//	private Category category;
	
	public Supplier(String supplierId, String mail, String name, String website, String categoryId) {
		super();
		this.supplierId = supplierId;
		this.mail = mail;
		this.name = name;
		this.website = website;
		this.categoryId = categoryId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	
}
