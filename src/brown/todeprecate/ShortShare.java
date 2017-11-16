package brown.todeprecate;

import java.util.List;

import brown.accounting.Account;
import brown.tradeable.library.Tradeable;


public class ShortShare extends Asset {
	private final double COUNT;
	private final Tradeable TYPE;
	

	public ShortShare(double count, Tradeable type) {
		this.COUNT = count;
		this.TYPE = type;
	}

	@Override
	public Integer getAgentID() {
		return null;
	}

	@Override
	public void setAgentID(Integer ID) {
		// Noop
	}

	@Override
	public double getCount() {
		return this.COUNT;
	}

	@Override
	public void setCount(double count) {
		//Noop
	}

	@Override
	public Tradeable getType() {
		return this.TYPE;
	}

	@Override
	public List<Account> convert(StateOfTheWorld closingState) {
		return null;
	}

	@Override
	public Asset split(double newCount) {
		return null;
	}

	@Override
	public Asset toAgent(Integer ID) {
		return this;
	}

}
