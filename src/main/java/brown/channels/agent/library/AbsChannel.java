package brown.channels.agent.library;

import brown.accounting.library.Ledger;
import brown.channels.agent.IAgentChannel;

public abstract class AbsChannel implements IAgentChannel{
  
  public final Integer ID;
  public Ledger ledger;

  public AbsChannel() { 
    this.ID = null; 
    this.ledger = null;
  }
  
  public AbsChannel(Integer ID, Ledger ledger) {
    this.ID = ID; 
    this.ledger = ledger;
  }
  
  @Override
  public Integer getAuctionID() {
    return this.ID;
  }

  @Override
  public Ledger getLedger() {
    return this.ledger;
  }
}
