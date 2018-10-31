package brown.auction.prevstate.library;

import java.util.LinkedList;
import java.util.List;

import brown.auction.prevstate.PrevStateType;
import brown.auction.value.manager.IValuationManager;
import brown.auction.value.manager.library.PredictionMarketDecoysConfig;
import brown.logging.library.Logging;
import brown.platform.accounting.library.Transaction;

/**
 * PrevStateInfo for prediction markets.
 * @author kerry
 *
 */
public class PredictionMarketInfo extends PrevStateInfo {
  public Boolean trueCoin;
  public List<Transaction> tradeHistory;
  public PrevStateType type;
  
  /**
   * PredictionMarketInfo is initially described only by
   * whether or not the coin is true or false.
   * @param coin
   */
  public PredictionMarketInfo(Boolean coin) {
    this.trueCoin = coin;
    this.tradeHistory = new LinkedList<Transaction>();
    this.type = PrevStateType.PREDICTION;
  }
  
  /**
   * can also be initialized with a trade history
   * @param trans a list of transactions.
   */
  public PredictionMarketInfo(List<Transaction> trans) {
    this.trueCoin = null;
    this.tradeHistory = trans;
    this.type = PrevStateType.PREDICTION;
  }

  @Override
  public void combine(PrevStateInfo prevState) {
    if (prevState.getType() == PrevStateType.PREDICTION){
      this.tradeHistory.addAll(((PredictionMarketInfo) prevState).tradeHistory);
    } else if (prevState.getType() == PrevStateType.BLANK){
      // a Blank 
    } else {
      Logging.log("Trying to combine prev state infos of incompatible types");
    }
  }

  @Override
  public void initialize(IValuationManager gconfig) {
    this.trueCoin = ((PredictionMarketDecoysConfig) gconfig).getTrueCoin();
  }

  @Override
  public PrevStateType getType() {
    return this.type;
  }
  
  @Override
  public String toString() {
    return "PredictionMarketInfo [trueCoin=" + trueCoin + ", tradeHistory Size="
        + tradeHistory.size() + ", type=" + type + "]";
  }
}
