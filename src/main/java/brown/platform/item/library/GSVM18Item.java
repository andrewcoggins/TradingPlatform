package brown.platform.item.library;

import org.spectrumauctions.sats.core.model.gsvm.GSVMLicense;

import brown.user.agent.library.SATSUtil;

public class GSVM18Item extends Item {
	private long id;
	private int seed;
	
	public GSVM18Item() {
		this.id = 0;
		this.seed = 0;
	}
	
	public GSVM18Item(long id, int seed) {
		super(Long.toString(id));
		this.seed = seed;
	}
	
	public GSVMLicense toLicense() {
		return SATSUtil.mapIDToGSVM18License(SATSUtil.createGSVM18Population(this.seed).get(0).getWorld()).get(this.id);
	}
}
