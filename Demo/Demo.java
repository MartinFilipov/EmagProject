package Demo;

import Classes.Armor;
import Classes.ArmorType;
import Classes.BankAccount;
import Classes.Laptop;
import Classes.Origin;
import Classes.Phone;
import Classes.Shop;
import Classes.ShopSupplier;
import Classes.User;
import Classes.Weapon;
import Classes.WeaponType;


public class Demo {
	public static void main(String[] args) {
		
		Shop shop=Shop.getInstance();
		ShopSupplier shopSupplier1 = new ShopSupplier(shop);
		ShopSupplier shopSupplier2 = new ShopSupplier(shop);
		ShopSupplier[] suppliers={shopSupplier1,shopSupplier2};
	
		for(int i=0;i<15;i++){
			ShopSupplier supplier=suppliers[(int)(Math.random()*2)];
			int chance=(int)(Math.random()*4);
			int quantity= (int)(Math.random()*10)+1;
			double price=200+500*Math.random();
			switch(chance){
			case 0: supplier.addProductToSupplyList(new Armor("Armor",price ,quantity, ArmorType.BODY_ARMOR, Origin.AMERICA));
				break;
			case 1: supplier.addProductToSupplyList(new Weapon("Weapon",price,quantity,WeaponType.AXE));
				break;
			case 2: supplier.addProductToSupplyList(new Laptop("Laptop", price, quantity, "Y510p", "Windows", 1200, 5000));
				break;
			case 3: supplier.addProductToSupplyList(new Phone("Phone", price, quantity, "Iphone5", true, "Android"));
				break;
			}
		}

		shopSupplier1.giveProductsToShop();
		shopSupplier2.giveProductsToShop();
		User user =new User(shop, new BankAccount("123843F", 10000),"New York","TestUser");
		user.chooseAProductToAddToCart();
		user.chooseAProductToAddToCart();
		user.chooseAProductToAddToCart();

		user.cashOut();
		System.out.println("User money: "+user.checkBalance());
		user.listWarranties();
		System.out.println("Shop profit: "+shop.getMoney());
		System.out.println("Supplier 1 money: "+shopSupplier1.getMoney());
		System.out.println("Supplier 2 money: "+shopSupplier2.getMoney());
		
	}
}
