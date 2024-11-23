// TASK2 - Dependency Inversion
// Introduce dependency inversion by extracting an interface MessageSender.

package messaging.infrastructure;

public interface MessageSender {
    void send(String message);
}
