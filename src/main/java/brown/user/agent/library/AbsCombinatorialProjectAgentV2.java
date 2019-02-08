package brown.user.agent.library;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.spectrumauctions.sats.core.bidlang.xor.SizeBasedUniqueRandomXOR;
import org.spectrumauctions.sats.core.bidlang.xor.XORValue;
import org.spectrumauctions.sats.core.model.Bundle;
import org.spectrumauctions.sats.core.model.UnsupportedBiddingLanguageException;
import org.spectrumauctions.sats.core.model.mrvm.MRVMBidder;
import org.spectrumauctions.sats.core.model.mrvm.MRVMGenericDefinition;
import org.spectrumauctions.sats.core.model.mrvm.MRVMLicense;
import org.spectrumauctions.sats.opt.model.mrvm.demandquery.MRVMDemandQueryMipResult;
import org.spectrumauctions.sats.opt.model.mrvm.demandquery.MRVM_DemandQueryMIP;

import brown.communication.bid.library.BidType;
import brown.communication.bidbundle.library.OneSidedBidBundle;
import brown.communication.channel.library.OpenOutcryChannel;
import brown.communication.messages.library.BankUpdateMessage;
import brown.communication.messages.library.CombinatorialClockReport;
import brown.communication.messages.library.GameReportMessage;
import brown.communication.messages.library.PrivateInformationMessage;
import brown.logging.library.Logging;
import brown.platform.tradeable.ITradeable;
import brown.platform.tradeable.library.SimpleTradeable;
import brown.system.setup.library.SpecValSetup;

/**
 * Alternate version of the combinatorial project agent. Used in the final project for 
 * cs1951k
 * @author acoggins
 *
 */
public abstract class AbsCombinatorialProjectAgentV2  extends AbsSpecValV2Agent {
  
  private static final int NUM_GOODS = 98;
  
  protected final double[] prices = new double[NUM_GOODS];
  protected final double[] allocations = new double[NUM_GOODS];

  public AbsCombinatorialProjectAgentV2(String host, int port, String name) {
    super(host, port, new SpecValSetup(), name);
  }
  
  public AbsCombinatorialProjectAgentV2(String host, int port) {
    super(host, port, new SpecValSetup());
  }
  
  @Override
  public void onClockMarket(OpenOutcryChannel channel) {
    // update current prices
    Map<ITradeable, Double> priceMap = channel.getReserve();
    for (ITradeable t : priceMap.keySet()) {
      prices[t.getID()] = priceMap.get(t);
    }
    
    // get agent's bundle of interested goods
    Set<Integer> bundle = onBidRound();
    
    // submit bid to server
    Map<ITradeable, BidType> bid = new HashMap<>();
    for (Integer i : bundle) {
      int good = Math.max(0, Math.min(NUM_GOODS-1, zeroIfNull(i)));
      SimpleTradeable st = new SimpleTradeable(good);
      BidType bt = new BidType(prices[good], 1);
      bid.put(st, bt);
    }
    channel.bid(this, new OneSidedBidBundle(bid));
  }
  
  @Override
  public void onGameReport(GameReportMessage gameReport) {
    System.out.println("Game report received");
    if (gameReport instanceof CombinatorialClockReport) {
      // cast to CombinatorialClockReport
      CombinatorialClockReport report = (CombinatorialClockReport) gameReport;
      
      // get goods that we're currently allocated
      for (Entry<ITradeable, Double> entry : report.winnings.entrySet()) {
        allocations[entry.getKey().getID()] = entry.getValue();
      }
      
      // anything else students want to do after a bid round
      onBidResults(allocations);
    }
  }
  
  @Override
  public void onPrivateInformation(PrivateInformationMessage privateInfo) {
    super.onPrivateInformation(privateInfo);

    // whatever else students want to do when an auction begins
    onAuctionStart();
  }
  
  @Override
  public synchronized void onBankUpdate(BankUpdateMessage update) {
    super.onBankUpdate(update);
    
    // get the bundle of goods we won
    Set<Integer> bundle = new HashSet<>();
    for (SimpleTradeable st : update.tradeableAdded.flatten()) {
      bundle.add(st.ID);
    }
    
    // whatever students want to do after an auction ends
    onAuctionEnd(bundle);
  }
  
  public double[] getPrices() {
    return prices;
  }
  
  public double getBundlePrice(Set<Integer> bundle){
    double price = 0;
    for (Integer i : bundle){
      price = price + this.prices[i];
    }
    return(price);
  }
  
  public double[] getAllocations() {
    return allocations;
  }
  

  public double queryValue(Set<Integer> bundle) {
    Set<MRVMLicense> mrvmBundle = new HashSet<MRVMLicense>();
    for (Integer good : bundle){      
      mrvmBundle.add(this.idToLicense.get(good));
    }
    return (this.valueScale * this.valuation.calculateValue(new Bundle<MRVMLicense>(mrvmBundle)).doubleValue());
  }
  
  public Map<Set<Integer>,Double> queryXORs(Integer n, Integer mean, Integer stdev) {
    Map<Set<Integer>, Double> toReturn = new HashMap<Set<Integer>, Double>();    
    SizeBasedUniqueRandomXOR<?> valueFunction;
    try {
      valueFunction = (SizeBasedUniqueRandomXOR<?>) valuation.getValueFunction(SizeBasedUniqueRandomXOR.class);
      valueFunction.setDistribution(mean, stdev, n);
      // Do something with the generated bids
      Iterator<? extends XORValue<?>> xorBidIterator = valueFunction.iterator();
      while (xorBidIterator.hasNext()) {
          XORValue bid = xorBidIterator.next();
          Bundle<MRVMLicense> licenses = bid.getLicenses();
          Set<Integer> goods = new HashSet<Integer>();
          for (MRVMLicense license : licenses){
            goods.add((int) license.getId());
          }        
          // Always just make ID 0?
          toReturn.put(goods, this.valueScale * this.valuation.calculateValue(licenses).doubleValue());
      }
      return toReturn;
    } catch (UnsupportedBiddingLanguageException e) {
      Logging.log("Unsupported Bidding Language Exception");
      return toReturn;
    }
  }
  
  public double sampleValue(Set<Integer> bundle) {
    // MRVMBidder sampleValuation = this.model.createPopulation(this.model.createWorld()).get(0);    
    List<MRVMBidder> sampleValuations = this.model.createPopulation(this.model.createWorld());
    double toReturn = 0;
    Set<MRVMLicense> mrvmBundle = new HashSet<MRVMLicense>();
    for (Integer good : bundle){      
      mrvmBundle.add(this.idToLicense.get(good));
    }    
    for (MRVMBidder val : sampleValuations){
      toReturn=toReturn+this.valueScale * val.calculateValue(new Bundle<MRVMLicense>(mrvmBundle)).doubleValue();
    }
    return (toReturn / sampleValuations.size());
  }
  
  public Map<Set<Integer>, Double> sampleValues(Set<Set<Integer>> bundles) {
    Map<Set<Integer>,Double> toReturn = new HashMap<Set<Integer>,Double>();    
    List<MRVMBidder> sampleValuations = this.model.createPopulation(this.model.createWorld());   
    
    for (Set<Integer> bundle : bundles){
      Set<MRVMLicense> mrvmBundle = new HashSet<MRVMLicense>();
      for (Integer good : bundle){      
        mrvmBundle.add(this.idToLicense.get(good));
      }    
      double sum = 0;
      for (MRVMBidder val : sampleValuations){
        //Logging.log("bundle" + mrvmBundle.size());
        try{
        sum = sum +this.valueScale * val.calculateValue(new Bundle<MRVMLicense>(mrvmBundle)).doubleValue();        
        }catch(IllegalArgumentException e){
          Logging.log("bundle" + mrvmBundle.size());
          Logging.log("SUM" + sum);                
        }
      }    
      toReturn.put(bundle, sum / sampleValuations.size());
    }
    return toReturn;
  }
  
  /**
   * gets favorite bundle at current prices. 
   * @return
   * the favorite bundle from an agent's valuation at the current prices. 
   * @author andrew
   */
  public Map<Set<Integer>, Double> getFavoriteBundle() {
    Map<Set<Integer>, Double> returnMap = new HashMap<Set<Integer>, Double>();
    Set<Integer> returnSet = new HashSet<Integer>();
    // create the map.
    Map<MRVMGenericDefinition, BigDecimal> em = new HashMap<MRVMGenericDefinition, BigDecimal>();
    double[] p = this.prices;
    // from our prices, create a set of MRMGenericDefinition associated with the prices.
    // map of liecenses to IDs. Assuming an injective mapping.
    Map<MRVMLicense, Integer> licenseToId = new HashMap<MRVMLicense, Integer>();
    for (int i= 0; i < p.length; i++) {
      licenseToId.put(this.idToLicense.get(i), i);
      MRVMGenericDefinition genericDef = new MRVMGenericDefinition(this.idToLicense.get(i).getBand(), this.idToLicense.get(i).getRegion());
      em.put(genericDef, new BigDecimal(p[i]));
    }
    // create and solve.
    MRVM_DemandQueryMIP mip = new MRVM_DemandQueryMIP(this.valuation, em);
    MRVMDemandQueryMipResult result = mip.getResult();
    Map<MRVMGenericDefinition, Integer> quant = result.getResultingBundle().getQuantities();
    BigDecimal price = result.getResultingBundle().getValue();
    //convert back into usable form.
    for (MRVMGenericDefinition def : quant.keySet()) {
       for (MRVMLicense l: this.idToLicense.values()) {
         if (l.getBand().equals(def.getBand()) && l.getRegion().equals(def.getRegion())) {
           returnSet.add(licenseToId.get(l));
         }
       }
    }
    returnMap.put(returnSet, price.doubleValue());
    return returnMap;
  }

  private int zeroIfNull(Integer i) {
    return i == null ? 0 : i;
  }
  
  public abstract Set<Integer> onBidRound();
  public abstract void onBidResults(double[] allocations);
  public abstract void onAuctionStart();
  public abstract void onAuctionEnd(Set<Integer> finalBundle);
}
