package brown.tradeable.library;

import brown.tradeable.AbsTradeable;

/**
 * The good to be used in the back-end bidding logic. 
 * Consists only of the private ID of the agent.
 * @author acoggins
 *
 */
public class Tradeable extends AbsTradeable {
  
  public final Integer ID; 
  public final Integer COUNT; 
  
  /**
   * empty constructor.
   */
  public Tradeable() {
    this.ID = null; 
    this.COUNT = null; 
  }
//  
//  /**
//   * constructor with ID.
//   * @param id
//   */
  public Tradeable(Integer id) {
    this.ID = id; 
    this.COUNT = 1; 
  }
  
  public Tradeable(Integer id, Integer count) {
    this.ID = id; 
    this.COUNT = count;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((COUNT == null) ? 0 : COUNT.hashCode());
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
    return true;
  }

  @Override
  public String toString() {
    return "Tradeable [ID=" + ID + ", COUNT=" + COUNT + "]";
  }
}
