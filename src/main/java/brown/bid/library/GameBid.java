package brown.bid.library;

import brown.bid.IBid;

/**
 * A bid that is used in games like the lemonade game.
 * @author acoggins
 *
 */
public class GameBid implements IBid {
  
  public final Integer move; 
  
  /**
   * For Kryo 
   * DO NOT USE
   */
  public GameBid() {
    this.move = null; 
  }
  
  /**
   * @param bid - gamebid is represented by a number
   */
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