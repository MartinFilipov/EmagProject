package Classes;

class DiscountGiver implements Runnable{
	private Shop shop;
	
	DiscountGiver(Shop shop) {
		this.shop = shop;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(15_000);
			this.shop.assignDiscounts();
		} catch (InterruptedException e) {
			return;
		}
	}

}
