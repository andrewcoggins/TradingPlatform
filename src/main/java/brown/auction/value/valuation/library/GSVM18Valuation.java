package brown.auction.value.valuation.library;

import java.util.Map;

import org.spectrumauctions.sats.core.model.Bundle;
import org.spectrumauctions.sats.core.model.gsvm.GSVMBidder;
import org.spectrumauctions.sats.core.model.gsvm.GSVMLicense;

import brown.auction.value.valuation.ISpecificValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.user.agent.library.SATSUtil;

public class GSVM18Valuation implements ISpecificValuation {
	private int seed;
	private int index;
	
	public GSVM18Valuation() {
		this.seed = 0;
		this.index = 0;
	}
	
	public GSVM18Valuation(int seed, int index) {
		this.seed = seed;
		this.index = index;
	}

	@Override
	public Double getValuation(ICart cart) {
		if (cart.getItemByName("seed") != null) {
			return new Integer(this.seed).doubleValue();
		}
		
		if (cart.getItemByName("index") != null) {
			return new Integer(this.index).doubleValue();
		}
		
		GSVMBidder bidder = SATSUtil.createGSVM18Bidder(this.seed, this.index);
		
		if (cart.getItemByName("position") != null) {
			return new Integer(bidder.getBidderPosition()).doubleValue();
		}
		
		Bundle<GSVMLicense> bundle = new Bundle<>();
		Map<Long, GSVMLicense> allGoods = SATSUtil.mapIDToGSVM18License(bidder.getWorld());
		for (IItem good : cart.getItems()) {
			bundle.add(allGoods.get(Long.parseLong(good.getName())));
		}
		return bidder.calculateValue(bundle).doubleValue();
	}

}
