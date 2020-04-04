package brown.platform.item.library;

import org.spectrumauctions.sats.core.model.Good;

public class SATSItem extends Item {
	private Good good;
	
	public SATSItem(Good good) {
		super(Long.toString(good.getId()));
		this.good = good;
	}
	
	public Good good() {
		return this.good;
	}
}
