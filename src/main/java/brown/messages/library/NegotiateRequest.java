package brown.messages.library;

import java.util.List;

import brown.accounting.Account;
import brown.agent.AbsAgent;
import brown.messages.AbsMessage;
import brown.tradeable.library.Tradeable;

/**
 * Direct Negotiation
 * A message sent to the server by an agent when it wants
 * to initiate a trade note: -1 indicates offer to all agents
 * 
 * @author lcamery
 */
public class NegotiateRequest extends AbsMessage {
  public final Integer toID;
  public final Integer fromID;

  public final Integer moniesRequested;
  public final List<Tradeable> sharesRequested;

  public final Integer moniesOffered;
  public final List<Tradeable> sharesOffered;

  public NegotiateRequest() {
    super(null);
    this.toID = null; 
    this.fromID = null;
    this.moniesRequested = null;
    this.moniesOffered = null;

    this.sharesOffered = null;
    this.sharesRequested = null;
  }
  
  /**
   * Constructor.
   * 
   * @param toID
   * @param fromID
   * @param moniesRequested
   * @param sharesRequested
   * @param moniesOffered
   * @param sharesOffered
   */
  public NegotiateRequest(Integer toID, Integer fromID, Integer moniesRequested,
      List<Tradeable> sharesRequested, Integer moniesOffered, List<Tradeable> sharesOffered) {
    super(null);
	this.toID = toID;
    this.fromID = fromID;

    this.moniesRequested = moniesRequested;
    this.moniesOffered = moniesOffered;

    this.sharesOffered = sharesOffered;
    this.sharesRequested = sharesRequested;
  }

  /**
   * Overwrites the fromID field to prevent malicious offer creation
   * 
   * @param correctID
   * @return
   */
  public NegotiateRequest safeCopy(Integer correctID) {
    return new NegotiateRequest(toID, correctID, moniesRequested, sharesRequested, moniesOffered, sharesOffered);
  }

  /**
   * Method that determines if two agents' accounts satisfy the assets needed to execute this trade
   * 
   * @param toAccount
   * @param fromAccount
   * @return
   */
  public boolean isSatisfied(Account toAccount, Account fromAccount) {
    if (fromAccount.getMonies() < moniesOffered || !fromAccount.getGoods().containsAll(sharesOffered)) {
      return false;
    }

    if (toAccount.getMonies() < moniesRequested || !toAccount.getGoods().containsAll(sharesRequested)) {
      return false;
    }

    return true;
  }

@Override
public void dispatch(AbsAgent agent) {
	agent.onNegotiateRequest(this);
}

}
