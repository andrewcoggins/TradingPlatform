package brown.auction.value.distribution.library;

import java.util.List;

import org.spectrumauctions.sats.core.model.gsvm.GSVMWorld;
import org.spectrumauctions.sats.core.model.gsvm.GSVMWorldSetup;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.valuation.ISpecificValuation;
import brown.auction.value.valuation.library.GSVM18Valuation;
import brown.platform.item.ICart;
import brown.user.agent.library.SATSUtil;

public class GSVM18ValuationDistribution extends AbsValuationDistribution implements IValuationDistribution {
	private int seed;
	private long populationID;
	
	/**
	 * For kryo DO NOT USE
	 */
	public GSVM18ValuationDistribution() {
		super(null, null);
		this.seed = 0;
		this.populationID = SATSUtil.createGSVM18Population(this.seed).get(0).getPopulation();
	}

	public GSVM18ValuationDistribution(ICart items, List<IValuationGenerator> generators) {
		super(items, generators);
		this.seed = (int)(System.currentTimeMillis() % 10000);
		this.populationID = SATSUtil.createGSVM18Population(this.seed).get(0).getPopulation();
	}

	@Override
	public ISpecificValuation sample(Integer agentID, List<List<Integer>> agentGroups) {
		int index = 0;
		for (List<Integer> group : agentGroups) {
			int j = group.indexOf(agentID);
			if (j != -1) {
				index = j;
				break;
			}
		}
		return new GSVM18Valuation(this.seed, index, this.populationID);
	}

}
