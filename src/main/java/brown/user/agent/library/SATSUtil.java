package brown.user.agent.library;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;import com.google.common.collect.Sets;

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
	
	public static final Map<Integer, Set<String>> ELIGIBLE_GOODS = 
			new ImmutableMap.Builder<Integer, Set<String>>()
			.put(1, ImmutableSet.of("A", "B", "C", "D", "M", "N"))
			.put(2, ImmutableSet.of("C", "D", "E", "F", "N", "O"))
			.put(3, ImmutableSet.of("E", "F", "G", "H", "O", "P"))
			.put(4, ImmutableSet.of("G", "H", "I", "J", "P", "Q"))
			.put(5, ImmutableSet.of("I", "J", "K", "L", "Q", "R"))
			.put(6, ImmutableSet.of("K", "L", "A", "B", "R", "M"))
			.put(7, ImmutableSet.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"))
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
		res.sort(new Comparator<GSVMBidder>() {

			@Override
			public int compare(GSVMBidder o1, GSVMBidder o2) {
				int p1 = o1.getBidderPosition();
				int p2 = o2.getBidderPosition();
				if (p1 == -1) {
					p1 = 6;
				}
				if (p2 == -1) {
					p2 = 6;
				}
				return Integer.compare(p1, p2);
			}
		});
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
