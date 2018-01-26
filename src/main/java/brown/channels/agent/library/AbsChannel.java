package brown.channels.agent.library;

import brown.channels.agent.IAgentChannel;

public abstract class AbsChannel implements IAgentChannel{

  public final Integer ID;

  /**
   * For Kryo
   * DO NOT USE
   */
  public AbsChannel() { 
    this.ID = null; 
  }

  /**
   * Constructor
   * @param ID
   */
  public AbsChannel(Integer ID) {
    this.ID = ID; 
  }
  
  @Override
  public Integer getMarketID() {
    return this.ID;
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
    AbsChannel other = (AbsChannel) obj;
    if (ID == null) {
      if (other.ID != null)
        return false;
    } else if (!ID.equals(other.ID))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "AbsChannel [ID=" + ID + "]";
  }  
}
