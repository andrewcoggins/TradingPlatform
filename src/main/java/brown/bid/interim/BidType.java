package brown.bid.interim; 

public class BidType implements IBidType { 
  
  public final Double price; 
  public final Integer quantity; 
  
  public BidType(Double price, Integer quantity) {
    this.price = price; 
    this.quantity = quantity; 
  }

  @Override
  public String toString() {
    return "BidType [price=" + price + ", quantity=" + quantity + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((price == null) ? 0 : price.hashCode());
    result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    BidType other = (BidType) obj;
    if (price == null) {
      if (other.price != null)
        return false;
    } else if (!price.equals(other.price))
      return false;
    if (quantity == null) {
      if (other.quantity != null)
        return false;
    } else if (!quantity.equals(other.quantity))
      return false;
    return true;
  }
  
}