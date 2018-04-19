package brown.value.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.logging.Logging;
import brown.market.library.PredictionMarketInfo;
import brown.market.library.PrevStateInfo;
import brown.messages.library.PredictionMarketValuationMessage;
import brown.messages.library.PrivateInformationMessage;
import brown.value.valuation.ValuationType;

public class PredictionMarketDecoysConfig extends ValConfig implements IValuationConfig {
  Boolean coin;
    
  // for kryo
  public PredictionMarketDecoysConfig(){
    super(ValuationType.Game);
    this.coin = null;
  }
    
  public void initialize(List<Integer> bidders){
    this.coin = Math.random() > 0.5;
  }
  
  public Boolean getTrueCoin(){
    return this.coin;
  }
  
  @Override
  public Map<Integer,PrivateInformationMessage> generateReport(List<Integer> agents) {    
    Collections.shuffle(agents);
    
    Map<Integer,PrivateInformationMessage> messages = new HashMap<Integer,PrivateInformationMessage>();
    
    int count = 0;
    for (Integer agent: agents) {
      int nDecoys = (int) count/2 + 1;
      messages.put(agent, new PredictionMarketValuationMessage(agent,flipCoins(nDecoys),nDecoys));
      count++;
    }
    
    return messages;
  }   
  
  public boolean flipCoins(int numDecoys) {
    boolean[] flips = new boolean[numDecoys+1];
    flips[0] = this.coin;
    for (int i = 1; i<=numDecoys; i++){
      flips[i] = Math.random() > .5;
    }
    // int floors so this generates a random integer from 0 to numDecoys
    Integer whichCoin = (int)(Math.random() * (numDecoys+1));
    return flips[whichCoin];
  }

  @Override
  public PrevStateInfo generateInfo() {
    return new PredictionMarketInfo(this.coin);
  }
}
