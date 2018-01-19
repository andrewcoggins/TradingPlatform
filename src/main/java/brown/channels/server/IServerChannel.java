package brown.channels.server;

import brown.accounting.library.Ledger;
import brown.channels.MechanismType;
import brown.channels.agent.IAgentChannel;

public interface IServerChannel {
	/**
	 * Gets the ID of the auction
	 * @return id
	 */
	public Integer getID();
	
	/**
	 * Is the market closed
	 * @return true if ended
	 */
	public boolean isClosed();
	
	/**
	 * What type of allocation mechanism does it use?
	 * @return MechanismType
	 */
	public MechanismType getMechanismType();
	
	/**
	 * Is shortselling permitted?
	 * @return true if shortable
	 */
	public boolean permitShort();

	/**
	 * Get wrapper
	 * @return
	 */
	public IAgentChannel wrap(Ledger ledger);

}
