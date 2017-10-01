package brown.bundles;

public class MarketState {
	public final Integer AGENTID;
	public final double PRICE;
	
	/**
	 * For kryo do not use
	 */
	public MarketState() {
		this.AGENTID = null;
		this.PRICE = 0;
	}
	
	/**
	 * a marketstate is an agentID and a price. This may be a misnomer.
	 * @param agentID
	 * @param price
	 */
	public MarketState(Integer agentID, double price) {
		this.AGENTID = agentID;
		this.PRICE = price;
	}
	
	@Override
	public String toString() {
		return "<" + this.AGENTID + "," + this.PRICE + ">";
	}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((AGENTID == null) ? 0 : AGENTID.hashCode());
    long temp;
    temp = Double.doubleToLongBits(PRICE);
    result = prime * result + (int) (temp ^ (temp >>> 32));
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
    MarketState other = (MarketState) obj;
    if (AGENTID == null) {
      if (other.AGENTID != null)
        return false;
    } else if (!AGENTID.equals(other.AGENTID))
      return false;
    if (Double.doubleToLongBits(PRICE) != Double.doubleToLongBits(other.PRICE))
      return false;
    return true;
  }
	
	
}
