package brown.bundles;

import java.util.Map;
import java.util.Set;

import brown.tradeables.Asset;

//not sure exactly how this is useful.
//nothing implements it or uses it in here.
public interface IBidBundle {
	/**
	 * Gives the market a complex bid bundle
	 * Set<ITradeable> -> Double
	 * 
	 * @return complex bundle
	 */
	public Map<Set<Asset>, Double> getComplexBundle();
	
	/**
	 * Gives the market a simple bid bundle
	 * ITradeable -> Double
	 * @return simple bid bundle
	 */
	public Map<Asset, Double> getSimpleBidBundle();
	
	/**
	 * Gives the market a complex bid bundle
	 * from a demand query
	 * @param tradeables
	 * @return complex bid bundle
	 */
	public Map<Set<Asset>, Double> getComplexDemandQuery(Set<Asset> tradeables);
}
