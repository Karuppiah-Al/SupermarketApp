package supermarket;

import java.time.LocalDate;

public class Store {
	private String storeId;
	private LocalDate addedDate;
	private LocalDate expiryDate;
	private int Quantity;
	
	private String productId;
	// private Product product;
	
	public Store(String storeId, LocalDate addedDate, LocalDate expiryDate, int quantity, String productId) {
		this.storeId = storeId;
		this.addedDate = addedDate;
		this.expiryDate = expiryDate;
		Quantity = quantity;
		this.productId = productId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public LocalDate getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(LocalDate addedDate) {
		this.addedDate = addedDate;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	
	
	
}
