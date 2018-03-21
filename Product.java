
public abstract class Product {
	
	private String name;
	private double price;
	private int quantity;
	private Warranty warranty;
	
	public Product(String name, double price, int quantity) {
		setName(name);
		setPrice(price);
		setQuantity(quantity);
	}
	
	//----------------------------------------METHODS----------------------------------------
	
	public abstract int getWarrantyPeriodInMonths();
	
	//----------------------------------------SETTERS----------------------------------------

	private void setName(String name) {
		if (name != null && name.trim().length() > 0) {
			this.name = name;
		} else {
			this.name = "Unknown";
		}
	}

	private void setPrice(double price) {
		this.price = price > 0 ? price : 0;
	}

	private void setQuantity(int quantity) {
		this.quantity = quantity > 0 ? quantity : 0;
	}

	private void setWarranty(Warranty warranty) {
		if (warranty != null) {
			this.warranty = warranty;
		} else {
			this.warranty = null;
		}
	}

	//----------------------------------------GETTERS----------------------------------------

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}
	
}
