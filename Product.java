

public abstract class Product {
	private static int id = 0;
	private int productId;
	private String name;
	private double price;
	private int quantity;
	private Warranty warranty;
	
	public Product(String name, double price, int quantity) {
		setName(name);
		setPrice(price);
		setQuantity(quantity);
		productId = ++id;
	}
	
	//----------------------------------------METHODS----------------------------------------

	abstract Product clone(int quantity);
	public abstract int getWarrantyPeriodInMonths();
	
	public void reduceQuantity(int quantity){
		if(this.quantity<quantity || quantity<=0){
			System.out.println("Invalid reduction amount.");
		}else{
			this.quantity-=quantity;
		}
	}
	
	public void increaseQuantity(int quantity){
		if (quantity > 0) {
			this.quantity += quantity;
		}
	}
	public boolean checkWarranty(){
		if(warranty!=null){
			return warranty.isValid();
		}
		return false;
	}
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

	void setWarranty(Warranty warranty) {
		if (warranty != null) {
			this.warranty = warranty;
		} else {
			this.warranty = null;
		}
	}
	
	void printWarranty(){
		if(warranty!=null){
			System.out.println(warranty);
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
	
	public int getProductId() {
		return productId;
	}
	
		
}
