package sample.app.layer4.infrastructure.ui;

import java.util.Scanner;

import sample.app.layer3.application.ports.in.BusinessManager;

public class UserInteraction {
	
	private final BusinessManager mgr;
	
	public UserInteraction(BusinessManager mgr) {
		this.mgr = mgr;
	}

	public void run(){
		Scanner keyboard = new Scanner(System.in);
		String line;
		System.out.println("Enter an id bewtween 1 and 99 to be processed: (blank line for exiting) ");
		while(!(line = keyboard.nextLine()).isBlank()) {
			try {
				int id = Integer.parseInt(line);
				System.out.println("=== Processing ===");
				if(id >= 1 && id <= 99) {
					mgr.print(id);
					mgr.process(id);
					mgr.print(id);					
				} else {
					System.out.println("Please enter a valid number bewtween 1 and 99");
				}
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid number bewtween 1 and 99");
			}
			System.out.println("=== ========== ===");
		}
		System.out.println("Exiting");
		keyboard.close();
	}

}
