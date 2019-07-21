package brown.platform.market;

import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.TradeRequestMessage;
import brown.platform.accounting.IAccountUpdate;


public interface IMarket {

  public Integer getMarketID();

  public TradeRequestMessage constructTradeRequest(Integer agentID);

  public boolean processBid(ITradeMessage bid);
  
  public List<IAccountUpdate> constructOrders();
  
  public Map<Integer,List<IInformationMessage>> constructReport();

  public void tick();
  
  public void setReserves(); 
  
  public void updateInnerInformation(); 
  
  public void updateOuterInformation(); 
  
  public void clearBidCache();

  public boolean isOpen(); 
  
  public IMarketPublicState getPublicState(); 
  
}
