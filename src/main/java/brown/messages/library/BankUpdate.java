package brown.messages.library;

import brown.accounting.Account;
import brown.agent.AbsAgent;
import brown.messages.AbsMessage;

/**
 * Message provided to agents when their accounts change
 */
public class BankUpdate extends AbsMessage {
	public final Account oldAccount;
	public final Account newAccount;
	
	/**
	 * Empty constructor for kryo
	 * DO NOT USE
	 */
	public BankUpdate() {
		super(null);
		this.oldAccount = null;
		this.newAccount = null;
	}

	/**
	 * Constructor for BankUpdate
	 * @param ID : agent ID
	 * @param oldAccount : old account
	 * @param newAccount : new account; contains info relevant to update
	 */
	public BankUpdate(int ID, Account oldAccount, Account newAccount) {
		super(ID);
		this.oldAccount = oldAccount;
		this.newAccount = newAccount;
	}

	@Override
	public void dispatch(AbsAgent agent) {
		agent.onBankUpdate(this);
	}

  @Override
  public String toString() {
    return "BankUpdate [oldAccount=" + oldAccount + ", newAccount="
        + newAccount + "]";
  }
	
	

}
