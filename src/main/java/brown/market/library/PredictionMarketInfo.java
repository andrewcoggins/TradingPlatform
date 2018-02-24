package brown.market.library;

import java.util.LinkedList;
import java.util.List;

import brown.accounting.library.Transaction;
import brown.logging.Logging;
import brown.value.config.GameValuationConfig;
import brown.value.config.PredictionMarketDecoysConfig;

public class PredictionMarketInfo extends PrevStateInfo{
  public Boolean trueCoin;
  public List<Transaction> tradeHistory;
  public PrevStateType type;
  
  public PredictionMarketInfo(Boolean coin) {
    this.trueCoin = coin;
    this.tradeHistory = new LinkedList<Transaction>();
    this.type = PrevStateType.PREDICTION;
  }
  
  @Override
  public void combine(PrevStateInfo prevState) {
    if (prevState.getType() == PrevStateType.PREDICTION){
      this.tradeHistory.addAll(((PredictionMarketInfo) prevState).tradeHistory);
    } else {
      Logging.log("Trying to combine prev state infos of incompatible types");
    }
    // TODO Auto-generated method stub
    
  }

  @Override
  public void initialize(GameValuationConfig gconfig) {
    this.trueCoin = ((PredictionMarketDecoysConfig) gconfig).getTrueCoin();
  }

  @Override
  public PrevStateType getType() {
    // TODO Auto-generated method stub
    return this.type;
  }
}
