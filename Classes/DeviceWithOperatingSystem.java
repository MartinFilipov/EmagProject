
package Classes;
public abstract class DeviceWithOperatingSystem extends Electronics{
	private String operatingSystem;

	public DeviceWithOperatingSystem(String name, double price, int quantity, String model, String operatingSystem) {
		super(name, price, quantity, model);
		setOperatingSystem(operatingSystem);
	}
	
	//----------------------------------------SETTERS----------------------------------------
	
	private void setOperatingSystem(String operatingSystem) {
		if (operatingSystem != null && operatingSystem.trim().length() > 0) {
			this.operatingSystem = operatingSystem;
		} else {
			this.operatingSystem = "Uknown";
		}
	}
	
	//----------------------------------------GETTERS----------------------------------------
	
	public String getOperatingSystem() {
		return operatingSystem;
	}

}
