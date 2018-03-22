
public class DeliveryDetails {
	private String adress;
	private String name;
	
	public DeliveryDetails(String adress, String name) {
		setAdress(adress);
		setName(name);
	}

	//----------------------------------------SETTERS----------------------------------------
	
	private void setAdress(String adress) {
		if(adress!=null && adress.trim().length()>0){
			this.adress = adress;
		}else{
			this.adress="Invalid adress";
		}
	}

	private void setName(String name) {
		if(name!=null && name.trim().length()>0){
			this.name = name;
		}else{
			this.name="Invalid name";
		}
	}
	
	//----------------------------------------GETTERS----------------------------------------
	

	public String getAdress() {
		return adress;
	}

	public String getName() {
		return name;
	}

}
