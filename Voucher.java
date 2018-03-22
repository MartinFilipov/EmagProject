
public class Voucher {
	private String code;
	private int discount;	
	
	public Voucher(String code, int discount) {
		setCode(code);
		setDiscount(discount);
	}
	
	//----------------------------------------SETTERS----------------------------------------
	
	private void setCode(String code) {
		if(code!=null && code.trim().length()>0){
			this.code = code;
		}else{
			this.code="Invalid code";
		}
	}
	private void setDiscount(int discount) {
		if(discount >0 && discount<100){
			this.discount = discount;
		}else{
			this.discount=0;
		}
	}
	
	//----------------------------------------GETTERS----------------------------------------
	
	public int getDiscount() {
		return discount;
	}
	public String getCode() {
		return code;
	}
	
	
	
}
