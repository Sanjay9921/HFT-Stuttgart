package sample.asynchronous.producer;

import java.util.Scanner;

import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

// see https://stackoverflow.com/questions/1525385/the-meaning-of-noinitialcontextexception-error
// and : https://hevodata.com/learn/jms-queue/
public class JMSProducer {
	
	private static final String BROKER_URL = "tcp://localhost:61616";
	private static final String QUEUE_NAME = "dynamicQueues/myqueue";
	
	public static void main(String[] args) throws Exception {
		MessageService jms = new MessageService();
		Session session = jms.start(BROKER_URL, QUEUE_NAME);
		
		MessageProducer producer = jms.createProducer();

		System.out.println("Enter a new message / enter an empty line to close provider session");
		Scanner keyboard = new Scanner(System.in);
		
		String line;
		while(!(line = keyboard.nextLine()).isBlank()) {
			Message msg = session.createTextMessage(line);
//			System.out.println("Sending text '" + line + "'");
			producer.send(msg);
		}

		keyboard.close();
		System.out.println("Shutting down Producer");

		jms.stop();
	}

}