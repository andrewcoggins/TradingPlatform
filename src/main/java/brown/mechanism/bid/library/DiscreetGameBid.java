package brown.mechanism.bid.library;

import brown.mechanism.bid.IBid;

/**
 * A bid that is used in games like the lemonade game.
 * @author acoggins
 *
 */
public class DiscreetGameBid implements IBid {
  
	public final Integer action; 
	
	public DiscreetGameBid(Integer action) {
		this.action = action; 
	}

	@Override
	public String toString() {
		return "GameBid [action=" + action + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiscreetGameBid other = (DiscreetGameBid) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		return true;
	}
}