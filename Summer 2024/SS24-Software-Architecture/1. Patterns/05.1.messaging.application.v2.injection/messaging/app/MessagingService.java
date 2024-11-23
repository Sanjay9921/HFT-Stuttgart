package messaging.app;

import messaging.infrastructure.EmailMessageSender;

public class MessagingService {
	
    private final EmailMessageSender messageSender;

    public MessagingService(EmailMessageSender messageSender) {
		this.messageSender = messageSender;
	}

	public void sendMessage(String message) {
        messageSender.send(message);
    }
}