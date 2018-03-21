import java.util.Date;

public class Warranty {
	private static final long MILISECONDS_IN_A_MONTH = (long) (2.62974383 * Math.pow(10, 9));
	private Product product;
	private Date endDate;

	Warranty(Product product) {
		if (product != null) {
			this.product = product;
			this.endDate = new Date(
					System.currentTimeMillis() + (product.getWarrantyPeriodInMonths() * MILISECONDS_IN_A_MONTH));
		} 
		else {
			System.out.println("Invalid product, can't make a warranty.");
		}
	}

	// ----------------------------------------GETTERS----------------------------------------

	public Date getEndDate() {
		return endDate;
	}

	@Override
	public String toString() {
		return "Warranty [product=" + product + ", endDate=" + endDate + "]";
	}

}
