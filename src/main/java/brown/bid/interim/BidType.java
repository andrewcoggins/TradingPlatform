package brown.bid.interim; 

public class BidType implements IBidType { 
  
  public final Double price; 
  public final Integer quantity; 
  
  public BidType(Double price, Integer quantity) {
    this.price = price; 
    this.quantity = quantity; 
  }
  
}