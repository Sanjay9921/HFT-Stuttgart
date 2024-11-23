package messaging.app;

public class MessagingService {
	
    private final MessageSender messageSender;

    public MessagingService(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	public void sendMessage(String message) {
        messageSender.send(message);
    }
}