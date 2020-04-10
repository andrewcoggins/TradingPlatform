package brown.platform.item.library;

public class PricedItem extends Item {
	private double price;
	
	public PricedItem() {
		super();
		this.price = 0;
	}
	
	  public PricedItem(String name, int count, double price) {
	    super(name, count); 
	    this.price = price;
	  }
	  

	  public PricedItem(String name, double price) {
	    super(name, 1); 
	    this.price = price;
	  }
	  
	  public double getPrice() {
		  return price;
	  }
}
