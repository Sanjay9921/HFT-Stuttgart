package messaging.infrastructure;

/*
public class EmailMessageSender {
    public void send(String message) {
        System.out.println("Sending email message: " + message);
    }
}
*/

// TASK2 - Dependency Inversion
// Use this interface instead of the original EmailMessageSender wherever possible.
public class EmailMessageSender implements MessageSender{
    @Override
    public void send(String message){
        System.out.println("Sending email message: " + message);
    }
}
