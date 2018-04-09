package Classes;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;

public class Shop {
	private static final int MIN_DISCOUNT_IN_PERCENTAGE = 10;
	private static final int DIFFERENCE_BETWEEN_MAX_AND_MIN_DISCOUNT = 11;
	private static final int NUMBER_OF_VOUCHERS = 100;
	private static final int NUMBER_OF_DIGITS_OF_VOUCHER = 10;
	private static final double SHOP_PROFIT_COEFF = 0.1;
	private static final double SUPPLIER_PROFIT_COEFF = 0.9;
	
	private Map<String, Integer> users;
	
	private double money;
	private Map<String, Voucher> voucherCodes;
	private Map<String, Map<Integer, Product>> products;
	private List<Product> discountedProducts;
	private Map<Integer, ShopSupplier> suppliers;

	public Shop() {
		this.discountedProducts = new ArrayList<>();
		this.users = new HashMap<>();
		this.products = new HashMap<>();
		this.money = 1_000;
		this.suppliers = new HashMap<>();
		this.voucherCodes=new HashMap<>();
		generateVouchers();
		loadUsers();
	}

	// ----------------------------------------METHODS----------------------------------------
	
	//generates NUMBER_OF_VOUCHERS number of vouchers with random code
	private void generateVouchers() {
		for (int voucherNumber = 0; voucherNumber < NUMBER_OF_VOUCHERS; voucherNumber++) {
			
			String voucherCode = generateVoucherCode();
			int voucherDiscount = (int) (Math.random() * DIFFERENCE_BETWEEN_MAX_AND_MIN_DISCOUNT + MIN_DISCOUNT_IN_PERCENTAGE);
			
			Voucher voucher = new Voucher(voucherCode, voucherDiscount);
			this.voucherCodes.put(voucherCode, voucher);
		}
	}
	
	//generates a random 10 digit code made up of alternating lower-case and upper-case letters
	private String generateVoucherCode() {
		StringBuilder code = new StringBuilder("");
		
		for (int digit = 0; digit < NUMBER_OF_DIGITS_OF_VOUCHER / 2; digit++) {
			char lowerCaseLetter = (char)(int)(Math.random() * ('z' - 'a' + 1) + 'a');
			char upperCaseLetter = (char)(int)(Math.random() * ('Z' - 'A' + 1) + 'A');
			code.append(lowerCaseLetter);
			code.append(upperCaseLetter);
		}
		
		return code.toString();
	}
	
	public void showProducts() {
		for (String name : this.products.keySet()) {
			double price=this.products.get(name).values().iterator().next().getPrice();
			System.out.println(name+", price: "+price);
		}
	}

	// receive products from supplier
	void receiveProductsFromShopSupplier(Map<String, Product> productsFromSupplier, ShopSupplier supplier) {
		if (productsFromSupplier != null && supplier != null) {

			for (Entry<String, Product> entry : productsFromSupplier.entrySet()) {
				if (entry.getKey() != null && entry.getValue() != null) {
					Product product = entry.getValue();

					// if the shop already has this product
					if (this.products.containsKey(product.getName())) {
						// if the product's id is the same as one of the
						// products in the shop
						if (this.products.get(product.getName()).containsKey(product.getProductId())) {
							System.out.println("This exact product is already in the shop");
							return;
						} else {
							// if the product's id is different BUT the supplier
							// is the same
							for (int productInShopId : this.products.get(product.getName()).keySet()) {
								if (this.suppliers.get(productInShopId).equals(supplier)) {
									this.products.get(product.getName()).get(productInShopId)
											.increaseQuantity(product.getQuantity());
									return;
								}
							}

							// at this point - the id is different and the
							// supplier is different

							// we add the product with unique id and supplier to
							// the products list
							this.products.get(product.getName()).put(product.getProductId(), product);
							// we link the product with its supplier
							this.suppliers.put(product.getProductId(), supplier);

						}

					} else {
						// otherwise, add it to the shop
						Map<Integer, Product> map = new HashMap<>();
						map.put(product.getProductId(), product);

						this.products.put(product.getName(), map);
						// add the products supplier, so he can receive his
						// money when the product is
						// sold
						this.suppliers.put(product.getProductId(), supplier);

					}

				}

			}

		}

	}

	private void paySupplier(ShopSupplier supplier, double sum) {
		if (supplier != null && sum > 0) {
			supplier.receivePaymentFromShop(sum);
		}
	}

	public boolean checkVoucher(String voucherCode) {
		if (voucherCode != null) {
			return this.voucherCodes.containsKey(voucherCode);
		}

		return false;
	}
	
	Voucher getVoucherByCode(String voucherCode) throws NoSuchVoucherException {
		if (voucherCode != null && voucherCode.trim().length() == 10) {
			if (this.voucherCodes.containsKey(voucherCode)) {
				return this.voucherCodes.get(voucherCode);
			}
		}
		
		throw new NoSuchVoucherException("No such voucher!");
	}

	private void increaseMoney(double money) {
		if (money > 0) {
			this.money += money;
		}
	}

	// Chooses a supplier for the selected product.
	// Returns the productID of the selected product.
	int chooseSupplierOfProduct(String productName) throws InvalidProductNameException {
		if (productName == null || !products.containsKey(productName)) {
			System.out.println("");
			throw new InvalidProductNameException("Invalid product name!");
		}
		Scanner in = new Scanner(System.in);
		int index = 1;
		for (Product p : products.get(productName).values()) {
			System.out.println("Index: " + (index++) + " " + p +", price: "+p.getPrice());
		}
		int insertIndex = 0;
		do {
			System.out.print("Pick a product index: ");
			insertIndex = in.nextInt();
		} while (insertIndex < 1 || insertIndex > products.get(productName).values().size());
		index = 1;
		int productID = 0;
		for (Product p : products.get(productName).values()) {
			if (index++ == insertIndex) {
				productID = p.getProductId();
			}
		}
		return productID;
	}

	void addToCart(Cart cart, String productName, int id) {
		cart.addProduct(products.get(productName).get(id));
	}


	// Sells the list of products to the user, depending on the quantities
	// he/she set
	// Calls an individual overloaded sell method to deal with every product
	// separately
	boolean sell(List<Product> products, User user, int[] quantities) {
		if (products != null && user != null) {
			boolean result = true;
			int indexOfQuantities = 0;
			for (Product p : products) {
				result = result && this.sell(p, user, quantities[indexOfQuantities++]);
			}
			return result;
		}
		return false;
	}
	
	private boolean sell(Product product, User user, int quantity) {
		if (product != null) {
			double retailPrice = (product.getPrice() * quantity);
			if (decreaseQuantityOfProductInShop(product, quantity)) {
				increaseMoney(retailPrice * SHOP_PROFIT_COEFF);
			} else {
				System.out.println("Something went wrong.");
				return false;
			}
			
			user.removeMoneyForProduct(retailPrice);
			Product userProduct=product.clone(quantity);
			Warranty userProductWarranty=new Warranty(userProduct);
			userProduct.setWarranty(userProductWarranty);
			user.addWarranty(userProductWarranty);
			user.addProduct(userProduct);
			paySupplier(this.suppliers.get(product.getProductId()), retailPrice * SUPPLIER_PROFIT_COEFF);
			
			return true;
		}
		return false;
	}
	
	// Sells the list of products to the user, depending on the quantities
	// he/she set and the entered voucher
	// Calls an individual overloaded sell method to deal with every product
	// separately
	boolean sell(List<Product> products, User user, int[] quantities, Voucher voucher) {
		if (products != null && user != null) {
			boolean result = true;
			int indexOfQuantities = 0;
			for (Product p : products) {
				result = result && this.sell(p, user, quantities[indexOfQuantities++], voucher);
			}
			return result;
		}
		return false;
	}

	private boolean sell(Product product, User user, int quantity, Voucher voucher) {
		if (product != null) {
			double retailPrice = (product.getPrice() * quantity);
			double retailPriceWithDiscount = retailPrice * (100 - voucher.getDiscount()) / 100;
			if (decreaseQuantityOfProductInShop(product, quantity)) {
				increaseMoney(retailPriceWithDiscount * SHOP_PROFIT_COEFF);
			} else {
				System.out.println("Something went wrong.");
				return false;
			}
			user.removeMoneyForProduct(retailPriceWithDiscount);
			Product userProduct=product.clone(quantity);
			Warranty userProductWarranty=new Warranty(userProduct);
			userProduct.setWarranty(userProductWarranty);
			user.addWarranty(userProductWarranty);
			user.addProduct(userProduct);
			paySupplier(this.suppliers.get(product.getProductId()), retailPriceWithDiscount * SUPPLIER_PROFIT_COEFF);
			return true;
		}
		return false;
	}
	
	
	//decreases the quantity of the product in the shop by the selected amount
	private boolean decreaseQuantityOfProductInShop(Product product, int quantity) {
		if (product != null) {
			if (this.products.get(product.getName()).get(product.getProductId()).getQuantity() >= quantity) {
				this.products.get(product.getName()).get(product.getProductId()).reduceQuantity(quantity);
				return true;
			} else {
				System.out.println("Not enough of this product in the shop");
			}
		}
		return false;
	}
	
	public double getMoney(){
		return money;
	}
	
	//----------------------------------------------------------------------------------------------------
	
	// loads the users from a file
	private void loadUsers() {
		try (Scanner sc = new Scanner(new File("registeredUsers.txt"));) {
			
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] usernamePassword = line.split(",");
				this.users.put(usernamePassword[0], Integer.parseInt(usernamePassword[1]));
			}
						
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			return;
		}
	}
	
	// checks if a username exists
	private boolean usernameTaken(String username) {
		if (username != null) {
			
			try (Scanner sc = new Scanner(new File("registeredUsers.txt"));) {
				
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					String[] usernamePassword = line.split(",");
					
					if (username.equals(usernamePassword[0])) {
						System.out.println("Username taken!");
						return true;
					}
				}
				
			} catch (FileNotFoundException e) {
				System.out.println("File not found");
			}
			
		}
		
		return false;
	}
	
	// checks if a username/password is valid length
	private boolean isValidLength(String specifier) {
		if (specifier != null) {
			if (specifier.length() >= 6) {
				return true;
			} else {
				System.out.println(specifier + " should be at least 6 characters");
			}
		}
		return false;
	}
	
	// registers a user and saves the information in a file
	void registerUser() {
		Scanner sc = new Scanner(System.in);
		
		String username;
		
		do {
			System.out.print("Enter username: ");
			username = sc.nextLine();
		} while (usernameTaken(username) || !isValidLength(username));
		
		String password;
		
		do {
			System.out.print("Enter password: ");
			password = sc.nextLine();
		} while (!isValidLength(password));
		
		try (FileWriter file = new FileWriter("registeredUsers.txt", true);
				PrintWriter out = new PrintWriter(file);) {
			
			out.print(username + ",");
			out.println(password.hashCode());
			
		} catch (IOException e) {
			return;
		}
	}
	
	// logs in a user with correct username and password
	boolean logInUser() {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter username: ");
		String username = sc.nextLine();
		
		if (!this.users.containsKey(username)) { 
			System.out.println("Invalid username");
			return false;
		}
		
		System.out.print("Enter password: ");
		String password = sc.nextLine();
		int hashedPassword = password.hashCode();
				
		if (this.users.get(username) != hashedPassword) {	
			System.out.println("Invalid password");
			return false;
		}
		
		return true;
		
	}
	
	private void clearDiscounts() {
		for (Product p : this.discountedProducts) {
			p.assignDiscount(0);
		}
		this.discountedProducts.clear();
	}

	public void assignDiscounts() {
		clearDiscounts();
		
		int numberOfProducts = this.products.size();
		int numberOfDiscounts = (int) (numberOfProducts * 0.1);
		
		int numberOfDiscountedProducts = 0;
		for (Entry<String, Map<Integer, Product>> entry : this.products.entrySet()) {
			
			Map<Integer, Product> suppliers = entry.getValue();
			
			int numberOfSuppliers = suppliers.size();
			int randomSupplier = new Random().nextInt(numberOfSuppliers);
			
			int index = 0;
			for (Product p : suppliers.values()) {
				if (index == randomSupplier) {
					p.assignDiscount(new Random().nextInt(20) + 1);
					discountedProducts.add(p);
					break;
				}
			}
			
			if (numberOfDiscountedProducts++ >= numberOfDiscounts) {
				return;
			}
			
		}
	}
	
// Here for testing purposes
//	public void printVoucherCodes(){
//		for(String s: voucherCodes.keySet()){
//			System.out.println(s);
//		}
//	}
}
