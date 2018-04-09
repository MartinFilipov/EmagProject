

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class User {
	private Shop shop;
	private BankAccount bankAccount;
	private Cart cart;
	private Set<Voucher> vouchers;
	private boolean isSubscribed;
	private List<Product> purchases;
	private List<Product> favourites;
	private List<Warranty> warranties;
	private List<Review> reviews;
	
	private boolean isLoggedIn;

	public User(Shop shop, BankAccount bankAccount,String address,String name) {
		this.shop = shop;
		this.bankAccount = bankAccount;
		this.purchases = new ArrayList<>();
		this.favourites = new ArrayList<>();
		this.warranties = new ArrayList<>();
		this.reviews = new ArrayList<>();
		this.cart=new Cart(shop, this, address, name);
		this.vouchers=new HashSet<>();
		this.isLoggedIn = false;
	}

	// ----------------------------------------METHODS----------------------------------------

	public void addToFavourites(Product product) {
		if (product != null) {

			if (!this.favourites.contains(product)) {
				this.favourites.add(product);
			} else {
				System.out.println("Product already in favourites");
				return;
			}

		}
	}

	public void cashOut() {

		if (this.vouchers.size() > 0) {
			System.out.println("Here are your vouchers:");

			int index = 1;
			for (Voucher voucher : this.vouchers) {
				System.out.println(index++ + " " + voucher);
			}

			int choice = -1;
			
			do {
				System.out.println("Choose a voucher to use or enter 0 if you do not want to use a voucher: ");
				choice = new Scanner(System.in).nextInt();
			} while (choice < 0 || choice > this.vouchers.size());

			if (choice > 0) {

				index = 1;
				Voucher theChosenVoucher = null;
				
				for (Voucher voucher : this.vouchers) {
					if (index++ == choice) {
						theChosenVoucher = voucher;
						break;
					}
				}
				
				if (this.cart.cashOutWithVoucher(theChosenVoucher)) {
					this.vouchers.remove(theChosenVoucher);
					return;
				}
			} 
		}
		
		this.cart.cashOut();

	}

	public void chooseAProductToAddToCart() {
		System.out.println("Enter a product name form the following");
		this.shop.showProducts();

		String name = new Scanner(System.in).nextLine();

		int productId;
		try {
			productId = this.shop.chooseSupplierOfProduct(name);
			this.shop.addToCart(this.cart, name, productId);
		} catch (InvalidProductNameException e) {
			System.out.println("You have entered an invalid name");
		}
	}

	public void addReview(Product product, String comment) {
		if (product != null && comment != null && comment.trim().length() > 0) {
			this.reviews.add(new Review(comment, product));
		}
	}

	public void compare(Product... products) {
		
	}

	public void subscribe() {
		if (!this.isSubscribed) {
			this.isSubscribed = true;
		} else {
			System.out.println("You are already subscribed");
		}
	}

	public void unsubscribe() {
		if (this.isSubscribed) {
			this.isSubscribed = false;
		} else {
			System.out.println("You are already unsubscribed");
		}
	}

	// ----------------------------------------METHODS----------------------------------------
	
	public void addProduct(Product product) {
		if (product != null) {
			this.purchases.add(product);
		}
	}
	
	public double checkBalance() {
		return this.bankAccount.getMoney();
	}

	public void showReviews() {
		for (Review review : this.reviews) {
			System.out.println(review);
		}
	}

	public void showFavourites() {
		for (Product favourite : this.favourites) {
			System.out.println(favourite);
		}
	}

	public void showWarranties() {
		for (Warranty warranty : this.warranties) {
			System.out.println(warranty);
		}
	}

	public void showPurchases() {
		for (Product purchase : this.purchases) {
			System.out.println(purchase);
		}
	}

	public void addVoucher(String voucherCode) {
		if (voucherCode != null && voucherCode.trim().length() == 10) {
			if (this.shop.checkVoucher(voucherCode)) {
				try {
					this.vouchers.add(this.shop.getVoucherByCode(voucherCode));
				} catch (NoSuchVoucherException e) {
					System.out.println("You've entered an invalid voucher code");
					return;
				}
			}
		}
	}
	
	void addWarranty(Warranty warranty){
		if(warranty!=null){
			this.warranties.add(warranty);
		}else{
			System.out.println("Invalid warranty.");
		}
	}
	
	public void listWarranties(){
		for(Product p: purchases){
			System.out.println(p);
			if(p.checkWarranty()){
				System.out.print("\t");
				p.printWarranty();
			}else{
				System.out.println("\tDoesn't have a valid warranty.");
			}
		}
	}
	
	// ----------------------------------------METHODS----------------------------------------

	public void setBankAccount(BankAccount bankAccount) {
		if (bankAccount != null) {
			this.bankAccount = bankAccount;
		}
	}

	public void removeMoneyForProduct(double d) {
		this.bankAccount.withdraw(d);
	}
	
	public void register() {
		this.shop.registerUser();
	}
	
	public void logIn() {
		if (this.shop.logInUser()) {
			this.isLoggedIn = true;
		}
	}
	
	public void logOut() {
		this.isLoggedIn = false;
	}

}
