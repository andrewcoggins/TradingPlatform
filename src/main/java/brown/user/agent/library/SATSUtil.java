package brown.user.agent.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.spectrumauctions.sats.core.model.gsvm.GSVMBidder;
import org.spectrumauctions.sats.core.model.gsvm.GSVMLicense;
import org.spectrumauctions.sats.core.model.gsvm.GSVMNationalBidderSetup;
import org.spectrumauctions.sats.core.model.gsvm.GSVMRegionalBidderSetup;
import org.spectrumauctions.sats.core.model.gsvm.GSVMWorld;
import org.spectrumauctions.sats.core.model.gsvm.GSVMWorldSetup.GSVMWorldSetupBuilder;
import org.spectrumauctions.sats.core.util.random.JavaUtilRNGSupplier;
import org.spectrumauctions.sats.core.util.random.RNGSupplier;

import com.google.common.collect.Lists;

public class SATSUtil {
	public static List<GSVMBidder> createGSVM18Population(int seed) {
		RNGSupplier rng = new JavaUtilRNGSupplier(seed);
		RNGSupplier rng2 = new JavaUtilRNGSupplier(seed + 1);
		return new GSVMWorld(new GSVMWorldSetupBuilder().build(), rng).createPopulation(
				Lists.newArrayList(new GSVMRegionalBidderSetup.Builder().build()),
				Lists.newArrayList(new GSVMNationalBidderSetup.Builder().build()),
				rng2);
	}
	
	public static GSVMBidder createGSVM18Bidder(int seed, int index) {
		return createGSVM18Population(seed).get(index);
	}
	
	public static Map<Long, GSVMLicense> mapIDToGSVM18License(GSVMWorld world) {
		Map<Long, GSVMLicense> result = new HashMap<>();
		for (GSVMLicense license : world.getLicenses()) {
			result.put(license.getId(), license);
		}
		return result;
	}
}
