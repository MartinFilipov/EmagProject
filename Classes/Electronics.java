
package Classes;
public abstract class Electronics extends Product{
	private static final int WARRANTY_FOR_ELECTRONICS = 24;
	
	private String model;
	
	public Electronics(String name, double price, int quantity, String model) {
		super(name, price, quantity);
		setModel(model);
	}

	@Override
	public int getWarrantyPeriodInMonths() {
		return WARRANTY_FOR_ELECTRONICS;
	}
	
	//----------------------------------------SETTERS----------------------------------------

	private void setModel(String model) {
		if (model != null && model.trim().length() > 0) {
			this.model = model;
		} else {
			this.model = "Unknown";
		}
	}
	
	//----------------------------------------GETTERS----------------------------------------
	
	public String getModel() {
		return model;
	}

}
