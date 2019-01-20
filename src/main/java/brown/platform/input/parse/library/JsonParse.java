package brown.platform.input.parse.library;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.json.simple.parser.JSONParser;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import brown.auction.preset.AbsMarketRules;
import brown.auction.preset.FlexibleRules;
import brown.auction.rules.IActivityRule;
import brown.auction.rules.IAllocationRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IPaymentRule;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;
import brown.auction.rules.library.NoRecordKeeping;
import brown.auction.rules.library.OneGrouping;
import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.auction.value.manager.IValuationManager;
import brown.auction.value.manager.library.ValuationManager;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.ITradeableManager;
import brown.mechanism.tradeable.library.TradeableManager;
import brown.platform.accounting.IAccountManager;
import brown.platform.accounting.IEndowmentManager;
import brown.platform.accounting.library.AccountManager;
import brown.platform.accounting.library.EndowmentManager;
import brown.platform.market.IMarketManager;
import brown.platform.market.library.MarketManager;
import brown.platform.simulator.ISimulationManager;
import brown.platform.simulator.library.SimulationManager;
import brown.platform.whiteboard.IWhiteboard;
import brown.platform.whiteboard.library.Whiteboard;
import brown.platform.world.IDomainManager;
import brown.platform.world.IWorldManager;
import brown.platform.world.library.DomainManager;
import brown.platform.world.library.WorldManager;

public class JsonParse {
	  /**
    *
    * @param args
    * 0: numruns : int
    * 1: delayTime: int
    * 2: tradeabletype : string
    * 3: numtradeables : int
    * 4: distribution
    * 5: generator
    * 6: endowment tradeable type
    * 7: endowment num tradeables
    * 8: endowment money
    * 9: allocationrule
    * 10: paymentrule
    * 11: queryrule
    * 12: activityrule
    * 13: ir policy
    * 14: termination condition
	 * @throws ClassNotFoundException 
	 * @throws InstantiationException
	 * @throws IllegalAccessException 
	 * @throws InvocationTargetException 
	 * @throws NoSuchMethodException 
	 * @throws FileNotFoundException 
	 * @throws JsonIOException 
	 * @throws JsonSyntaxException 
    */
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
    InstantiationException, IllegalAccessException, InvocationTargetException, JsonSyntaxException, JsonIOException, FileNotFoundException  {

		JSONParser parser = new JSONParser();
		
		JsonData data = new Gson().fromJson(new FileReader(JsonParse.class.getResource("testjson.json").getFile()), JsonData.class);
	    Integer numRuns = new Integer(data.getNumRuns());
	    Integer delayTime = new Integer(data.getSimulation().getStartingDelay());
	    ISimulationManager simulationManager = new SimulationManager();
	    
	    for (SimulationProperty s : data.getSimulation().getProperties()) {
	    	
	    	String tTypeString = s.getTradeables().get(0).getTradeableType();
		    String distributionString = s.getValutions().getDistribution();
		    Float distributionMax = s.getValutions().getMax();
			Float distributionMin = s.getValutions().getMin();
		    String generatorString = s.getValutions().getGenerator();
		    String endowmenttTypeString = s.getEndowments().getType();
		    Integer endowmentMoney = new Integer(s.getEndowments().getMoney());
		    
			Class<?> tTypeClass = Class.forName(tTypeString);
			Class<?> generatorClass = Class.forName(generatorString);
			Class<?> distributionClass = Class.forName(distributionString);
			Class<?> endowmenttTypeClass = Class.forName(endowmenttTypeString);
  	
			Constructor<?> tTypeCons = tTypeClass.getConstructor(Integer.TYPE);
			Constructor<?> generatorCons = generatorClass.getConstructor(Double.TYPE, Double.TYPE);
			Constructor<?> distributionCons = distributionClass.getConstructor(IValuationGenerator.class, Set.class);
			Constructor<?> endowmentTypeCons = endowmenttTypeClass.getConstructor(Integer.TYPE);
			
		    List<ITradeable> allTradeables = new LinkedList<>();
		    for (Tradeables t : s.getTradeables()) {
			    Integer numTradeables = new Integer(t.getNumTradeables());
			    for (int i = 0; i < numTradeables; i++){
				      allTradeables.add((ITradeable) tTypeCons.newInstance(i));
				    }
		    	
		    }
		    
		    List<ITradeable> endowTradeables = new LinkedList<>();
		    for (Tradeables t : s.getEndowments().getTradeable()) {
			    Integer endowmentNumTradeables = new Integer(t.getNumTradeables());
			    for (int i = 0; i < endowmentNumTradeables; i++){
				      endowTradeables.add((ITradeable) endowmentTypeCons.newInstance(i));
				    }
		    }
		    
		    IWorldManager worldManager = new WorldManager();
		    IMarketManager marketManager = new MarketManager();
		    IDomainManager domainManager = new DomainManager();
		    IEndowmentManager endowmentManager = new EndowmentManager();
		    IAccountManager accountManager = new AccountManager();
		    IValuationManager valuationManager = new ValuationManager();
		    ITradeableManager tradeableManager = new TradeableManager();
		    IWhiteboard whiteboard = new Whiteboard();

		    IValuationGenerator valGenerator = (IValuationGenerator) generatorCons.newInstance(distributionMax, distributionMin);
		    IValuationDistribution valDistribution = (IValuationDistribution) distributionCons.newInstance(valGenerator,
		            new HashSet<>(allTradeables));

		    for (Seqmarket m : s.getSeqmarket()) {
		    	List<AbsMarketRules> marketList = new LinkedList<>();
		    	for (Simmarket i : m.getSimmarket()) {
			    	String aRuleString = i.getMarketrules().getAllocationRule();
				    String pRuleString = i.getMarketrules().getPaymentRule();
				    String qRuleString = i.getMarketrules().getQueryRule();
				    String actRuleString = i.getMarketrules().getActivityRule();
				    String irPolicyString = i.getMarketrules().getInformationRevelationPolicy();
				    String tConditionString = i.getMarketrules().getTerminationCondition();
				    
					Class<?> aRuleClass = Class.forName(aRuleString);
					Class<?> pRuleClass = Class.forName(pRuleString);
					Class<?> qRuleClass = Class.forName(qRuleString);
					Class<?> actRuleClass = Class.forName(actRuleString);
					Class<?> irPolicyClass = Class.forName(irPolicyString);
					Class<?> tConditionClass = Class.forName(tConditionString);
					
					Constructor<?> aRuleCons = aRuleClass.getConstructor();
					Constructor<?> pRuleCons = pRuleClass.getConstructor();
					Constructor<?> qRuleCons = qRuleClass.getConstructor();
					Constructor<?> actRuleCons = actRuleClass.getConstructor();
					Constructor<?> irPolicyCons = irPolicyClass.getConstructor();
					Constructor<?> tConditionCons = tConditionClass.getConstructor();   	
					
					 AbsMarketRules marketRule = new FlexibleRules((IAllocationRule) aRuleCons.newInstance(),
					            (IPaymentRule) pRuleCons.newInstance(),
					            (IQueryRule) qRuleCons.newInstance(),
					            new OneGrouping(),
					            (IActivityRule) actRuleCons.newInstance(),
					            (IInformationRevelationPolicy) irPolicyCons.newInstance(),
					            (ITerminationCondition) tConditionCons.newInstance(),
					            new NoRecordKeeping());

					    marketList.add(marketRule);
		    	}
			    marketManager.createSimultaneousMarket(marketList);

		    }
		   
		    endowmentManager.createEndowment(endowmentMoney, endowTradeables);
		    valuationManager.createValuation(0, valDistribution);
		    tradeableManager.createTradeables(numTradeables);
		    domainManager.createDomain(tradeableManager, valuationManager, accountManager, endowmentManager);
		    worldManager.createWorld(domainManager, marketManager, whiteboard);
		    simulationManager.createSimulation(worldManager);

		    tradeableManager.lock();
		    valuationManager.lock();
		    simulationManager.lock();
		    marketManager.lock();

	    }
	    
	    simulationManager.runSimulation(delayTime, numRuns);

	}

}

class JsonData {
	private String name;
	private String numRuns;
	private Simulation simulation;
	
	public String getNumRuns() {
		return numRuns;
	}

	public void setNumRuns(String numRuns) {
		this.numRuns = numRuns;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Simulation getSimulation() {
		return simulation;
	}

	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}

}

class Simulation {
	private String type;
	private List<SimulationProperty> properties;
	private String startingDelay;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<SimulationProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<SimulationProperty> properties) {
		this.properties = properties;
	}

	public String getStartingDelay() {
		return startingDelay;
	}

	public void setStartingDelay(String startingDelay) {
		this.startingDelay = startingDelay;
	}

}

class SimulationProperty {
	private List<Tradeables> tradeables;
	private Valuations valuations;
	private Endowments endowments;
	private List<Seqmarket> seqmarket;

	public List<Tradeables> getTradeables() {
		return tradeables;
	}

	public void setTradeables(List<Tradeables> tradeables) {
		this.tradeables = tradeables;
	}

	public Valuations getValutions() {
		return valuations;
	}

	public void setValutions(Valuations valutions) {
		this.valuations = valutions;
	}

	public Endowments getEndowments() {
		return endowments;
	}

	public void setEndowments(Endowments endowments) {
		this.endowments = endowments;
	}

	public List<Seqmarket> getSeqmarket() {
		return seqmarket;
	}

	public void setSeqmarket(List<Seqmarket> seqmarket) {
		this.seqmarket = seqmarket;
	}

}

class Tradeables {
	private String type;
	private String tradeableType;
	private String numTradeables;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTradeableType() {
		return tradeableType;
	}
	public void setTradeableType(String tradeableType) {
		this.tradeableType = tradeableType;
	}
	public String getNumTradeables() {
		return numTradeables;
	}
	public void setNumTradeables(String numTradeables) {
		this.numTradeables = numTradeables;
	}
	
}

class Valuations {
	private String type;
	private String distribution;
	private String generator;
	private Float min;
	private Float max;
	
	public Float getMin() {
		return min;
	}
	public void setMin(Float min) {
		this.min = min;
	}
	public Float getMax() {
		return max;
	}
	public void setMax(Float max) {
		this.max = max;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDistribution() {
		return distribution;
	}
	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}
	public String getGenerator() {
		return generator;
	}
	public void setGenerator(String generator) {
		this.generator = generator;
	}

}

class Endowments {
	private String type;
	private List<Tradeables> tradeable;
	private String money;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Tradeables> getTradeable() {
		return tradeable;
	}
	public void setTradeable(List<Tradeables> tradeable) {
		this.tradeable = tradeable;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}

}

class Seqmarket {
	private String type;
	private List<Simmarket> simmarket;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public List<Simmarket> getSimmarket() {
		return simmarket;
	}
	public void setSimmarket(List<Simmarket> simmarket) {
		this.simmarket = simmarket;
	}

}

class Simmarket {
	private String type;
	private MarketRules marketrules;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public MarketRules getMarketrules() {
		return marketrules;
	}
	public void setMarketrules(MarketRules marketrules) {
		this.marketrules = marketrules;
	}
	
}

class MarketRules {
	private String type;
	private String allocationRule;
	private String paymentRule;
	private String queryRule;
	private String activityRule;
	private String informationRevelationPolicy;
	private String terminationCondition;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAllocationRule() {
		return allocationRule;
	}

	public void setAllocationRule(String allocationRule) {
		this.allocationRule = allocationRule;
	}

	public String getPaymentRule() {
		return paymentRule;
	}

	public void setPaymentRule(String paymentRule) {
		this.paymentRule = paymentRule;
	}

	public String getQueryRule() {
		return queryRule;
	}

	public void setQueryRule(String queryRule) {
		this.queryRule = queryRule;
	}

	public String getActivityRule() {
		return activityRule;
	}

	public void setActivityRule(String activityRule) {
		this.activityRule = activityRule;
	}

	public String getInformationRevelationPolicy() {
		return informationRevelationPolicy;
	}

	public void setInformationRevelationPolicy(String informationRevelationPolicy) {
		this.informationRevelationPolicy = informationRevelationPolicy;
	}

	public String getTerminationCondition() {
		return terminationCondition;
	}

	public void setTerminationCondition(String terminationCondition) {
		this.terminationCondition = terminationCondition;
	}

}
