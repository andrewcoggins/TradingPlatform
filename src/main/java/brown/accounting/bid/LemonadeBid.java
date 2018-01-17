package brown.accounting.bid;

public class LemonadeBid extends AbsBid {
  
  @Override
  public String toString() {
    return "LemonadeBid [bid=" + bid + "]";
  }

  public final Integer bid; 
  
  public LemonadeBid(Integer bid) {
    this.bid = bid; 
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((bid == null) ? 0 : bid.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    return(obj instanceof LemonadeBid && 
        ((LemonadeBid) obj).bid.equals(this.bid));
  }
  
}