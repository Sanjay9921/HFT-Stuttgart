package messaging.infrastructure;

import messaging.app.MessageSender;

public class EmailMessageSender implements MessageSender {

    @Override
	public void send(String message) {
        System.out.println("Sending email message: " + message);
    }
}
