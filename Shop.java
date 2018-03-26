package Classes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class Shop {
	private double money;
	private Set<String> voucherCodes;
	private Map<String, Map<Integer, Product>> products;
	private Map<Integer, ShopSupplier> suppliers;

	public Shop() {
		this.products = new HashMap<>();
		this.money = 1_000;
		this.suppliers = new HashMap<>();
	}

	// ----------------------------------------METHODS----------------------------------------

	// receive products from supplier
	public void receiveProductsFromShopSupplier(Map<String, Product> productsFromSupplier, ShopSupplier supplier) {
		if (productsFromSupplier != null && supplier != null) {

			for (Entry<String, Product> entry : productsFromSupplier.entrySet()) {
				if (entry.getKey() != null && entry.getValue() != null) {
					Product product = entry.getValue();

					// if the shop already has this product
					if (this.products.containsKey(product.getName())) {
						// if the product's id is the same as one of the products in the shop
						if (this.products.get(product.getName()).containsKey(product.getProductId())) {
							System.out.println("This exact product is already in the shop");
							return;
						} else {
							// if the product's id is different BUT the supplier is the same
							for (int productInShopId : this.products.get(product.getName()).keySet()) {
								if (this.suppliers.get(productInShopId).equals(supplier)) {
									this.products.get(product.getName()).get(productInShopId)
											.increaseQuantity(product.getQuantity());
									return;
								}
							}

							// at this point - the id is different and the supplier is different

							// we add the product with unique id and supplier to the products list
							this.products.get(product.getName()).put(product.getProductId(), product);
							// we link the product with its supplier
							this.suppliers.put(product.getProductId(), supplier);

						}

					} else {
						// otherwise, add it to the shop
						Map<Integer, Product> map = new HashMap<>();
						map.put(product.getProductId(), product);

						this.products.put(product.getName(), map);
						// add the products supplier, so he can receive his money when the product is
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

	public boolean checkVoucher(Voucher voucher) {
		if (voucher != null) {
			return this.voucherCodes.contains(voucher.getCode());
		}

		return false;
	}

	private void increaseMoney(double money) {
		if (money > 0) {
			this.money += money;
		}
	}
	
	// Chooses a supplier for the selected product.
	// Returns the productID of the selected product.
	int chooseSupplierOfProduct(String productName) throws InvalidProductNameException{
		if(productName==null || !products.containsKey(productName)){
			System.out.println("");
			throw new InvalidProductNameException("Invalid product name!");
		}
		Scanner in=new Scanner(System.in);
		int index=1;
		for(Product p:products.get(productName).values()){
			System.out.println("Index: "+(index++)+" "+ p);
		}
		int insertIndex=0;
		do{
			System.out.print("Pick a product index: ");
			insertIndex=in.nextInt();
		}while(insertIndex<1 || insertIndex>products.get(productName).values().size());
		index=1;
		int productID=0;
		for(Product p:products.get(productName).values()){
			if(index++==insertIndex){
				productID= p.getProductId();
			}
		}
		return productID;
	}
	
	void addToCart(Cart cart, String productName,int id){
		cart.addProduct(products.get(productName).get(id));
	}
	
	private boolean sell(Product product, User user, int quantity) {
		if (product != null) {
			if (decreaseQuantityOfProductInShop(product,quantity)) {
				increaseMoney(product.getPrice() * quantity*10/100);
			}else{ 
				System.out.println("Something went wrong.");
				return false;
			}
			user.removeMoneyForProduct(product.getPrice()*quantity);
			user.addProduct(product.clone(quantity));
			paySupplier(this.suppliers.get(product.getProductId()),product.getPrice()*quantity*90/100);
			return true;
		}
		return false;
	}
	
	//Sells the list of products to the user, depending on the quantities he/she set
	public boolean sell(List<Product> products, User user, int[] quantities) {
		if(products!=null && user!=null){
			boolean result=true;
			int indexOfQuantities=0;
			for(Product p: products){
				result = result && this.sell(p, user, quantities[indexOfQuantities++]);
			}
			return result;
		}
		return false;
	}
	private boolean sell(Product product, User user, int quantity, Voucher voucher){
		
		return false;
	}
	public boolean sell(List<Product> products2, User user, int[] quantities, Voucher voucher) {
				
		return false;
	}
	
	private boolean decreaseQuantityOfProductInShop(Product product,int quantity) {
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


	
}
