package brown.tradeable.library;

import brown.tradeable.ITradeable;

public abstract class AbsTradeable implements ITradeable { 

  public final Integer ID;
  public Integer COUNT; // Java flaw: final type cannot be inherited! UGH!
  public final TradeableType TYPE; 
  
  /**
   * For Kryo
   * DO NOT USE
   */
  public AbsTradeable() {
    this.ID = 0; 
    this.COUNT = 0; 
    this.TYPE = null;
  }
  
  public AbsTradeable(Integer ID, Integer count, TradeableType type) {
   this.ID = ID; 
   this.COUNT = count;
   this.TYPE = type;
  }

  public Integer getID() {
    return this.ID;
  }
  
  public Integer getCount() {
    return this.COUNT;
  }

  public TradeableType getType() {
    return this.TYPE;
  }
  
  @Override
  public String toString() {
    return "AbsTradeable [ID=" + ID + ", COUNT=" + COUNT + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((COUNT == null) ? 0 : COUNT.hashCode());
    result = prime * result + ((ID == null) ? 0 : ID.hashCode());
    result = prime * result + ((TYPE == null) ? 0 : TYPE.hashCode());
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
    AbsTradeable other = (AbsTradeable) obj;
    if (COUNT == null) {
      if (other.COUNT != null)
        return false;
    } else if (!COUNT.equals(other.COUNT))
      return false;
    if (ID == null) {
      if (other.ID != null)
        return false;
    } else if (!ID.equals(other.ID))
      return false;
    if (TYPE != other.TYPE)
      return false;
    return true;
  }  
}