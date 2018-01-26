package brown.market.library;

public abstract class PrevStateInfo {
  
  // Combine this report with information from another report
  public abstract void combine(PrevStateInfo prevState);
  
}
