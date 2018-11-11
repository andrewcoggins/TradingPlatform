package brown.user.simulations;

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
import brown.auction.value.manager.ValuationManager;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.ITradeableManager;
import brown.mechanism.tradeable.library.TradeableManager;
import brown.platform.accounting.IAccountManager;
import brown.platform.accounting.IEndowmentManager;
import brown.platform.accounting.library.AccountManager;
import brown.platform.accounting.library.EndowmentManager;
import brown.platform.market.IMarketManager;
import brown.platform.market.library.MarketManager;
import brown.platform.simulation.ISimulationManager;
import brown.platform.simulation.library.SimulationManager;
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
    */
	public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

		JSONParser parser = new JSONParser();

		JsonData data = new Gson().fromJson(new FileReader((JsonParse.class.getResource("testjson.json").getFile())),
				JsonData.class);
		System.out.println();

	    Integer numRuns = new Integer(1);
	    Integer delayTime = new Integer(data.getSimulation().getStartingDelay().getType());
	    String tTypeString = data.getSimulation().getProperties().get(0).getTradeables().get(0).getProperties().getTradeableType().getType();
	    Integer numTradeables = new Integer(data.getSimulation().getProperties().get(0).getTradeables().get(0).getProperties().getNumTradeables().getType());
	    String distributionString = data.getSimulation().getProperties().get(0).getValutions().getProperties().getDistribution().getType();
	    String generatorString = data.getSimulation().getProperties().get(0).getValutions().getProperties().getGenerator().getType();
	    String endowmenttTypeString = data.getSimulation().getProperties().get(0).getEndowments().getType();
	    Integer endowmentNumTradeables = new Integer(data.getSimulation().getProperties().get(0).getEndowments().getProperties().getTradeable().get(0).getProperties().getNumTradeables().getType());
	    Integer endowmentMoney = new Integer(data.getSimulation().getProperties().get(0).getEndowments().getProperties().getMoney().getType());
	    String aRuleString = data.getSimulation().getProperties().get(0).getSeqmarket().get(0).getProperties().getSimmarket().get(0).getProperties().getMarketrules().getProperties().getAllocationRule().getType();
	    String pRuleString = data.getSimulation().getProperties().get(0).getSeqmarket().get(0).getProperties().getSimmarket().get(0).getProperties().getMarketrules().getProperties().getPaymentRule().getType();
	    String qRuleString = data.getSimulation().getProperties().get(0).getSeqmarket().get(0).getProperties().getSimmarket().get(0).getProperties().getMarketrules().getProperties().getQueryRule().getType();
	    String actRuleString = data.getSimulation().getProperties().get(0).getSeqmarket().get(0).getProperties().getSimmarket().get(0).getProperties().getMarketrules().getProperties().getActivityRule().getType();
	    String irPolicyString =  data.getSimulation().getProperties().get(0).getSeqmarket().get(0).getProperties().getSimmarket().get(0).getProperties().getMarketrules().getProperties().getInformationRevelationPolicy().getType();
	    String tConditionString =  data.getSimulation().getProperties().get(0).getSeqmarket().get(0).getProperties().getSimmarket().get(0).getProperties().getMarketrules().getProperties().getTerminationCondition().getType();
	    
		Class<?> tTypeClass = Class.forName(tTypeString);
		Class<?> generatorClass = Class.forName(generatorString);
		Class<?> distributionClass = Class.forName(distributionString);
		Class<?> endowmenttTypeClass = Class.forName(endowmenttTypeString);
		Class<?> aRuleClass = Class.forName(aRuleString);
		Class<?> pRuleClass = Class.forName(pRuleString);
		Class<?> qRuleClass = Class.forName(qRuleString);
		Class<?> actRuleClass = Class.forName(actRuleString);
		Class<?> irPolicyClass = Class.forName(irPolicyString);
		Class<?> tConditionClass = Class.forName(tConditionString);

		Constructor<?> tTypeCons = tTypeClass.getConstructor(Integer.TYPE);
		Constructor<?> generatorCons = generatorClass.getConstructor(Double.TYPE, Double.TYPE);
		Constructor<?> distributionCons = distributionClass.getConstructor(IValuationGenerator.class, Set.class);
		Constructor<?> endowmentTypeCons = endowmenttTypeClass.getConstructor(Integer.TYPE);
		Constructor<?> aRuleCons = aRuleClass.getConstructor();
		Constructor<?> pRuleCons = pRuleClass.getConstructor();
		Constructor<?> qRuleCons = qRuleClass.getConstructor();
		Constructor<?> actRuleCons = actRuleClass.getConstructor();
		Constructor<?> irPolicyCons = irPolicyClass.getConstructor();
		Constructor<?> tConditionCons = tConditionClass.getConstructor();

	    List<ITradeable> allTradeables = new LinkedList<>();
	    for (int i = 0; i < numTradeables; i++){
	      allTradeables.add((ITradeable) tTypeCons.newInstance(i));
	    }
	    List<ITradeable> endowTradeables = new LinkedList<>();
	    for (int i = 0; i < endowmentNumTradeables; i++){
	      endowTradeables.add((ITradeable) endowmentTypeCons.newInstance(i));
	    }
	    IValuationGenerator valGenerator = (IValuationGenerator) generatorCons.newInstance(1.0, 0.0);
	    IValuationDistribution valDistribution = (IValuationDistribution) distributionCons.newInstance(valGenerator,
	            new HashSet<>(allTradeables));
	    IWorldManager worldManager = new WorldManager();
	    IMarketManager marketManager = new MarketManager();
	    IDomainManager domainManager = new DomainManager();
	    IEndowmentManager endowmentManager = new EndowmentManager();
	    IAccountManager accountManager = new AccountManager();
	    IValuationManager valuationManager = new ValuationManager();
	    ITradeableManager tradeableManager = new TradeableManager();
	    ISimulationManager simulationManager = new SimulationManager();
	    IWhiteboard whiteboard = new Whiteboard();

	    AbsMarketRules marketRule = new FlexibleRules((IAllocationRule) aRuleCons.newInstance(),
	            (IPaymentRule) pRuleCons.newInstance(),
	            (IQueryRule) qRuleCons.newInstance(),
	            new OneGrouping(),
	            (IActivityRule) actRuleCons.newInstance(),
	            (IInformationRevelationPolicy) irPolicyCons.newInstance(),
	            (ITerminationCondition) tConditionCons.newInstance(),
	            new NoRecordKeeping());
	    List<AbsMarketRules> marketList = new LinkedList<>();
	    marketList.add(marketRule);
	    marketManager.createSimultaneousMarket(marketList);
	    endowmentManager.createEndowment(endowmentMoney, endowTradeables);
	    valuationManager.createValuation(0, valDistribution);
	    tradeableManager.createSimpleTradeables(numTradeables);
	    domainManager.createDomain(tradeableManager, valuationManager, accountManager, endowmentManager);
	    worldManager.createWorld(domainManager, marketManager, whiteboard);
	    simulationManager.createSimulation(worldManager);

	    tradeableManager.lock();
	    valuationManager.lock();
	    simulationManager.lock();
	    marketManager.lock();

	    simulationManager.runSimulation(delayTime, numRuns);
	}

}

class JsonData {
	private Type name;
	private Simulation simulation;

	public Type getName() {
		return name;
	}

	public void setName(Type name) {
		this.name = name;
	}

	public Simulation getSimulation() {
		return simulation;
	}

	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}

}

class Type {
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

class Simulation {
	private String type;
	private List<SimulationProperty> properties;
	private Type startingDelay;

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

	public Type getStartingDelay() {
		return startingDelay;
	}

	public void setStartingDelay(Type startingDelay) {
		this.startingDelay = startingDelay;
	}

}

class SimulationProperty {
	private List<Tradeables> tradeables;
	private Valuations valutions;
	private Endowments endowments;
	private List<Seqmarket> seqmarket;

	public List<Tradeables> getTradeables() {
		return tradeables;
	}

	public void setTradeables(List<Tradeables> tradeables) {
		this.tradeables = tradeables;
	}

	public Valuations getValutions() {
		return valutions;
	}

	public void setValutions(Valuations valutions) {
		this.valutions = valutions;
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
	private TradeableProperty properties;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TradeableProperty getProperties() {
		return properties;
	}

	public void setProperties(TradeableProperty properties) {
		this.properties = properties;
	}

}

class TradeableProperty {
	private Type tradeableType;
	private Type numTradeables;

	public Type getTradeableType() {
		return tradeableType;
	}

	public void setTradeableType(Type tradeableType) {
		this.tradeableType = tradeableType;
	}

	public Type getNumTradeables() {
		return numTradeables;
	}

	public void setNumTradeables(Type numTradeables) {
		this.numTradeables = numTradeables;
	}

}

class Valuations {
	private String type;
	private ValuationProperty properties;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ValuationProperty getProperties() {
		return properties;
	}

	public void setProperties(ValuationProperty properties) {
		this.properties = properties;
	}

}

class ValuationProperty {
	private Type distribution;
	private Type generator;

	public Type getDistribution() {
		return distribution;
	}

	public void setDistribution(Type distribution) {
		this.distribution = distribution;
	}

	public Type getGenerator() {
		return generator;
	}

	public void setGenerator(Type generator) {
		this.generator = generator;
	}

}

class Endowments {
	private String type;
	private EndowmentProperty properties;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public EndowmentProperty getProperties() {
		return properties;
	}

	public void setProperties(EndowmentProperty properties) {
		this.properties = properties;
	}

}

class EndowmentProperty {
	private List<Tradeables> tradeable;
	private Type money;
	public List<Tradeables> getTradeable() {
		return tradeable;
	}

	public void setTradeable(List<Tradeables> tradeable) {
		this.tradeable = tradeable;
	}
	public Type getMoney() {
		return money;
	}

	public void setMoney(Type money) {
		this.money = money;
	}
	
}

class Seqmarket {
	private String type;
	private SeqmarketProperty properties;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public SeqmarketProperty getProperties() {
		return properties;
	}

	public void setProperties(SeqmarketProperty properties) {
		this.properties = properties;
	}

}

class SeqmarketProperty {
	private List<Simmarket> simmarket;

	public List<Simmarket> getSimmarket() {
		return simmarket;
	}

	public void setSimmarket(List<Simmarket> simmarket) {
		this.simmarket = simmarket;
	}

}

class Simmarket {
	private String type;
	private SimmarketProperty properties;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public SimmarketProperty getProperties() {
		return properties;
	}

	public void setProperties(SimmarketProperty properties) {
		this.properties = properties;
	}

}

class SimmarketProperty {
	private MarketRules marketrules;

	public MarketRules getMarketrules() {
		return marketrules;
	}

	public void setMarketrules(MarketRules marketrules) {
		this.marketrules = marketrules;
	}

}

class MarketRules {
	private String type;
	private MarketRulesProperty properties;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MarketRulesProperty getProperties() {
		return properties;
	}

	public void setProperties(MarketRulesProperty properties) {
		this.properties = properties;
	}

}

class MarketRulesProperty {
	private Type allocationRule;
	private Type paymentRule;
	private Type queryRule;
	private Type activityRule;
	private Type informationRevelationPolicy;
	private Type terminationCondition;

	public Type getAllocationRule() {
		return allocationRule;
	}

	public void setAllocationRule(Type allocationRule) {
		this.allocationRule = allocationRule;
	}

	public Type getPaymentRule() {
		return paymentRule;
	}

	public void setPaymentRule(Type paymentRule) {
		this.paymentRule = paymentRule;
	}

	public Type getQueryRule() {
		return queryRule;
	}

	public void setQueryRule(Type queryRule) {
		this.queryRule = queryRule;
	}

	public Type getActivityRule() {
		return activityRule;
	}

	public void setActivityRule(Type activityRule) {
		this.activityRule = activityRule;
	}

	public Type getInformationRevelationPolicy() {
		return informationRevelationPolicy;
	}

	public void setInformationRevelationPolicy(Type informationRevelationPolicy) {
		this.informationRevelationPolicy = informationRevelationPolicy;
	}

	public Type getTerminationCondition() {
		return terminationCondition;
	}

	public void setTerminationCondition(Type terminationCondition) {
		this.terminationCondition = terminationCondition;
	}

}