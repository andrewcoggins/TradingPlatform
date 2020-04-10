package brown.user.agent.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.spectrumauctions.sats.core.model.gsvm.GSVMBidder;
import org.spectrumauctions.sats.core.model.gsvm.GSVMLicense;
import org.spectrumauctions.sats.core.model.gsvm.GSVMNationalBidderSetup;
import org.spectrumauctions.sats.core.model.gsvm.GSVMRegionalBidderSetup;
import org.spectrumauctions.sats.core.model.gsvm.GSVMWorld;
import org.spectrumauctions.sats.core.model.gsvm.GSVMWorldSetup.GSVMWorldSetupBuilder;
import org.spectrumauctions.sats.core.model.gsvm.GlobalSynergyValueModel;
import org.spectrumauctions.sats.core.util.file.FilePathUtils;
import org.spectrumauctions.sats.core.util.random.JavaUtilRNGSupplier;
import org.spectrumauctions.sats.core.util.random.RNGSupplier;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class SATSUtil {
	public static final GSVMWorld WORLD = new GlobalSynergyValueModel().createWorld(System.currentTimeMillis() % 10000);
			
	public static final Map<String, Long> ITEM_TO_GSVM_ID = new ImmutableMap.Builder<String, Long>()
			.put("A", 0l)
			.put("B", 1l)
			.put("C", 2l)
			.put("D", 3l)
			.put("E", 4l)
			.put("F", 5l)
			.put("G", 6l)
			.put("H", 7l)
			.put("I", 8l)
			.put("J", 9l)
			.put("K", 10l)
			.put("L", 11l)
			.put("M", 12l)
			.put("N", 13l)
			.put("O", 14l)
			.put("P", 15l)
			.put("Q", 16l)
			.put("R", 17l)
			.build();
	
	public static final Map<Long, String> GSVM_ID_TO_ITEM = ImmutableMap.copyOf(
			ITEM_TO_GSVM_ID.entrySet().stream()
			.collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey)));
	
	public static List<GSVMBidder> createGSVM18Population(int seed) {
		RNGSupplier rng2 = new JavaUtilRNGSupplier(seed);
		return WORLD.createPopulation(
				Lists.newArrayList(new GSVMRegionalBidderSetup.Builder().build()),
				Lists.newArrayList(new GSVMNationalBidderSetup.Builder().build()),
				rng2);
	}
	
	public static List<GSVMBidder> restoreGSVM18Population(long populationID) {
		List<GSVMBidder> res = new ArrayList<>();
		WORLD.restorePopulation(populationID).forEach(bidder -> res.add((GSVMBidder)bidder));
		return res;
	}
	
	public static Map<Long, GSVMLicense> mapIDToGSVM18License(GSVMWorld world) {
		Map<Long, GSVMLicense> result = new HashMap<>();
		for (GSVMLicense license : world.getLicenses()) {
			result.put(license.getId(), license);
		}
		return result;
	}
}
