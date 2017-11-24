package brown.accounting.bidbundle;

import brown.accounting.bid.AbsBid;

public abstract class AbsBidBundle implements IBidBundle { 
  
  public abstract AbsBid getBids();
  
}