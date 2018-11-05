package brown.user.simulations;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

public class JsonParse {
	
	public static void main(String[] args) throws IOException {
		String js = "{" + 
				"  'name': { 'type': 'text' }," + 
				"  'simulation': {" + 
				"    'type': 'nested'," + 
				"    'properties': [" + 
				"      {" + 
				"        'tradeables': [" + 
				"          {" + 
				"            'type': 'nested'," + 
				"            'properties': {" + 
				"              'tradeableType': { 'type': 'text' }," + 
				"              'numTradeables': { 'type': 'integer' }" + 
				"            }" + 
				"          }" + 
				"        ]," + 
				"        'valuations': {" + 
				"          'type': 'nested'," + 
				"          'properties': {" + 
				"            'distribution': { 'type': 'text' }," + 
				"            'generator': { 'type': 'text' }" + 
				"          }" + 
				"        }," + 
				"        'endowments': {" + 
				"          'type': 'nested'," + 
				"          'properties': {" + 
				"            'tradeable': [" + 
				"              {" + 
				"                'type': 'nested'," + 
				"                'properties': {" + 
				"                  'tradeableType': { 'type': 'text' }," + 
				"                  'numTradeables': { 'type': 'integer' }" + 
				"                }" + 
				"              }" + 
				"            ]," + 
				"            'money': { 'type': 'float' }" + 
				"          }" + 
				"        }," + 
				"        'seqmarket': [" + 
				"          {" + 
				"            'type': 'nested'," + 
				"            'properties': {" + 
				"              'simmarket': [" + 
				"                {" + 
				"                  'type': 'nested'," + 
				"                  'properties': {" + 
				"                    'marketrules': {" + 
				"                      'type': 'nested'," + 
				"                      'properties': {" + 
				"                        'allocationRule': { 'type': 'text' }," + 
				"                        'paymentRule': { 'type': 'text' }," + 
				"                        'queryRule': { 'type': 'text' }," + 
				"                        'activityRule': { 'type': 'text' }," + 
				"                        'informationRevelationPolicy': { 'type': 'text' }," + 
				"                        'terminationCondition': { 'type': 'text' }" + 
				"                      }" + 
				"                    }" + 
				"                  }" + 
				"                }" + 
				"              ]" + 
				"            }" + 
				"          }" + 
				"        ]" + 
				"      }" + 
				"    ]," + 
				"    'startingDelay': { 'type': 'integer' }" + 
				"  }" + 
				"}";
		JSONParser parser = new JSONParser();
//		try {
//            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader((JsonParse.class.getResource("testjson.json").getFile())));
//            System.out.println(((JSONObject)jsonObject.get("name")).get("type"));
//            
//		} catch (Exception e) {
//			// TODO: handle exception
//            System.out.println(e);
//
//		}
		
		
		JsonData data = new Gson().fromJson(js, JsonData.class);
		System.out.println(data.getName().getType());
		
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
	private List <SimulationProperty> properties;
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
	private List <Tradeables> tradeables;
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
	private Type money;
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
	public Type getMoney() {
		return money;
	}
	public void setMoney(Type money) {
		this.money = money;
	}
	
}

class EndowmentProperty {
	private List<Tradeables> tradeable;

	public List<Tradeables> getTradeable() {
		return tradeable;
	}

	public void setTradeable(List<Tradeables> tradeable) {
		this.tradeable = tradeable;
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
	private List <Simmarket> simmarket;

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