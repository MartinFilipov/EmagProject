package Classes;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Cart {
	private Shop shop;
	private User user;
	private List<Product> products;
	private int quantities[];
	private DeliveryDetails deliveryDetails;

	Cart(Shop shop, User user, String adress, String name) {
		if (shop != null) {
			this.shop = shop;
		} else {
			System.out.println("Invalid shop!");
		}
		if (user != null) {
			this.user = user;
		} else {
			System.out.println("Invalid user!");
		}
		this.products = new LinkedList<>();
		this.deliveryDetails = new DeliveryDetails(adress, name);
	}

	// ----------------------------------------METHODS----------------------------------------
	private void setQuantityOfProducts(){
		quantities=new int[products.size()];
		Scanner in=new Scanner(System.in);
		int index=0;
		for(Product p:products){
			System.out.println(p);
			int quantity=0;
			do{
				System.out.print("Insert quantity between 1: "+p.getQuantity());
				quantity=in.nextInt();
			}while(quantity<1 || quantity>p.getQuantity());
			quantities[index++]=quantity;
		}
	}
	
	boolean cashOut() {
		setQuantityOfProducts();
		double totalSum = 0;
		int index=0;
		for (Product product : products) {			
			totalSum += (product.getPrice() * quantities[index++]);
		}
		// checkBalance() returns amount of money from user BankAccount
		if (totalSum > user.checkBalance()) {
			System.out.println("Not enough money, please remove items.");
			return false;
		}

		if(shop.sell(products, user, quantities)){
			products.clear();
			return true;
		}
		return false;
	}

	boolean cashOutWithVoucher(Voucher voucher) {
		if(!shop.checkVoucher(voucher)){
			System.out.println("Invalid voucher!");
			return false;
		}
		setQuantityOfProducts();
		double totalSum = 0;
		int index=0;
		for (Product product : products) {
			totalSum += (product.getPrice()* quantities[index++]);
		}
		
		// checkBalance() returns amount of money from user BankAccount
		if (totalSum*(100-voucher.getDiscount())/100  > user.checkBalance()) {
			System.out.println("Not enough money, please remove items.");
			return false;
		}
		
		// Shopa trqbva da go prodade izpolzvaiki voucher.
		if(shop.sell(products, user, quantities, voucher)){
			products.clear();
			return true;
		}			
		return false;
	}

	boolean addProduct(Product product) {
		if (product != null) {
			synchronized(product){
				this.products.add(product);
				return true;
			}
		}
		return false;
	}

	boolean removeProduct(Product product) {
		if (product != null) {
			synchronized(product){
				this.products.remove(product);
				return true;
			}
		}
		return false;
	}

	// ----------------------------------------GETTERS----------------------------------------

	DeliveryDetails getDeliveryDetails() {
		return new DeliveryDetails(deliveryDetails.getAdress(),deliveryDetails.getName());
	}
	
}
