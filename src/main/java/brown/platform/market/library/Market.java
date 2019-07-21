package brown.platform.market.library;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketPublicState;
import brown.auction.marketstate.IMarketState;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.TradeRequestMessage;
import brown.platform.accounting.IAccountUpdate;
import brown.platform.item.ICart;
import brown.platform.market.IFlexibleRules;
import brown.platform.market.IMarket;

/**
 * Common implementation of IMarket.
 *
 * @author acoggins
 */
public class Market implements IMarket {

  private final Integer ID;
  private final IFlexibleRules RULES;
  private final IMarketState STATE;
  private final IMarketPublicState PUBLICSTATE;
  private final ICart TRADEABLES;

  private List<ITradeMessage> bids;

  /**
   * @param rules
   * @param state TODO: history
   */
  public Market(Integer ID, IFlexibleRules rules, IMarketState state,
      IMarketPublicState publicState, ICart tradeables) {
    this.ID = ID;
    this.RULES = rules;
    this.STATE = state;
    this.PUBLICSTATE = publicState;
    this.TRADEABLES = tradeables;
    this.bids = new LinkedList<ITradeMessage>();
  }

  // Make MarketID a field
  @Override
  public Integer getMarketID() {
    return this.ID;
  }

  // Processing bids is a four step process:
  // 1. Send trade requests via Query Rule
  // 2. Check acceptability via Activity Rule
  // 3. Find allocation and payments (via these rules)
  // 4. Send game report (via IR policy)
  public TradeRequestMessage constructTradeRequest(Integer agentID) {
    this.RULES.getQRule().makeTradeRequest(ID, STATE, TRADEABLES, bids, agentID);
    TradeRequestMessage request = this.STATE.getTRequest();
    return request;
  }

  public boolean processBid(ITradeMessage bid) {
    // TODO: put logic where trade message much only contain tradeables in
    // market.
    this.RULES.getActRule().isAcceptable(this.STATE, bid, this.bids,
        this.TRADEABLES);
    boolean acceptable = this.STATE.getAcceptable();
    if (acceptable) {
      this.bids.add(bid);
    }
    return acceptable;
  }

  public List<IAccountUpdate> constructOrders() {
    // Set allocation and payment
    this.RULES.getARule().setAllocation(this.STATE, this.bids);
    this.RULES.getPRule().setOrders(this.STATE, this.bids); // setPayment

    // Construct orders from allocation and payments
    // why no? return this.STATE.getAllocation();
    return this.STATE.getPayments();
//    return null;
  }

  @Override
  // Make sure this is called after constructOrders
  public Map<Integer, List<IInformationMessage>> constructReport() {
    this.RULES.getIRPolicy().updatePublicState(this.STATE, this.PUBLICSTATE);
    return null;
  }

  // Move to MarketState
  // For example, this needs to be done in the logic before getAcceptable,
  // e.g., once per iteration in an Open Outcry auction
  @Override
  public void setReserves() {
    this.RULES.getActRule().setReserves(this.STATE);
  }

  // This stays, b/c Bids are being moved out of MarketState
  // But all that logic might end up somewhere else
  @Override
  public void clearBidCache() {
    this.bids.clear();
  }

  // The remaining logic pertains to the Termination Condition
  @Override
  public void tick() {
    System.out.println(STATE.getTicks());
    this.STATE.tick();
  }

  @Override
  public boolean isOpen() {
    this.RULES.getTerminationCondition().checkTerminated(this.STATE);
    return this.STATE.isOpen();
  }

  @Override
  public IMarketPublicState getPublicState() {
    return this.PUBLICSTATE;
  }

  @Override
  public void updateOuterInformation() {
    this.RULES.getIRPolicy().updatePublicState(this.STATE, this.PUBLICSTATE);
  }

  @Override
  public void updateInnerInformation() {
    this.RULES.getInnerIRPolicy().updatePublicState(this.STATE,
        this.PUBLICSTATE);
  }

}
