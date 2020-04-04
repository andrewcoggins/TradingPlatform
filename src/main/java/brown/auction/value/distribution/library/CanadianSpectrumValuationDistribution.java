package brown.auction.value.distribution.library;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.spectrumauctions.sats.core.model.mrvm.MRVMBidder;
import org.spectrumauctions.sats.core.model.mrvm.MRVMLocalBidderSetup;
import org.spectrumauctions.sats.core.model.mrvm.MRVMNationalBidderSetup;
import org.spectrumauctions.sats.core.model.mrvm.MRVMRegionalBidderSetup;
import org.spectrumauctions.sats.core.model.mrvm.MRVMWorld;
import org.spectrumauctions.sats.core.model.mrvm.MRVMWorldSetup;
import org.spectrumauctions.sats.core.util.random.JavaUtilRNGSupplier;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.valuation.ISpecificValuation;
import brown.auction.value.valuation.library.SATSValuation;
import brown.auction.value.valuation.library.TACValuation;
import brown.platform.item.ICart;

public class CanadianSpectrumValuationDistribution extends AbsValuationDistribution implements IValuationDistribution {
	private Iterator<MRVMBidder> bidders;
	private static final Map<Integer, MRVMBidder> agentIDToSATSBidder = new HashMap<>();

	/**
	 * For kryo DO NOT USE
	 */
	public CanadianSpectrumValuationDistribution() {
		super(null, null);
		this.bidders = null;
	}

	public CanadianSpectrumValuationDistribution(ICart items, List<IValuationGenerator> generators) {
		super(items, generators);
		this.bidders = null;
		reset();
	}

	private void reset() {
		this.bidders = new MRVMWorld(new MRVMWorldSetup.MRVMWorldSetupBuilder().build(), new JavaUtilRNGSupplier())
				.createPopulation(
						new MRVMLocalBidderSetup.Builder().build(),
						new MRVMRegionalBidderSetup.Builder().build(),
						new MRVMNationalBidderSetup.Builder().build(),
						new JavaUtilRNGSupplier())
				.iterator();
	}

	@Override
	public ISpecificValuation sample(Integer agentID, List<List<Integer>> agentGroups) {
		if (this.bidders == null || !this.bidders.hasNext()) {
			this.reset();
		}
		MRVMBidder b = this.bidders.next();
		agentIDToSATSBidder.put(agentID, b);
		return new SATSValuation(b);
	}
	
	public static Map<Integer, MRVMBidder> agentIDToSATSBidder() {
		return agentIDToSATSBidder;
	}

}
