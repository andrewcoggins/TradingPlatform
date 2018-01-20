package brown.channels.agent.library;

import brown.accounting.library.Ledger;
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
   * Actual constructor
   * @param ID
   * @param ledger
   */
  public AbsChannel(Integer ID, Ledger ledger) {
    this.ID = ID; 
  }
  
  @Override
  public Integer getMarketID() {
    return this.ID;
  }
  
}
