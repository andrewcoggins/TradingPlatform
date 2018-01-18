package brown.accounting.bid;

public class GameBid extends AbsBid {
  
  public final Integer move; 
  
  /**
   * For Kryo 
   * DO NOT USE
   */
  public GameBid() {
    this.move = null; 
  }
  
  public GameBid(Integer bid) {
    this.move = bid; 
  }

  @Override
  public String toString() {
    return "Gamebid [move=" + move + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((move == null) ? 0 : move.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    return(obj instanceof GameBid && 
        ((GameBid) obj).move.equals(this.move));
  }
  
}