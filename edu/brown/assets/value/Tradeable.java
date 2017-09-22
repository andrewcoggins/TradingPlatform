package brown.assets.value;

/**
 * the basic, indivisible trading datatype. 
 * Implemented by every other type of tradeable
 * and used in auctions.
 * @author acoggins
 *
 */
public class Tradeable {
	public final Integer ID;
	
	/**
	 * for Kryo.
	 */
	public Tradeable() {
		this.ID = null;
	}
	
	/**
	 * A BasicType consists of a TradeableType enum, and the private
	 * ID of its agent owner.
	 * @param type
	 * the type enum of the basicType
	 * @param ID
	 * the private id of the agent that owns the BasicType.
	 */
	public Tradeable(Integer ID) {
		this.ID = ID;
	}

  @Override
  public String toString() {
    return "Tradeable [ID=" + ID + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ID == null) ? 0 : ID.hashCode());
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
    Tradeable other = (Tradeable) obj;
    if (ID == null) {
      if (other.ID != null)
        return false;
    } else if (!ID.equals(other.ID))
      return false;
    return true;
  }
  
  
	

	
	
}
