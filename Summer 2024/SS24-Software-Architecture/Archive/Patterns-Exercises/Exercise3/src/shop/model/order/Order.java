package shop.model.order;

import java.util.ArrayList;
import java.util.List;

import shop.model.products.StockedProduct;

public class Order{

	private static final int EXPRESS_DAYS = 2;
	private boolean standard = false;
	private boolean express = false;
	private boolean tracking = false;	
	private DeliveryService service;

	private List<StockedProduct>  products = new ArrayList<>();

	public Order(){
		service = new DeliveryService(this);
	}
	
	public void setShipment() {
		standard = true;
	}

	public void setExpress() {
		express = true;
	}

	public void setTracking() {
		tracking = true;
	}

	public List<StockedProduct> getProducts(){
		return products;
	}

	public int getCosts() {
		int costs = 0;

		if(standard)
			costs = costs + 300;
		
		if(express)
			costs = costs + 400;
		
		if(tracking)
			costs = costs + 200;

		for(StockedProduct p : products)
			costs = costs + p.getPrice();
		return costs;
	}

	public int getLatestDeliveryDate() {
		int delivery = 0;
		for(StockedProduct p : products)
			if(p.getDeliveryPeriod() > delivery)
				delivery = p.getDeliveryPeriod();
		
		if(express){
			if(delivery == 1 || delivery == 2)
				delivery = 1;
			else if(delivery > 2)
				delivery = delivery - EXPRESS_DAYS;
		}
		
		return delivery;
	}

	public boolean isEmpty() {
		return products.isEmpty();
	}

	public boolean isPhysical() {
		if(products.isEmpty())
			return false;
		for(StockedProduct p : products)
			if(p.isPhysical())
				return true;
		return false;
	}

	public boolean isDigital() {
		if(products.isEmpty())
			return false;
		for(StockedProduct p : products)
			if(p.isPhysical())
				return false;
		return true;
	}

	public void add(StockedProduct product) {
		this.products.add(product);
	}

	public void deliver() {
		System.out.println(listing("Sent out:", "-", products));
		
		if(tracking) System.out.println("Tracking started\n");
		service.start();
	}

	public List<StockedProduct> deliver(int day) {
		int inclusiveDays = day;
		if(express){
			if(day == 0)
				inclusiveDays = 0;
			else
				inclusiveDays = day + EXPRESS_DAYS;
		}

		List<StockedProduct> delivered = new ArrayList<>();
		for(StockedProduct p : products)
			if(p.getDeliveryPeriod() <= inclusiveDays)
				delivered.add(p);
		products.removeAll(delivered);
		
		if(tracking){
			if(!delivered.isEmpty())
				System.out.println(listing("Day " + day +", delivered:", "*", delivered));	
			
			List<StockedProduct> remaining = this.getProducts();
			if(!remaining.isEmpty())
				System.out.println(listing("Day " + day +", still on its way:", "-", remaining));
		}
		return delivered;
	}

	public void delivered(String message) {
		if(tracking) System.out.println("Tracking finished\n");

		System.out.println("Everything delivered" + message);
	}
	
	public String toString(){
		String string = products.toString();
		return string.substring(1, string.length() - 1);
	}
	
	private String listing(String message, String bullet, List<StockedProduct> products){
		if(products == null || products.isEmpty())
			return "";
		StringBuilder sb = new StringBuilder();
		sb.append(message).append('\n');
		for(StockedProduct product : products)
			sb.append(bullet).append(' ').append(product.toString()).append('\n');		
		return sb.toString();
	}
}
