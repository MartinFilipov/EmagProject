import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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

}
