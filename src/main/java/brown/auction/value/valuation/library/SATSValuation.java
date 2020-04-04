package brown.auction.value.valuation.library;

import org.spectrumauctions.sats.core.model.Bidder;
import org.spectrumauctions.sats.core.model.Bundle;

import brown.auction.value.valuation.ISpecificValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.SATSItem;

public class SATSValuation implements ISpecificValuation {
	private Bidder<?> bidder; 
	
	public SATSValuation(Bidder<?> bidder) {
		this.bidder = bidder;
	}

	@Override
	public Double getValuation(ICart cart) {
		Bundle bundle = new Bundle<>();
		for (IItem good : cart.getItems()) {
			bundle.add(((SATSItem)good).good());
		}
		return this.bidder.getValue(bundle);
	}

}
