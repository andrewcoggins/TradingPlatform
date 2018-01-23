package brown.messages.library;


import brown.agent.AbsAgent;
import brown.tradeable.ITradeable;

/**
 * Message provided to agents when their accounts change
 */
public class BankUpdateMessage extends AbsMessage {
  
	public final ITradeable tradeableAdded; 
	public final ITradeable tradeableLost;
	public final Double moniesAdded; 
	
	public BankUpdateMessage() {
		super(null);
		this.tradeableAdded = null; 
		this.tradeableLost = null;
		this.moniesAdded = null;
	}

	/**
	 * Constructor for BankUpdate
	 * @param ID : agent ID
	 * @param oldAccount : old account
	 * @param newAccount : new account; contains info relevant to update
	 */
	public BankUpdateMessage(int ID, ITradeable tradeableAdded, ITradeable tradeableLost, 
	    double moniesAdded) {
		super(ID);
		this.tradeableAdded = tradeableAdded; 
		this.tradeableLost = tradeableLost; 
		this.moniesAdded = moniesAdded; 
	}

	@Override
	public void dispatch(AbsAgent agent) {
		agent.onBankUpdate(this);
	}


}
