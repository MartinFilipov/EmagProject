
public class Weapon extends Medieval implements Testable, IWeapon{
	private static final double CHANCE_OF_BREAKING_WHEN_HIT_IN_THE_GROUND= 0.1;
	private static final double CHANCE_OF_BREAKING_WHILE_SWINGING = 0.08;

	private WeaponType weaponType;
	
	
	public Weapon(String name, double price, int quantity, WeaponType weaponType) {
		super(name, price, quantity);
		setWeaponType(weaponType);
	}

	//----------------------------------------METHODS----------------------------------------
	@Override
	public boolean test() {
		return hitTheGround() && swing();
	}
	
	public boolean hitTheGround(){
		System.out.println("You hit your "+weaponType+" in the ground.");
		if(Math.random()>CHANCE_OF_BREAKING_WHEN_HIT_IN_THE_GROUND){
			System.out.println("Your weapon is fine!");
			return true;
		}else{
			System.out.println("Your weapon broke.");
			return false;
		}
	}

	@Override
	public boolean swing() {
		System.out.println("You started swinging your "+weaponType+" in the air.");
		if(Math.random()>CHANCE_OF_BREAKING_WHILE_SWINGING){
			System.out.println("Your weapon is fine!");
			return true;
		}else{
			System.out.println("Your weapon broke.");
			return false;
		}
	}

	//----------------------------------------SETTERS----------------------------------------
	private void setWeaponType(WeaponType weaponType) {
		if(weaponType!=null){
			this.weaponType = weaponType;
		}else{
			this.weaponType=WeaponType.AXE;
		}
	}

	//----------------------------------------GETTERS----------------------------------------
	public WeaponType getWeaponType() {
		return weaponType;
	}

}
