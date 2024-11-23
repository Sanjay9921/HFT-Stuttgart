package sample.asynchronous.consumer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

// see https://stackoverflow.com/questions/1525385/the-meaning-of-noinitialcontextexception-error
// and : https://hevodata.com/learn/jms-queue/
public class MessageService {
	
	private boolean running = false;
	private BrokerService broker;
	private Connection connection;
	private Queue queue;
	private Session session;
	
	private BrokerService createBroker(String brokerUrl) {
		BrokerService broker = null;
		try {
			broker = BrokerFactory.createBroker(new URI("broker:(" + brokerUrl + ")"));
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (Exception e) {
//			e.printStackTrace();
			System.err.println("Broker already running");
		}
		return broker;
	}
	
	private InitialContext createInitalContext(String brokerUrl) {
		try {
			Properties props = new Properties();
			props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
			props.setProperty(Context.PROVIDER_URL, brokerUrl);
			InitialContext context = new InitialContext(props);
			return context;
		} catch (NamingException e) {
			e.printStackTrace();
			System.exit(0);
		}
		return null;
	}

	private Connection createQueueConnection(InitialContext context) {
		try {
			QueueConnectionFactory connFactory = (QueueConnectionFactory)context.lookup("ConnectionFactory");        
			Connection connection = connFactory.createQueueConnection();
			connection.start();
			return connection;
		} catch (NamingException | JMSException e) {
			e.printStackTrace();
			System.exit(0);
		}
		return null;
	}
	
	public Session start(String brokerUrl, String queueName) {
		try {
			broker = createBroker(brokerUrl);
			if(broker != null) broker.start();		
			InitialContext ctx = createInitalContext(brokerUrl);
			connection = createQueueConnection(ctx);
			
			queue = (Queue) ctx.lookup(queueName);  		
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			running = true;
			return session;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public MessageConsumer createConsumer() {
		try {
			if(running) return session.createConsumer(queue);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

	public MessageProducer createProducer() {
		try {
			if(running) return session.createProducer(queue);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void stop() {
		try {
			session.close();
			connection.close();
			if(broker != null) broker.stop();
			
			session = null;
			connection = null;
			broker = null;
			queue = null;
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
