
public class BankAccount {
	private String id;
	private double money;
	
		
	public BankAccount(String id, double money) {
		setId(id);
		setMoney(money);
	}
	//----------------------------------------METHODS----------------------------------------
	
	public synchronized boolean deposit(int money){
		if(money>0){
			this.money+=money;
			return true;
		}
		System.out.println("Invalid deposit amount!");
		return false;
	}
	public synchronized boolean withdraw(int money){
		if(this.money>=money){
			this.money-=money;
			return true;
		}
		System.out.println("Withdraw amount exceeds the limit!");
		return false;
	}

	//----------------------------------------SETTERS----------------------------------------
	
	private void setId(String id) {
		if(id!=null && id.trim().length()>0){
			this.id=id;
		}else{
			this.id="Invalid ID";
		}
	}

	private void setMoney(double money) {
		if(this.money>0){
			this.money = money;
		}else{
			this.money=0;
		}
	}

	//----------------------------------------GETTERS----------------------------------------
	
	public String getId() {
		return id;
	}

	public double getMoney() {
		return money;
	}
	
	
}
