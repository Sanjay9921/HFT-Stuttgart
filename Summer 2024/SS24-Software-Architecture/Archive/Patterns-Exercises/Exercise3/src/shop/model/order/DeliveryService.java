package shop.model.order;

public class DeliveryService implements Runnable {
	
	private static final int ONE_DAY = 2000;
	private Order order;
		
	public DeliveryService(Order order){
		this.order = order; 
	}
	
	public void start(){
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {

		int day = 0;
		for(; day <= order.getLatestDeliveryDate(); day++){
			try { Thread.sleep(ONE_DAY); } catch (InterruptedException e) {}
			order.deliver(day);			
		}

		day--;
		order.delivered(" after " + day + " days");
	}
}
