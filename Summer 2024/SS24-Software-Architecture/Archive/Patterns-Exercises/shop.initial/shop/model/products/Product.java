package shop.model.products;


public class Product{
	
	private Type type;
	private String name;
	private int price;
	private int deliveryPeriod; // days to deliver
		
	public Product(String type, String name, double price, int deliveryPeriod) {
		this(Type.parseType(type), name, (int)(price * 100), deliveryPeriod);
	}
	
	private Product(Type type, String name, int price, int deliveryPeriod) {
		super();
		this.type = type;
		this.name = name;
		this.price = price;
		this.deliveryPeriod = deliveryPeriod;
	}

	public String getName() {
		return name;
	}

	public int getDeliveryPeriod() {
		return deliveryPeriod;
	}

	public int getPrice() {
		return price;
	}

	private double toEuro(){
		return price / 100.0;
	}

	public boolean isDigital() {
		return type == Type.DOWNLOAD;
	}

	public boolean isPhysical() {
		return !this.isDigital();
	}

	public String toString(){
		return (name.length() < 20 ? name : name.substring(0, 20) + "...") + ", (" + type.getName() + ", " + toEuro() + "€)";
	}
}
