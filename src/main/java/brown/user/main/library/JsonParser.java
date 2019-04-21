package brown.user.main.library;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import brown.auction.rules.IActivityRule;
import brown.auction.rules.IAllocationRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IInnerIRPolicy;
import brown.auction.rules.IPaymentRule;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;
import brown.logging.library.ErrorLogging;
import brown.platform.market.IFlexibleRules;
import brown.platform.market.library.FlexibleRules;
import brown.user.main.IEndowmentConfig;
import brown.user.main.IJsonParser;
import brown.user.main.IMarketConfig;
import brown.user.main.ISimulationConfig;
import brown.user.main.ITradeableConfig;
import brown.user.main.IValuationConfig;

public class JsonParser implements IJsonParser {

  @SuppressWarnings("unchecked")
  @Override
  public List<ISimulationConfig> parseJSON(String fileName)
      throws FileNotFoundException, IOException, ParseException,
      ClassNotFoundException, NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {
    Object rawInput = new JSONParser().parse(new FileReader(fileName));

    JSONObject jo = (JSONObject) rawInput;
    JSONArray ja = (JSONArray) jo.get("simulation");

    Iterator simulationIterator = ja.iterator();
    Iterator tradeableIterator;
    Iterator valuationIterator;
    Iterator endowmentIterator;
    Iterator seqMarketIterator;
    Iterator simMarketIterator;
    Iterator marketTradeablesIterator;
    Iterator endowmentTradeablesIterator;
    Iterator valuationTradeablesIterator;
    Iterator generatorIterator;

    Iterator<Map.Entry> keyIterator;
    Iterator<Map.Entry> tradeableKeyIterator;
    Iterator<Map.Entry> valuationKeyIterator;
    Iterator<Map.Entry> endowmentKeyIterator;
    Iterator<Map.Entry> seqMarketKeyIterator;
    Iterator<Map.Entry> simMarketKeyIterator;
    Iterator<Map.Entry> marketRulesKeyIterator;
    Iterator<Map.Entry> marketTradeablesKeyIterator;
    Iterator<Map.Entry> endowmentTradeablesKeyIterator;
    Iterator<Map.Entry> valuationTradeablesKeyIterator;
    Iterator<Map.Entry> generatorKeyIterator;
    
    // within simulation strings
    List<Integer> numRunsList = new LinkedList<Integer>();
    List<List<Map<String, String>>> tradeables =
        new LinkedList<List<Map<String, String>>>();
    List<List<List<Map<String, String>>>> endowmentTradeables =
        new LinkedList<List<List<Map<String, String>>>>();
    List<List<Map<String, Double>>> endowmentParams =
        new LinkedList<List<Map<String, Double>>>();
    List<List<List<Map<String, String>>>> marketRules =
        new LinkedList<List<List<Map<String, String>>>>();
    List<List<List<List<String>>>> marketTradeables =
        new LinkedList<List<List<List<String>>>>();
    List<List<String>> valuationDistributions = new LinkedList<List<String>>();
    List<List<List<String>>> valuationTradeables =
        new LinkedList<List<List<String>>>();
    List<List<List<String>>> valuationGeneratorNames =
        new LinkedList<List<List<String>>>();
    List<List<List<List<Double>>>> valuationGeneratorParameters =
        new LinkedList<List<List<List<Double>>>>();

    // get the strings from the json
    while (simulationIterator.hasNext()) {
      List<Map<String, String>> simulationTradeables =
          new LinkedList<Map<String, String>>();
      List<List<Map<String, String>>> simulationEndowmentTradeables =
          new LinkedList<List<Map<String, String>>>();
      List<Map<String, Double>> simulationEndowmentParams =
          new LinkedList<Map<String, Double>>();
      List<List<Map<String, String>>> simulationMarketRules =
          new LinkedList<List<Map<String, String>>>();
      List<List<List<String>>> simulationMarketTradeables =
          new LinkedList<List<List<String>>>();
      List<String> simulationValuationDistributions = new LinkedList<String>();
      List<List<String>> simulationValuationTradeables =
          new LinkedList<List<String>>();
      List<List<String>> simulationValuationGeneratorNames =
          new LinkedList<List<String>>();
      List<List<List<Double>>> simulationValuationGeneratorParameters =
          new LinkedList<List<List<Double>>>();

      keyIterator = ((Map) simulationIterator.next()).entrySet().iterator();
      while (keyIterator.hasNext()) {
        Map.Entry pair = keyIterator.next();
        if (pair.getKey().equals("numRuns")) {
          numRunsList.add(((Long) pair.getValue()).intValue());
        } else if (pair.getKey().equals("tradeables")) {
          tradeableIterator = ((JSONArray) pair.getValue()).iterator();
          while (tradeableIterator.hasNext()) {
            Map<String, String> tradeableConfig = new HashMap<String, String>();
            tradeableKeyIterator =
                ((Map) tradeableIterator.next()).entrySet().iterator();
            while (tradeableKeyIterator.hasNext()) {
              Map.Entry tradeablePair = tradeableKeyIterator.next();
              if (tradeablePair.getKey().equals("tradeableName")) {
                String tradeableName = (String) tradeablePair.getValue();
                tradeableConfig.put((String) tradeablePair.getKey(),
                    tradeableName);
              } else if (tradeablePair.getKey().equals("numTradeables")) {
                String numTradeables = (String) tradeablePair.getValue();
                tradeableConfig.put((String) tradeablePair.getKey(),
                    numTradeables);
              } else {
                ErrorLogging.log(
                    "ERROR: JSON Parse: Tradeable: unrecognized input key: "
                        + tradeablePair.getKey());
              }
            }
            simulationTradeables.add(tradeableConfig);
          }
        } else if (pair.getKey().equals("valuation")) {
          valuationIterator = ((JSONArray) pair.getValue()).iterator();
          while (valuationIterator.hasNext()) {
            valuationKeyIterator =
                ((Map) valuationIterator.next()).entrySet().iterator();
            while (valuationKeyIterator.hasNext()) {
              Map.Entry valuationPair = valuationKeyIterator.next();
              if (valuationPair.getKey().equals("distribution")) {
                String distribution = (String) valuationPair.getValue();
                simulationValuationDistributions.add(distribution);
              } else if (valuationPair.getKey().equals("generator")) {
                List<String> singleValuationGeneratorNames =
                    new LinkedList<String>();
                List<List<Double>> singleValuationGeneratorParameters =
                    new LinkedList<List<Double>>();
                generatorIterator = ((JSONArray) valuationPair.getValue()).iterator();
                while (generatorIterator.hasNext()) {
                  generatorKeyIterator =
                      ((Map) generatorIterator.next()).entrySet().iterator();
                  while (generatorKeyIterator.hasNext()) {
                    Map.Entry generatorPair = generatorKeyIterator.next();
                    if (generatorPair.getKey().equals("name")) {
                      String generatorName = (String) generatorPair.getValue();
                      singleValuationGeneratorNames.add(generatorName);
                    } else if (generatorPair.getKey().equals( "parameters")) {
                      List<Double> generatorParams = (List<Double>)generatorPair.getValue(); 
                      singleValuationGeneratorParameters.add(generatorParams);
                    }
                  }
                }
                simulationValuationGeneratorNames
                    .add(singleValuationGeneratorNames);
                simulationValuationGeneratorParameters.add(singleValuationGeneratorParameters); 
              } else if (valuationPair.getKey().equals("tradeables")) {
                List<String> singleValuationTradeables =
                    new LinkedList<String>();

                valuationTradeablesIterator =
                    ((JSONArray) valuationPair.getValue()).iterator();
                while (valuationTradeablesIterator.hasNext()) {
                  valuationTradeablesKeyIterator =
                      ((Map) valuationTradeablesIterator.next()).entrySet()
                          .iterator();
                  while (valuationTradeablesKeyIterator.hasNext()) {
                    Map.Entry valuationTradeablesPair =
                        valuationTradeablesKeyIterator.next();
                    if (valuationTradeablesPair.getKey().equals( "tradeableName")) {
                      String valuationTradeableName =
                          (String) valuationTradeablesPair.getValue();
                      singleValuationTradeables.add(valuationTradeableName);
                    } else {
                      ErrorLogging.log(
                          "ERROR: JSON Parse: Valuation Tradeables: unrecognized input key: "
                              + valuationTradeablesPair.getKey());
                    }
                  }
                }
                simulationValuationTradeables.add(singleValuationTradeables);
              } else {
                ErrorLogging.log(
                    "ERROR: JSON Parse: Valuation: unrecognized input key: "
                        + valuationPair.getKey());
              }
            }
          }
        } else if (pair.getKey().equals("endowments")) {
          endowmentIterator = ((JSONArray) pair.getValue()).iterator();
          while (endowmentIterator.hasNext()) {
            List<Map<String, String>> singleEndowmentTradeables =
                new LinkedList<Map<String, String>>();
            Map<String, Double> singleEndowmentParams =
                new HashMap<String, Double>();

            endowmentKeyIterator =
                ((Map) endowmentIterator.next()).entrySet().iterator();
            while (endowmentKeyIterator.hasNext()) {
              Map.Entry endowmentPair = endowmentKeyIterator.next();
              if (endowmentPair.getKey().equals("tradeable")) {
                endowmentTradeablesIterator =
                    ((JSONArray) endowmentPair.getValue()).iterator();
                while (endowmentTradeablesIterator.hasNext()) {
                  Map<String, String> individualEndowmentTradeable =
                      new HashMap<String, String>();

                  endowmentTradeablesKeyIterator =
                      ((Map) endowmentTradeablesIterator.next()).entrySet()
                          .iterator();
                  while (endowmentTradeablesKeyIterator.hasNext()) {
                    Map.Entry endowmentTradeablesPair =
                        endowmentTradeablesKeyIterator.next();
                    if (endowmentTradeablesPair.getKey().equals( "tradeableName")) {
                      String endowmentTradeableName =
                          (String) endowmentTradeablesPair.getValue();
                      individualEndowmentTradeable.put(
                          (String) endowmentTradeablesPair.getKey(),
                          endowmentTradeableName);
                    } else if (endowmentTradeablesPair
                        .getKey().equals("numTradeables")) {
                      String endowmentNumTradeables =
                          (String) endowmentTradeablesPair.getValue();
                      individualEndowmentTradeable.put(
                          (String) endowmentTradeablesPair.getKey(),
                          endowmentNumTradeables);
                    } else {
                      ErrorLogging.log(
                          "ERROR: JSON Parse: Endowment Tradeables: unrecognized input key: "
                              + endowmentTradeablesPair.getKey());
                    }
                  }
                  singleEndowmentTradeables.add(individualEndowmentTradeable);
                }
              } else if (endowmentPair.getKey().equals("money")) {
                Double money = (Double) endowmentPair.getValue();
                singleEndowmentParams.put((String) endowmentPair.getKey(),
                    money);
              } else if (endowmentPair.getKey().equals("frequency")) {
                Double frequency = (Double) endowmentPair.getValue();
                singleEndowmentParams.put((String) endowmentPair.getKey(),
                    frequency);
              } else {
                ErrorLogging.log(
                    "ERROR: JSON Parse: Endowments: unrecognized input key: "
                        + endowmentPair.getKey());
              }
            }
            simulationEndowmentTradeables.add(singleEndowmentTradeables);
            simulationEndowmentParams.add(singleEndowmentParams);
          }
        } else if (pair.getKey().equals("seqMarket")) {
          seqMarketIterator = ((JSONArray) pair.getValue()).iterator();
          while (seqMarketIterator.hasNext()) {
            seqMarketKeyIterator =
                ((Map) seqMarketIterator.next()).entrySet().iterator();
            while (seqMarketKeyIterator.hasNext()) {
              Map.Entry seqMarketPair = seqMarketKeyIterator.next(); 
              if (seqMarketPair.getKey().equals( "simMarket")) {
                List<Map<String, String>> simMarketRules =
                    new LinkedList<Map<String, String>>();
                List<List<String>> simMarketTradeables =
                    new LinkedList<List<String>>();

                simMarketIterator = ((JSONArray) seqMarketPair.getValue()).iterator();
                while (simMarketIterator.hasNext()) {
                  Map<String, String> singleMarketRules =
                      new HashMap<String, String>();
                  List<String> singleMarketTradeables =
                      new LinkedList<String>();

                  simMarketKeyIterator =
                      ((Map) simMarketIterator.next()).entrySet().iterator();
                  while (simMarketKeyIterator.hasNext()) {
                    Map.Entry simMarketPair = simMarketKeyIterator.next();
                    if (simMarketPair.getKey().equals("marketRules")) {
                      marketRulesKeyIterator = ((Map) simMarketPair.getValue())
                          .entrySet().iterator();
                      while (marketRulesKeyIterator.hasNext()) {
                        Map.Entry marketRulesPair =
                            marketRulesKeyIterator.next();
                        if (marketRulesPair.getKey().equals("aRule")) {
                          String aRule = (String) marketRulesPair.getValue();
                          singleMarketRules
                              .put((String) marketRulesPair.getKey(), aRule);
                        } else if (marketRulesPair.getKey().equals("pRule")) {
                          String pRule = (String) marketRulesPair.getValue();
                          singleMarketRules
                              .put((String) marketRulesPair.getKey(), pRule);
                        } else if (marketRulesPair.getKey().equals("qRule")) {
                          String qRule = (String) marketRulesPair.getValue();
                          singleMarketRules
                              .put((String) marketRulesPair.getKey(), qRule);
                        } else if (marketRulesPair.getKey().equals("actRule")) {
                          String actRule = (String) marketRulesPair.getValue();
                          singleMarketRules
                              .put((String) marketRulesPair.getKey(), actRule);
                        } else if (marketRulesPair.getKey().equals("irPolicy")) {
                          String irPolicy = (String) marketRulesPair.getValue();
                          singleMarketRules
                              .put((String) marketRulesPair.getKey(), irPolicy);
                        } else if (marketRulesPair
                            .getKey().equals( "innerIRPolicy")) {
                          String innerIRPolicy =
                              (String) marketRulesPair.getValue();
                          singleMarketRules.put(
                              (String) marketRulesPair.getKey(), innerIRPolicy);
                        } else if (marketRulesPair.getKey().equals("tCondition")) {
                          String tCondition =
                              (String) marketRulesPair.getValue();
                          singleMarketRules.put(
                              (String) marketRulesPair.getKey(), tCondition);
                        } else {
                          ErrorLogging.log(
                              "ERROR: JSON Parse: MarketRules: unrecognized input key: "
                                  + marketRulesPair.getKey());
                        }
                      }
                    } else if (simMarketPair.getKey().equals("tradeables")) {
                      marketTradeablesIterator =
                          ((JSONArray) simMarketPair.getValue()).iterator();
                      while (marketTradeablesIterator.hasNext()) {
                        marketTradeablesKeyIterator =
                            ((Map) marketTradeablesIterator.next()).entrySet()
                                .iterator();
                        while (marketTradeablesKeyIterator.hasNext()) {
                          Map.Entry marketTradeablesPair =
                              marketTradeablesKeyIterator.next();
                          if (marketTradeablesPair
                              .getKey().equals( "tradeableName")) {
                            String marketTradeableName =
                                (String) marketTradeablesPair.getValue();
                            singleMarketTradeables.add(marketTradeableName);
                          } else {
                            ErrorLogging.log(
                                "ERROR: JSON Parse: MarketTradeables: unrecognized input key: "
                                    + marketTradeablesPair.getKey());
                          }
                        }
                      }
                    } else {
                      ErrorLogging.log(
                          "ERROR: JSON Parse: simMarket: unrecognized input key: "
                              + simMarketPair.getKey());
                    }
                  }
                  simMarketRules.add(singleMarketRules);
                  simMarketTradeables.add(singleMarketTradeables);
                }
                simulationMarketRules.add(simMarketRules);
                simulationMarketTradeables.add(simMarketTradeables);
              } else {
                ErrorLogging.log(
                    "ERROR: JSON Parse: SeqMarket: unrecognized input key: "
                        + seqMarketPair.getKey());
              }
            }
          }
        } else {
          ErrorLogging
              .log("ERROR: JSON Parse: Simulation: unrecognized input key: "
                  + pair.getKey());
        }
      }
      tradeables.add(simulationTradeables);
      endowmentTradeables.add(simulationEndowmentTradeables);
      endowmentParams.add(simulationEndowmentParams);
      marketRules.add(simulationMarketRules);
      marketTradeables.add(simulationMarketTradeables);
      valuationDistributions.add(simulationValuationDistributions);
      valuationTradeables.add(simulationValuationTradeables);
      valuationGeneratorNames.add(simulationValuationGeneratorNames);
      valuationGeneratorParameters.add(simulationValuationGeneratorParameters);
      
    }
    
    
    System.out.println(tradeables);
    System.out.println(endowmentTradeables);
    System.out.println(endowmentParams);
    System.out.println(marketRules);
    System.out.println(marketTradeables);
    System.out.println(valuationDistributions);
    System.out.println(valuationTradeables);
    System.out.println(valuationGeneratorNames);
    System.out.println(valuationGeneratorParameters);
    
    
    
    // parse the strings into classes if necessary, and put into configs
    
    // tradeable configs

    List<List<ITradeableConfig>> tConfigs =
        new LinkedList<List<ITradeableConfig>>();
    for (List<Map<String, String>> simulationTradeables : tradeables) {
      List<ITradeableConfig> simulationTConfigs =
          new LinkedList<ITradeableConfig>();
      for (Map<String, String> aTradeable : simulationTradeables) {
        ITradeableConfig tConfig =
            new TradeableConfig(aTradeable.get("tradeableName"),
                Integer.parseInt(aTradeable.get("numTradeables")));
        simulationTConfigs.add(tConfig);
      }
      tConfigs.add(simulationTConfigs);
    }

    // valuation configs

    List<List<IValuationConfig>> valuationConfigs =
        new LinkedList<List<IValuationConfig>>();
    for (int i = 0; i < valuationDistributions.size(); i++) {
      List<IValuationConfig> simulationValConfigs =
          new LinkedList<IValuationConfig>();
      List<String> simulationValuationDistributions =
          valuationDistributions.get(i);
      List<List<String>> simulationValuationTradeables =
          valuationTradeables.get(i);
      List<List<String>> simulationValuationGeneratorNames =
          valuationGeneratorNames.get(i);
      List<List<List<Double>>> simulationValuationGeneratorParams =
          valuationGeneratorParameters.get(i);
      for (int j = 0; j < simulationValuationDistributions.size(); j++) {
        String valuationDistributionString =
            simulationValuationDistributions.get(i);
        Class<?> distributionClass =
            Class.forName("brown.auction.value.distribution.library."
                + valuationDistributionString);
        Constructor<?> distributionCons =
            distributionClass.getConstructor(Map.class, List.class);
        
        
        List<String> singleValuationTradeables =
            simulationValuationTradeables.get(j);

        List<String> singleValuationGeneratorNames =
            simulationValuationGeneratorNames.get(j);

        List<List<Double>> singleValuationGeneratorParams =
            simulationValuationGeneratorParams.get(j);

        Map<Constructor<?>, List<Double>> generators =
            new HashMap<Constructor<?>, List<Double>>();

        for (int k = 0; k < singleValuationGeneratorNames.size(); k++) {
          String generatorName = singleValuationGeneratorNames.get(k);
          Class<?> generatorClass = Class.forName(
              "brown.auction.value.generator.library." + generatorName);
          Constructor<?> generatorCons =
              generatorClass.getConstructor(List.class);

          List<Double> generatorParams = new LinkedList<Double>();
          List<Double> generatorStringParams =
              singleValuationGeneratorParams.get(k);
          for (Double param : generatorStringParams) {
            generatorParams.add(param);
          }
          // TODO: need to make this not a map so we can have dup generators
          generators.put(generatorCons, generatorParams);
        }
        IValuationConfig valConfig = new ValuationConfig(
            singleValuationTradeables, distributionCons, generators);
        simulationValConfigs.add(valConfig);
      }
      valuationConfigs.add(simulationValConfigs);
    }

    // endowment configs

    List<List<IEndowmentConfig>> endowmentConfigs =
        new LinkedList<List<IEndowmentConfig>>();
    for (int i = 0; i < endowmentTradeables.size(); i++) {
      List<IEndowmentConfig> simulationEndowmentConfigs =
          new LinkedList<IEndowmentConfig>();
      List<List<Map<String, String>>> simulationEndowmentTradeables =
          endowmentTradeables.get(i);
      List<Map<String, Double>> simulationEndowmentParams =
          endowmentParams.get(i);
      for (int j = 0; j < simulationEndowmentTradeables.size(); j++) {
        List<Map<String, String>> singleEndowmentTradeables =
            simulationEndowmentTradeables.get(j);
        Map<String, Double> singleEndowmentParams =
            simulationEndowmentParams.get(j);
        Double money = singleEndowmentParams.get("money");
        Integer frequency =
            ((Long) Math.round(singleEndowmentParams.get("frequency"))).intValue();
        Map<String, Integer> endowmentMapping = new HashMap<String, Integer>();
        for (int k = 0; k < singleEndowmentTradeables.size(); k++) {
          Map<String, String> singleTradeable =
              singleEndowmentTradeables.get(k);
          String tName = singleTradeable.get("tradeableName");
          Integer numTradeables =
              Integer.parseInt(singleTradeable.get("numTradeables"));
          endowmentMapping.put(tName, numTradeables);
        }
        // TODO: endowment names??
        simulationEndowmentConfigs.add(
            new EndowmentConfig("default", endowmentMapping, money, frequency));
      }
      endowmentConfigs.add(simulationEndowmentConfigs);
    }

    // market configs

    List<List<List<IMarketConfig>>> marketConfigs =
        new LinkedList<List<List<IMarketConfig>>>();
    for (int i = 0; i < marketRules.size(); i++) {
      List<List<IMarketConfig>> simulationMarketConfigs =
          new LinkedList<List<IMarketConfig>>();
      List<List<Map<String, String>>> simulationMarketRules =
          marketRules.get(i);
      List<List<List<String>>> simulationMarketTradeables =
          marketTradeables.get(i);
      for (int j = 0; j < simulationMarketRules.size(); j++) {
        List<IMarketConfig> simMarketConfigs = new LinkedList<IMarketConfig>();
        List<Map<String, String>> simultaneousMarketRules =
            simulationMarketRules.get(j);
        List<List<String>> simultaneousMarketTradeables =
            simulationMarketTradeables.get(j);
        for (int k = 0; k < simultaneousMarketRules.size(); k++) {
          Map<String, String> singleMarketRules =
              simultaneousMarketRules.get(k);
          String aRuleString = singleMarketRules.get("aRule");
          String pRuleString = singleMarketRules.get("pRule");
          String qRuleString = singleMarketRules.get("qRule");
          String actRuleString = singleMarketRules.get("actRule");
          String irPolicyString = singleMarketRules.get("irPolicy");
          String innerIRPolicyString = singleMarketRules.get("innerIRPolicy");
          String tConditionString = singleMarketRules.get("tCondition");

          Class<?> aRuleClass = Class.forName(
              "brown.auction.rules.allocation.onesided." + aRuleString);
          Class<?> pRuleClass = Class
              .forName("brown.auction.rules.payment.onesided." + pRuleString);
          Class<?> qRuleClass = Class
              .forName("brown.auction.rules.query.onesided." + qRuleString);
          Class<?> actRuleClass = Class.forName(
              "brown.auction.rules.activity.onesided." + actRuleString);
          Class<?> irPolicyClass = Class
              .forName("brown.auction.rules.ir.onesided." + irPolicyString);
          Class<?> innerIRPolicyClass = Class
              .forName("brown.auction.rules.innerir.onesided." + innerIRPolicyString);
          Class<?> tConditionClass = Class.forName(
              "brown.auction.rules.termination.onesided." + tConditionString);

          Constructor<?> aRuleCons = aRuleClass.getConstructor();
          Constructor<?> pRuleCons = pRuleClass.getConstructor();
          Constructor<?> qRuleCons = qRuleClass.getConstructor();
          Constructor<?> actRuleCons = actRuleClass.getConstructor();
          Constructor<?> irPolicyCons = irPolicyClass.getConstructor();
          Constructor<?> innerIRPolicyCons =
              innerIRPolicyClass.getConstructor();
          Constructor<?> tConditionCons = tConditionClass.getConstructor();

          IFlexibleRules marketRule =
              new FlexibleRules((IAllocationRule) aRuleCons.newInstance(),
                  (IPaymentRule) pRuleCons.newInstance(),
                  (IQueryRule) qRuleCons.newInstance(),
                  (IActivityRule) actRuleCons.newInstance(),
                  (IInformationRevelationPolicy) irPolicyCons.newInstance(),
                  (IInnerIRPolicy) innerIRPolicyCons.newInstance(),
                  (ITerminationCondition) tConditionCons.newInstance());

          List<String> singleMarketTradeables =
              simultaneousMarketTradeables.get(k);

          IMarketConfig singleMarketConfig =
              new MarketConfig(marketRule, singleMarketTradeables);
          simMarketConfigs.add(singleMarketConfig);
        }
        simulationMarketConfigs.add(simMarketConfigs);
      }
      marketConfigs.add(simulationMarketConfigs);
    }

    // put it all together
    List<ISimulationConfig> simulationConfigs =
        new LinkedList<ISimulationConfig>();

    for (int i = 0; i < tConfigs.size(); i++) {
      ISimulationConfig singleConfig =
          new SimulationConfig(numRunsList.get(i),
              tConfigs.get(i), valuationConfigs.get(i), endowmentConfigs.get(i),
              marketConfigs.get(i));
      simulationConfigs.add(singleConfig); 
    }

    return simulationConfigs;
  }

  @Override
  public Map<String, Integer> parseJSONOuterParameters(String fileName)
      throws FileNotFoundException, IOException, ParseException {
    Object rawInput = new JSONParser().parse(new FileReader(fileName));

    JSONObject jo = (JSONObject) rawInput;

    
    Map<String, Integer> outerParams = new HashMap<String, Integer>(); 
    outerParams.put("numTotalRuns", Integer.parseInt((String) jo.get("numTotalRuns"))); 
    outerParams.put("startingDelayTime", Integer.parseInt((String) jo.get("startingDelayTime"))); 
    outerParams.put("simulationDelayTime", Integer.parseInt((String) jo.get("simulationDelayTime"))); 
    
    return outerParams; 
    
  }
  
}
