package Classes;

import java.util.HashMap;
import java.util.Map;

public class ShopSupplier {
	private Shop shop;
	private Map<String, Product> supplyList;
	private double money;

	public ShopSupplier(Shop shop) {
		this.supplyList = new HashMap<>();
		this.shop = shop;
	}

	public void addProductToSupplyList(Product... products) {
		if (products != null) {

			for (Product product : products) {
				if (product != null) {
					if (!this.supplyList.containsKey(product.getName())) {
						this.supplyList.put(product.getName(), product);
					} else {
						this.supplyList.get(product.getName()).increaseQuantity(product.getQuantity());
					}
				}
			}
		}

	}

	public void giveProductsToShop() {
		if (this.supplyList.size() > 0) {

			this.shop.receiveProductsFromShopSupplier(new HashMap<String, Product>(supplyList), this);
			this.supplyList.clear();

		}
	}

	public void receivePaymentFromShop(double sum) {
		if (sum > 0) {
			this.money += sum;
		}
	}

	public double getMoney() {
		return money;
	}
	
}
