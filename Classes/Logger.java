package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

class Logger extends Thread {
	private Shop shop;
	
	Logger(Shop shop){
		this.shop=shop;
		setDaemon(true);
	}
	@Override
	public void run() {
		File prodavaniq = new File("ProdadenaStoka.txt");

		while (true) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			List<Product> products=shop.getSoldProducts();
			try (PrintWriter out = new PrintWriter(prodavaniq)) {
				int index=1;
				for(Product p: products){
					out.println((index++) +": "+ p);
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}

	}

}
