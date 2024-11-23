package messaging.main;

import messaging.app.MessagingService;
import messaging.infrastructure.EmailMessageSender;

public class Main {

	public static void main(String[] args) {
		EmailMessageSender messageSender = new EmailMessageSender();
		MessagingService messagingService  = new MessagingService(messageSender);
		messagingService.sendMessage("Hello, World!");
	}

}
