
public abstract class Medieval extends Product{

	private	static final int WARRANTY_PERIOD=6;
	
	public Medieval(String name, double price, int quantity) {
		super(name, price, quantity);
	}
	
	//----------------------------------------METHODS----------------------------------------
	
	public int getWarrantyPeriodInMonths(){
		return WARRANTY_PERIOD;
	}

}
