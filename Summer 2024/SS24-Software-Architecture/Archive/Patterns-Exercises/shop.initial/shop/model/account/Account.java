package shop.model.account;

import java.util.Date;

import shop.model.mobile.MobilePhone;
import shop.model.order.Order;
import shop.model.products.Product;

public class Account{
	
	private static final int NONE = 0;
	private static final int DIRECT_DEBIT = 1;
	private static final int CREDIT_CARD = 2;
	private static final int TRANSFER = 3;
	
	private String first, last, phone, user, pass;

	private String bankno, accountno; // for direct debit
	private String cardno; // for credit card payment
	private String eMail; // for transfer

	private int policy = NONE;	
	
	private boolean loggedIn = false;
	private Order order;
	
	private MobilePhone mobilePhone = null;

	public Account(String first, String last) {
		this(first, last, "", (first + "." + last).toLowerCase(), "secret");
	}

	public Account(String first, String last, String phone, String user, String pass) {
		this.first = first.trim();
		this.last = last.trim();
		this.phone = phone.trim();
		this.user = user.trim().toLowerCase();
		this.pass = pass.trim();
		this.order = new Order();

		if(phone.length() != 0){
			mobilePhone = new MobilePhone(phone);
			mobilePhone.dispose();
		}
	}

	public boolean owns(String user){
		return this.user.equals(user);
	}

	public boolean login(String user, String pass){
		loggedIn = this.user.equals(user) && this.pass.equals(pass);
		return loggedIn;
	}
	
	public void logout(){
		loggedIn = false;
	}
	
	public String getPhone(){
		return phone;
	}

	public void add(Product product){
		if((order.isEmpty() || order.isDigital()) && product.isPhysical())
			order.setShipment();
		order.add(product);
	}

	public void clearOrder() {
		order = new Order();
	}
	
	public boolean isDigitalOrder(){
		return order.isDigital();
	}

	public boolean isPhysicalOrder(){
		return order.isPhysical();
	}
	
	public int getCosts(){
		return order.getCosts();
	}

	public double getCostsAsEuro(){
		return order.getCosts() / 100.0;
	}
	
	public void setDirectDebit(String bankno, String accountno) {
		this.bankno = bankno.replaceAll("\\D+","");
		this.accountno = accountno.replaceAll("\\D+","");
		this.policy = DIRECT_DEBIT;
	}

	public void setCreditCard(String cardno) {
		this.cardno = cardno.replaceAll("\\D+","");
		this.policy = CREDIT_CARD;
	}

	public void setTransfer(String eMail) {
		this.eMail = eMail.replaceAll("\\s", "");
		this.policy = TRANSFER;
	}
	
	public String getPolicyAsString(){
		switch(policy){
		case NONE : return "";
		case DIRECT_DEBIT : return "DirectDebit " + bankno + "|" + accountno;
		case CREDIT_CARD : return "CreditCard #" + cardno;
		case TRANSFER : return "Bank Transfer, contact: " + eMail;
		}
		return "";
	}

	public void addExpress(){
		if(order.isPhysical())
			order.setExpress();
	}
	
	public void addTracking(){
		order.setTracking();
	}
	
	public void complete(){
		if(!loggedIn)
			return;
		
		if(order.isEmpty() || policy == NONE)
			return;
		
		this.pay("Order of " + new Date(), this.getCostsAsEuro());
		
		order.deliver();
		
		this.clearOrder();		
	}

	private void pay(String orderId, double amount){
		switch(policy){
			case DIRECT_DEBIT : System.out.println(orderId + ": " + amount + "€ will be transfered from your account.\n"); break;
			case CREDIT_CARD : System.out.println(orderId + ": The " + amount + " will be taken from your credit card.\n"); break;
			case TRANSFER : System.out.println(orderId + ": Please transfer " + amount + " to our account 12DE456789|000123.\n"); break;
			default : System.out.println("No payment method defined");
		}
	}

	public void showMobile(){
		if(mobilePhone != null && !mobilePhone.isVisible()){
			mobilePhone.pack();
			mobilePhone.setVisible(true);
		}
	}

	@Override
	public String toString() {
		return first + " " + last + "; open orders:\n" + order;
	}
}
