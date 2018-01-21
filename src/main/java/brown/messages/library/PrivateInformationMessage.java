package brown.messages.library;

import java.util.List;

import brown.agent.AbsAgent;
import brown.tradeable.ITradeable;
import brown.value.distribution.IValuationDistribution;
import brown.value.valuation.IValuation;

public class PrivateInformationMessage extends AbsMessage {

  private List<ITradeable> allTradeables; 
  private IValuation privateValuation;
  private IValuationDistribution allValuations; 
  
  public PrivateInformationMessage(Integer ID, List<ITradeable> allTradeables, IValuation privateValuation) {
    super(ID);
    this.allTradeables = allTradeables;
    this.privateValuation = privateValuation; 
  }

  public PrivateInformationMessage(Integer ID, List<ITradeable> allTradeables, IValuation privateValuation, 
      IValuationDistribution allValuations) {
    super(ID);
    this.allTradeables = allTradeables;
    this.privateValuation = privateValuation; 
  }
  
  public List<ITradeable> getTradeables() {
    return this.allTradeables; 
  }
  
  public IValuation getPrivateValuation() {
    return this.privateValuation; 
  }
  
  public IValuationDistribution getAllValuations() {
    return this.allValuations; 
  }
  
  @Override
  public void dispatch(AbsAgent agent) {
    agent.onPrivateInformation(this);
  }
  
}