package brown.registration;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import brown.messages.Registration;
import brown.valuable.library.Tradeable;


/**
 * Registration with valuation from server for lab 3.
 * 
 * @author lcamery
 */
public class ValuationRegistration extends Registration {
	/**
	 * Agent's valuation.
	 */
	private final Map<Tradeable, Double> valueBundle;

	/**
	 * Empty Constructor for Kryo.
	 */
	public ValuationRegistration() {
		super(null);
		this.valueBundle = null;
	}

	/**
	 * Constructor.
	 * 
	 * @param id
	 * @param value
	 */
	public ValuationRegistration(Integer id, Map<Tradeable, Double> values) {
		super(id);
		this.valueBundle = values;
	}

	
	/**
	 * Getter.
	 * 
	 * @return the agents valuation.
	 */
	public Double getValue(Set<Tradeable> aVal) {
		return this.valueBundle.getOrDefault(aVal, 0.0);
	}
	
	public Set<Double> getAllValues() {
	  return new HashSet<Double> (this.valueBundle.values());
	}

	public Map<Tradeable, Double> getValues() {
		return this.valueBundle;
	}
}
