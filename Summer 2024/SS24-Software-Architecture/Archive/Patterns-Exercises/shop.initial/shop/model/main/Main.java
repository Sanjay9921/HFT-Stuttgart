package shop.model.main;

import shop.model.account.Account;
import shop.model.products.Product;

public class Main {
	
	public static void main(String[] args) throws InterruptedException{
		
		// Create the account
		Account account = new Account("John", "Doe", "555 555 123", "john.doe", "secret");
		account.login("john.doe", "secret");
		
		account.setDirectDebit("500600", "12345678");
		
		// Create and add some products
		account.add(new Product("download", "Java eBook", 9.99, 0));
		account.add(new Product("CD", "Some Music", 4.99, 1));
		account.add(new Product("BluRay", "A Movie", 8.99, 2));
		account.add(new Product("BluRay", "A Second Movie", 8.99, 2));
		account.add(new Product("Book", "Java Book", 6.99, 3));
		account.add(new Product("DVD", "Another Movie", 8.99, 4));
		account.add(new Product("CD", "Some Rare Music", 10.99, 5));
		account.add(new Product("Book", "Some Rare Book", 10.99, 6));
		
		// Add options
		account.addExpress();
		account.addTracking();
		
		// Complete the order
		account.complete();	
		
		// logout after some time
		Thread.sleep(10000);
		account.logout();
		System.out.println("logged out");		
	}
}
