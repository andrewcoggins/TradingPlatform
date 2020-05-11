package brown.auction.value.valuation.library;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.spectrumauctions.sats.core.model.Bundle;
import org.spectrumauctions.sats.core.model.gsvm.GSVMBidder;
import org.spectrumauctions.sats.core.model.gsvm.GSVMLicense;
import org.spectrumauctions.sats.core.util.file.FilePathUtils;
import org.spectrumauctions.sats.opt.model.gsvm.demandquery.GSVM_DemandQueryMIP;
import org.spectrumauctions.sats.opt.model.gsvm.demandquery.GSVM_DemandQueryMipResult;

import brown.auction.value.valuation.ISpecificValuation;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.PricedItem;
import brown.user.agent.library.SATSUtil;

public class GSVM18Valuation implements ISpecificValuation {
	private int seed;
	private int index;
	private long populationID;
	private Set<String> dqResult;
	private int agentID;
	private static Map<Integer, Integer> idToPosition = new HashMap<>();
	
	public GSVM18Valuation() {
		this.seed = 0;
		this.index = 0;
		this.populationID = 0;
		this.dqResult = new HashSet<>();
		this.agentID = -1;
	}
	
	public GSVM18Valuation(int seed, int index, long populationID, int agentID) {
		this.seed = seed;
		this.index = index;
		this.populationID = populationID;
		this.dqResult = new HashSet<>();
		this.agentID = agentID;
		this.getPosition();
	}
	
	private GSVMBidder getBidder() {
		GSVMBidder bidder;
		try {
			bidder = SATSUtil.restoreGSVM18Population(this.populationID).get(this.index);
		} catch (Exception e) {
			bidder = SATSUtil.createGSVM18Population(this.seed).get(this.index);
		}
		return bidder;
	}
	
	private int getPosition() {
		int pos = getBidder().getBidderPosition() + 1;
		if (pos == 0) {
			pos = 7;
		}
		idToPosition.put(this.agentID, pos);
		return pos;
	}
	
	public static Integer positionOf(int agentID) {
		return idToPosition.get(agentID);
	}

	@Override
	public Double getValuation(ICart cart) {
		if (cart.getItemByName("position") != null) {
			return new Integer(this.getPosition()).doubleValue();
		}
		

		GSVMBidder bidder = this.getBidder();
		
		Map<Long, GSVMLicense> allGoods = SATSUtil.mapIDToGSVM18License(bidder.getWorld());
		
		if (cart.getItemByName("demand_query") != null) {
			if (cart.getItemByName("reset") != null) {
				this.dqResult.clear();
				Map<GSVMLicense, BigDecimal> prices = new HashMap<>();
				for (GSVMLicense license : allGoods.values()) {
					String name = SATSUtil.GSVM_ID_TO_ITEM.get(license.getId());
					if (cart.containsItem(name)) {
						prices.put(license, new BigDecimal(((PricedItem)cart.getItemByName(name)).getPrice()));
					}
				}
				GSVM_DemandQueryMipResult result = new GSVM_DemandQueryMIP(bidder, prices).getResult();
				result.getResultingBundle().getLicenses().forEach(license -> this.dqResult.add(SATSUtil.GSVM_ID_TO_ITEM.get(license.getId())));
				return null;
			}
			for (IItem item : cart.getItems()) {
				if (item.getName().equals("demand_query") || item.getName().equals("reset")) {
					continue;
				}
				
				return this.dqResult.contains(item.getName()) ? 1.0 : -1.0;
			}
		}
		
//		Bundle<GSVMLicense> bundle = new Bundle<>();
//		for (IItem good : cart.getItems()) {
//			bundle.add(allGoods.get(SATSUtil.ITEM_TO_GSVM_ID.get(good.getName())));
//		}
//		return bidder.calculateValue(bundle).doubleValue();
		
		if (cart.getItems().size() == 0) {
			return 0.0;
		}
		
		double sum = 0.0;
		for (IItem good : cart.getItems()) {
			Bundle<GSVMLicense> bundle = new Bundle<>();
			bundle.add(allGoods.get(SATSUtil.ITEM_TO_GSVM_ID.get(good.getName())));
			sum += bidder.calculateValue(bundle).doubleValue();
		}
		return sum * Math.pow(1.2, cart.getItems().size() - 1);
	}

}
