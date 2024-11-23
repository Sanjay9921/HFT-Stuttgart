package messaging.app;

import messaging.infrastructure.EmailMessageSender;

// TASK2 - Dependency Inversion
// Use this interface instead of the original EmailMessageSender wherever possible.
import messaging.infrastructure.MessageSender;

public class MessagingService {
    // private EmailMessageSender messageSender = new EmailMessageSender();
    /*
        public void sendMessage(String message) {
            messageSender.send(message);
        }
    */
    
    // TASK1 - Dependency Injection
    // Modify the MessagingService class to accept an instance of EmailMessageSender on construction.
    /*
    private final EmailMessageSender messageSender;
    
    public MessagingService(EmailMessageSender messagingSender){
        super();
        this.messageSender = messagingSender;
    }
    
    public void sendMessage(String message){
        this.messageSender.send(message);
    }
    */
    
    // TASK2 - Dependency Inversion
    // Use this interface instead of the original EmailMessageSender wherever possible.
    private final MessageSender messageSender;
    
    public MessagingService(MessageSender messageSender){
        this.messageSender = messageSender;
    }
    
    public void sendMessage(String message){
        messageSender.send(message);
    }
}