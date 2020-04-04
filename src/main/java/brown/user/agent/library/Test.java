package brown.user.agent.library;

import java.util.List;

import org.spectrumauctions.sats.core.model.Good;
import org.spectrumauctions.sats.core.model.World;
import org.spectrumauctions.sats.core.model.mrvm.MRVMBidder;
import org.spectrumauctions.sats.core.model.mrvm.MRVMBidderSetup;
import org.spectrumauctions.sats.core.model.mrvm.MRVMLocalBidder;
import org.spectrumauctions.sats.core.model.mrvm.MRVMLocalBidderSetup;
import org.spectrumauctions.sats.core.model.mrvm.MRVMNationalBidder;
import org.spectrumauctions.sats.core.model.mrvm.MRVMNationalBidderSetup;
import org.spectrumauctions.sats.core.model.mrvm.MRVMRegionalBidder;
import org.spectrumauctions.sats.core.model.mrvm.MRVMRegionalBidderSetup;
import org.spectrumauctions.sats.core.model.mrvm.MRVMWorld;
import org.spectrumauctions.sats.core.model.mrvm.MRVMWorldSetup;
import org.spectrumauctions.sats.core.util.random.JavaUtilRNGSupplier;
import org.spectrumauctions.sats.core.util.random.RNGSupplier;

public class Test {
	public static void main(String[] args) {
		MRVMWorld w = new MRVMWorld(new MRVMWorldSetup.MRVMWorldSetupBuilder().build(), new JavaUtilRNGSupplier());
		List<MRVMBidder> p = w.createPopulation(
				new MRVMLocalBidderSetup.Builder().build(),
				new MRVMRegionalBidderSetup.Builder().build(),
				new MRVMNationalBidderSetup.Builder().build(),
				new JavaUtilRNGSupplier());
		System.out.println(p.size());
		for (MRVMBidder b : p) {
			System.out.println(b instanceof MRVMNationalBidder);
		}
		for (Good g : w.getLicenses()) {
			System.out.println(Long.toString(g.getId()));
		}
	}
}
