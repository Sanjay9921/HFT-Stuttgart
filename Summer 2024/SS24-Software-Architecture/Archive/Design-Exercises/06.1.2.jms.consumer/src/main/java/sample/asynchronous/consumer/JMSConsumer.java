package sample.asynchronous.consumer;

import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

// see https://stackoverflow.com/questions/1525385/the-meaning-of-noinitialcontextexception-error
// and : https://hevodata.com/learn/jms-queue/
public class JMSConsumer {
	
	private static final String BROKER_URL = "tcp://localhost:61616";
	private static final String QUEUE_NAME = "dynamicQueues/myqueue";
	
	public static record ConsumerMessageListener(String consumerName) implements MessageListener {

		public void onMessage(Message message) {
			TextMessage textMessage = (TextMessage) message;
			try {
				System.out.println(consumerName + " received: '" + textMessage.getText() + "'");
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {		
		MessageService jms = new MessageService();
		jms.start(BROKER_URL, QUEUE_NAME);

        MessageConsumer consumer = jms.createConsumer();
        consumer.setMessageListener(new ConsumerMessageListener("Consumer"));

		System.out.println("Press 'Enter' to close consumer session");
		Scanner keyboard = new Scanner(System.in);
		keyboard.nextLine();
		keyboard.close();

		System.out.println("Shutting down Consumer");
		jms.stop();
	}
}