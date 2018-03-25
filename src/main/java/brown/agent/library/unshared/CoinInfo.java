package brown.agent.library.unshared;

/**
 * This class represents a pair (Info, NumDecoys) with the information and the number of decoys received by an agent.
 * 
 * @author Enrique Areyan Viqueira
 * @author Ishaan Agarwal
 */
public class CoinInfo {
  /**
   * True if heads, false otherwise.
   */
  protected boolean information;
  /**
   * The number of decoys.
   */
  protected int numberOfDecoys;

  /**
   * Constructor.
   * 
   * @param information
   * @param numberOfDecoys
   */
  public CoinInfo(boolean information, int numberOfDecoys) {
    this.information = information;
    this.numberOfDecoys = numberOfDecoys;
  }

  /**
   * Returns 1 if head, 0 if tails;
   * 
   * @return a numerical representation of the information.
   */
  public int getInfo() {
    return this.information ? 1 : 0;
  }

  /**
   * Getter.
   * 
   * @return the number of decoyes.
   */
  public int getNumberOfDecoys() {
    return this.numberOfDecoys;
  }
  
  @Override
  public String toString(){
    return "Coin = " + this.information + " No of Decoys = " + this.numberOfDecoys;
  }
  
  @Override
  public boolean equals(Object obj)
  {
    if(obj instanceof CoinInfo) {
    CoinInfo obj2 = (CoinInfo)obj; 
    if(obj2.getInfo() == this.getInfo() && obj2.getNumberOfDecoys() == this.getNumberOfDecoys()) {
      return true;
    }
    }
    return false;
  }
  

}