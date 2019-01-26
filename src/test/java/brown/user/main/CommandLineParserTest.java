package brown.user.main;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.*;

import brown.auction.rules.activity.onesided.OneShotActivity;
import brown.auction.rules.allocation.onesided.HighestPriceAllocation;
import brown.auction.rules.ir.onesided.AnonymousPolicy;
import brown.auction.rules.payment.onesided.SecondPricePayment;
import brown.auction.rules.query.onesided.SimpleQuery;
import brown.auction.rules.termination.onesided.OneShotTermination;
import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.distribution.library.AdditiveValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.generator.library.NormalValGenerator;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.platform.market.library.AbsMarketRules;
import brown.platform.market.library.FlexibleRules;
import brown.user.main.CommandLineParser;
import brown.user.main.EndowmentConfig;
import brown.user.main.ICommandLineParser;
import brown.user.main.IEndowmentConfig;
import brown.user.main.IMarketConfig;
import brown.user.main.ISimulationConfig;
import brown.user.main.ITradeableConfig;
import brown.user.main.MarketConfig;
import brown.user.main.SimulationConfig;
import brown.user.main.TradeableConfig;


public class CommandLineParserTest {
  
  @Test
  public void testCommandLineParser() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    
    int numRuns = 1; 
    int delayTime = 1; 
    String tTypeString = "SimpleTradeable"; 
    int numTradeables = 5; 
    String distributionString = "AdditiveValuationDistribution"; 
    String generatorString = "NormalValGenerator"; 
    int endowmentNumTradeables = 4; 
    double endowmentMoney = 100.0; 
    String aRuleString = "HighestPriceAllocation"; 
    String pRuleString = "SecondPricePayment"; 
    String qRuleString = "SimpleQuery"; 
    String actRuleString = "OneShotActivity"; 
    String irPolicyString = "AnonymousPolicy"; 
    String tConditionString = "OneShotTermination"; 
    
    
    ICommandLineParser p = new CommandLineParser(); 
    ISimulationConfig sConfig = p.parseCommandLine(numRuns, delayTime,
        tTypeString, numTradeables, distributionString, generatorString,
        endowmentNumTradeables, endowmentMoney, aRuleString, pRuleString,
        qRuleString, actRuleString, irPolicyString, tConditionString);  
    
    Map<String, Integer> eMap = new HashMap<String, Integer>(); 
    eMap.put("default", endowmentNumTradeables); 
    IEndowmentConfig eConfig = new EndowmentConfig("default", eMap, endowmentMoney);
    List<IEndowmentConfig> eConfigList = new LinkedList<IEndowmentConfig>(); 
    eConfigList.add(eConfig); 
    
    // tradeableConfig
    List<ITradeable> allTradeables = new LinkedList<ITradeable>();
    for (int i = 0; i < numTradeables; i++) {
      allTradeables.add(new SimpleTradeable(i)); 
    }
    IValuationGenerator valGenerator = new NormalValGenerator(0.0, 1.0); 
    IValuationDistribution dist = new AdditiveValuationDistribution(valGenerator, new HashSet<ITradeable>(allTradeables)); 
    List <ITradeableConfig> tConfigList = new LinkedList<ITradeableConfig>(); 
    ITradeableConfig tConfig = new TradeableConfig("default", allTradeables.get(0).getType(), allTradeables.size(), dist); 
    tConfigList.add(tConfig); 
    
    
    // marketConfig
    AbsMarketRules marketRule = new FlexibleRules(new HighestPriceAllocation(),
        new SecondPricePayment(),
        new SimpleQuery(),
        new OneShotActivity(),
        new AnonymousPolicy(),
        new OneShotTermination());
    // marketConfig
    Map<String, Integer> mMap = new HashMap<String, Integer>(); 
    mMap.put("default", allTradeables.size()); 
    List<List<IMarketConfig>> mConfigSquared = new LinkedList<List<IMarketConfig>>(); 
    List<IMarketConfig> mConfigList = new LinkedList<IMarketConfig>(); 
    IMarketConfig mConfig = new MarketConfig(marketRule, mMap);
    mConfigList.add(mConfig); 
    mConfigSquared.add(mConfigList); 
    
    ISimulationConfig testConfig = new SimulationConfig(numRuns, tConfigList, eConfigList, mConfigSquared); 
    
    assertEquals(sConfig, testConfig); 
  }
  
  public static void main(String[] args) {
    
  }
}