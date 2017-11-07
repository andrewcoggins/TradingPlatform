package brown.tradeables;

import java.util.List;
import java.util.function.Function;

import brown.assets.accounting.Account;
import brown.assets.value.EndState;
import brown.states.StateOfTheWorld;
import brown.valuable.library.Tradeable;

/**
 * All non-cash assets will extend this class
 */
public class Asset {
	protected double count;
	protected Integer agentID;
	//protected Function<EndState, List<Account>> CONVERTER;
	
	protected final long TIMESTAMP;
	protected final Tradeable TYPE;
	
	/**
	 * For KyroNet
	 */
	public Asset() {
		this.count = 0;
		this.agentID = null;
		//this.CONVERTER = null;
		this.TIMESTAMP = 0;
		this.TYPE = null;
	}
	
	/**
	 * Simple Good
	 * @param type
	 * the type of the tradeable, meaning its enum type and private id of the owner.
	 * @param count
	 * number of BasicTypes in the tradeable.
	 * @param owner
	 * Owner of the Tradeable.
	 */
	public Asset(Tradeable type, double count, Integer owner) {
		this.count = count;
		this.TYPE = type;
		this.agentID = owner;
		
		this.TIMESTAMP = System.currentTimeMillis();
		//this.CONVERTER = (state) -> null;
	}
	
	/**
	 * Simple good w/o owner
	 * @param type
	 * the type of the tradeable, meaning its enum type and private id of the owner.
	 * @param count
	 * number of Basictypes in the tradeable.
	 */
	public Asset(Tradeable type, double count) {
		this.count = count;
		this.TYPE = type;
		this.agentID = null;
		
		this.TIMESTAMP = System.currentTimeMillis();
		//this.CONVERTER = (state) -> null;
	}
	
	/**
	 * Contract
	 * @param type
	 * BasicType of the tradeable.
	 * @param count
	 * number of tradeables.
	 * @param owner
	 * private id of the tradeable's owner.
	 * @param closure
	 * function describing the closure of the tradeable (ask about this?)
	 */
	public Asset(Tradeable type, double count, Integer owner,
			Function<EndState, List<Account>> closure) {
		this.count = count;
		this.TYPE = type;
		this.agentID = owner;
		
		this.TIMESTAMP = System.currentTimeMillis();
		//this.CONVERTER = closure;
	}
	
	/**
	 * Contract w/o owner
	 * @param type
	 * @param count
	 * @param closure
	 */
	public Asset(Tradeable type, double count,
			Function<EndState, List<Account>> closure) {
		this.count = count;
		this.TYPE = type;
		this.agentID = null;
		
		this.TIMESTAMP = System.currentTimeMillis();
		//this.CONVERTER = closure;
	}
	
	/**
	 * gets agent id
	 * @return
	 * Agent id
	 */
	public Integer getAgentID() {
		return this.agentID;
	}
	
	/**
	 * sets agent id
	 * @param ID
	 * a private ID
	 */
	public void setAgentID(Integer ID) {
		this.agentID = ID;
	}
	
	/**
	 * get count of number of basic goods.
	 * @return
	 */
	public double getCount() {
		return this.count;
	}

	/**
	 * set the count of the number of basic goods.
	 * @param count
	 */
	public void setCount(double count) {
		this.count = count;
	}
	
	/**
	 * get the enum type of the tradeable.
	 * @return
	 */
	public Tradeable getType() {
		return this.TYPE;
	}
	
	public List<Account> convert(StateOfTheWorld closingState) {
		//return this.CONVERTER.apply(new EndState(this.count, closingState));
	  return null;
	}
	
	public Asset split(double newCount) {
		this.count -= newCount;
		//return new Asset(this.TYPE, newCount, this.agentID, this.CONVERTER);
		return null; 
	}
	
	/**
	 * gives an already-created Tradeable to an agent.
	 * @param id
	 * agent's private id.
	 * @return
	 * returns a new tradeable that belongs to the 
	 * agent specified by the id. 
	 */
	public Asset toAgent(Integer id) {
		Integer toInclude = null;
		if (id.equals(this.agentID)) {
			toInclude = this.agentID;
		}
		
		return new Asset(this.TYPE, this.count, toInclude);
	}
}
