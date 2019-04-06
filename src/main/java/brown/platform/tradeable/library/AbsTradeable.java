
package brown.platform.tradeable.library;

import brown.platform.tradeable.ITradeable;

/**
 * Abstract tradeable type. All other tradeables
 * extend this class.
 * @author acoggins
 *
 */
public abstract class AbsTradeable implements ITradeable { 

  private Integer ID;
  private String NAME; 
  private TradeableType TYPE; 
  
  /**
   * For Kryo
   * DO NOT USE
   */
  public AbsTradeable() {
    this.ID = null; 
    this.NAME = null; 
    this.TYPE = null;
  }
  
  /**
   * An abstract tradeable has an ID, a count and a type.
   * @param ID tradeable ID
   * @param count count of tradeable. 
   * @param type type of tradeable.
   */
  public AbsTradeable(Integer ID, String name, TradeableType type) {
   this.ID = ID; 
   this.NAME = name; 
   this.TYPE = type;
  }

  /**
   * Gets the ID of the tradeable. 
   */
  public Integer getID() {
    return this.ID;
  }
  
  /**
   * gets the name of the tradeable.
   * @return
   */
  public String getName() {
    return this.NAME; 
  }
  

  /**
   * Gets the type of the tradeable.
   */
  public TradeableType getType() {
    return this.TYPE;
  }

  @Override
  public String toString() {
    return "AbsTradeable [ID=" + ID + ", NAME=" + NAME + ", TYPE=" + TYPE + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ID == null) ? 0 : ID.hashCode());
    result = prime * result + ((NAME == null) ? 0 : NAME.hashCode());
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
    if (ID == null) {
      if (other.ID != null)
        return false;
    } else if (!ID.equals(other.ID))
      return false;
    if (NAME == null) {
      if (other.NAME != null)
        return false;
    } else if (!NAME.equals(other.NAME))
      return false;
    if (TYPE != other.TYPE)
      return false;
    return true;
  }

}