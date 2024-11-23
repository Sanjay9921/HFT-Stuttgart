package messaging.main;

import messaging.app.MessagingService;

public class Main {

	public static void main(String[] args) {
		MessagingService messagingService = new MessagingService();
		messagingService.sendMessage("Hello, World!");
	}

}
