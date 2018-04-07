
public class Review {
	private String comment;
	private Product product;
	
	public Review(String comment, Product product) {
		setComment(comment);
		setProduct(product);
	}
	
	private void setComment(String comment) {
		if (comment != null && comment.trim().length() > 0) {
			this.comment = comment;
		}
	}
	
	private void setProduct(Product product) {
		if (product != null) {
			this.product = product;
		}
	}
	
	public String getComment() {
		return comment;
	}
	
	public Product getProduct() {
		return product;
	}
	
}
