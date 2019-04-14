
package brown.platform.tradeable.library;

import brown.platform.tradeable.ITradeable;

/**
* A simple tradeable is a single tradeable.
* @author amy
*/
public class Tradeable implements ITradeable {   


  private Integer ID;
  private String NAME; 
  
  /**
   * For Kryo
   * DO NOT USE
   */
  public Tradeable() {
    this.ID = null; 
    this.NAME = null; 
  }
  
  /**
   * An abstract tradeable has an ID, a count and a type.
   * @param ID tradeable ID
   * @param count count of tradeable. 
   * @param type type of tradeable.
   */
  public Tradeable(Integer ID, String name) {
   this.ID = ID; 
   this.NAME = name; 
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
  

  @Override
  public String toString() {
    return "Tradeable [ID=" + ID + ", NAME=" + NAME + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ID == null) ? 0 : ID.hashCode());
    result = prime * result + ((NAME == null) ? 0 : NAME.hashCode());
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
    if (NAME == null) {
      if (other.NAME != null)
        return false;
    } else if (!NAME.equals(other.NAME))
      return false;
    return true;
  }

}