package messaging.app;

import messaging.infrastructure.EmailMessageSender;

public class MessagingService {

	private EmailMessageSender messageSender = new EmailMessageSender();

	public void sendMessage(String message) {
		messageSender.send(message);
	}
}