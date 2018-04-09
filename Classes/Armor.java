package Classes;


public class Armor extends Medieval implements Testable, IArmor{

	private static final double CHANCE_OF_FORMING_RUST=0.05;
	private static final double CHANCE_OF_NOT_FITTING=0.10;
	private ArmorType armorType;
	private Origin origin;
	
	
	public Armor(String name, double price, int quantity, ArmorType armorType, Origin origin) {
		super(name, price, quantity);
		setArmorType(armorType);
		setOrigin(origin);
	}

	//----------------------------------------METHODS----------------------------------------
	@Override
	public boolean test() {
		return checkForRust()&&checkIfItFits();
	}

	@Override
	public boolean checkForRust() {
		if(Math.random()>CHANCE_OF_FORMING_RUST){
			System.out.println("Your "+armorType+" doesn't have any rust on it.");
			return true;
		}else{
			System.out.println("Your "+armorType+" is rusty.");
			return false;
		}
	}

	@Override
	public boolean checkIfItFits() {
		if(Math.random()>CHANCE_OF_NOT_FITTING){
			System.out.println("Your "+armorType+" fits perfectly.");
			return true;
		}else{
			System.out.println("Your "+armorType+" doesn't fit you at all.");
			return false;
		}
	} 
	
	@Override
	public Armor clone(int quantity) {
		return new Armor(this.getName(), this.getPrice(), quantity, this.getArmorType(), this.getOrigin());
	}
	
	//----------------------------------------SETTERS----------------------------------------
	private void setArmorType(ArmorType armorType) {
		if(armorType!=null){
			this.armorType = armorType;
		}else{
			this.armorType=ArmorType.BELT;
		}
	}


	private void setOrigin(Origin origin) {
		if(origin!=null){
			this.origin = origin;
		}else{
			this.origin=Origin.CHINA;
		}
	}
	//----------------------------------------GETTERS----------------------------------------
	public ArmorType getArmorType() {
		return armorType;
	}
	public Origin getOrigin() {
		return origin;
	}

	@Override
	public String toString() {
		return "Armor name: " + this.getName() + ", armor type: "+armorType+", origin: " + origin;
	}




	

}
