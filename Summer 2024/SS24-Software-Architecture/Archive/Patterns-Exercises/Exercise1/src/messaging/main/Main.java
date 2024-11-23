package messaging.main;

import messaging.app.MessagingService;

// TASK1
import messaging.infrastructure.EmailMessageSender;

// TASK2
import messaging.infrastructure.MessageSender;

public class Main {
    public static void main(String[] args) {
        /*
        MessagingService messagingService = new MessagingService();
        messagingService.sendMessage("Hello, World!");
        */
        
        // TASK1 - Dependency Injection
        // Update the Main class to create an instance of EmailMessageSender and pass it to the MessagingService constructor.
        /*
        EmailMessageSender emailMessageSender = new EmailMessageSender();
        MessagingService messagingService = new MessagingService(emailMessageSender);
        messagingService.sendMessage("Hello, World!");
        */
        
        // TASK2 - Dependency Inversion
        // Use this interface instead of the original EmailMessageSender wherever possible.
        MessageSender messageSender = new EmailMessageSender();
        MessagingService messagingService = new MessagingService(messageSender);
        messagingService.sendMessage("Hello, World!");
    }
}
