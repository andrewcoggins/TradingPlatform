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
import brown.platform.item.ICart;
import brown.platform.market.IFlexibleRules;
import brown.platform.market.library.FlexibleRules;
import brown.user.main.IEndowmentConfig;
import brown.user.main.IItemConfig;
import brown.user.main.IJsonParser;
import brown.user.main.IMarketConfig;
import brown.user.main.ISimulationConfig;
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
    Iterator itemIterator;
    Iterator valuationIterator;
    Iterator endowmentIterator;
    Iterator seqMarketIterator;
    Iterator simMarketIterator;
    Iterator marketitemsIterator;
    Iterator endowmentitemsIterator;
    Iterator valuationitemsIterator;
    Iterator generatorIterator;

    Iterator<Map.Entry> keyIterator;
    Iterator<Map.Entry> itemKeyIterator;
    Iterator<Map.Entry> valuationKeyIterator;
    Iterator<Map.Entry> endowmentKeyIterator;
    Iterator<Map.Entry> seqMarketKeyIterator;
    Iterator<Map.Entry> simMarketKeyIterator;
    Iterator<Map.Entry> marketRulesKeyIterator;
    Iterator<Map.Entry> marketitemsKeyIterator;
    Iterator<Map.Entry> endowmentitemsKeyIterator;
    Iterator<Map.Entry> valuationitemsKeyIterator;
    Iterator<Map.Entry> generatorKeyIterator;
    
    // within simulation strings
    List<Integer> numRunsList = new LinkedList<Integer>();
    List<List<Map<String, String>>> items =
        new LinkedList<List<Map<String, String>>>();

    List<List<List<Map<String, String>>>> marketRules =
        new LinkedList<List<List<Map<String, String>>>>();
    List<List<List<List<String>>>> marketitems =
        new LinkedList<List<List<List<String>>>>();
    List<List<String>> valuationDistributions = new LinkedList<List<String>>();
    List<List<List<String>>> valuationitems =
        new LinkedList<List<List<String>>>();
    List<List<List<String>>> valuationGeneratorNames =
        new LinkedList<List<List<String>>>();
    List<List<List<List<Double>>>> valuationGeneratorParameters =
        new LinkedList<List<List<List<Double>>>>();
    List<List<String>> endowmentDistributions = new LinkedList<List<String>>();
    List<List<List<String>>> endowmentItems =
        new LinkedList<List<List<String>>>();
    List<List<List<String>>> endowmentGeneratorNames =
        new LinkedList<List<List<String>>>();
    List<List<List<List<Double>>>> endowmentGeneratorParameters =
        new LinkedList<List<List<List<Double>>>>();

    // get the strings from the json
    while (simulationIterator.hasNext()) {
      List<Map<String, String>> simulationitems =
          new LinkedList<Map<String, String>>();

      List<List<Map<String, String>>> simulationMarketRules =
          new LinkedList<List<Map<String, String>>>();
      List<List<List<String>>> simulationMarketitems =
          new LinkedList<List<List<String>>>();
      List<String> simulationValuationDistributions = new LinkedList<String>();
      List<List<String>> simulationValuationitems =
          new LinkedList<List<String>>();
      List<List<String>> simulationValuationGeneratorNames =
          new LinkedList<List<String>>();
      List<List<List<Double>>> simulationValuationGeneratorParameters =
          new LinkedList<List<List<Double>>>();
      List<String> simulationEndowmentDistributions = new LinkedList<String>();
      List<List<String>> simulationEndowmentitems =
          new LinkedList<List<String>>();
      List<List<String>> simulationEndowmentGeneratorNames =
          new LinkedList<List<String>>();
      List<List<List<Double>>> simulationEndowmentGeneratorParameters =
          new LinkedList<List<List<Double>>>();

      keyIterator = ((Map) simulationIterator.next()).entrySet().iterator();
      while (keyIterator.hasNext()) {
        Map.Entry pair = keyIterator.next();
        if (pair.getKey().equals("numRuns")) {
          numRunsList.add(((Long) pair.getValue()).intValue());
        } else if (pair.getKey().equals("items")) {
          itemIterator = ((JSONArray) pair.getValue()).iterator();
          while (itemIterator.hasNext()) {
            Map<String, String> itemConfig = new HashMap<String, String>();
            itemKeyIterator =
                ((Map) itemIterator.next()).entrySet().iterator();
            while (itemKeyIterator.hasNext()) {
              Map.Entry itemPair = itemKeyIterator.next();
              if (itemPair.getKey().equals("itemName")) {
                String itemName = (String) itemPair.getValue();
                itemConfig.put((String) itemPair.getKey(),
                    itemName);
              } else if (itemPair.getKey().equals("numItems")) {
                String numitems = (String) itemPair.getValue();
                itemConfig.put((String) itemPair.getKey(),
                    numitems);
              } else {
                ErrorLogging.log(
                    "ERROR: JSON Parse: item: unrecognized input key: "
                        + itemPair.getKey());
              }
            }
            simulationitems.add(itemConfig);
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
              } else if (valuationPair.getKey().equals("items")) {
                List<String> singleValuationitems =
                    new LinkedList<String>();

                valuationitemsIterator =
                    ((JSONArray) valuationPair.getValue()).iterator();
                while (valuationitemsIterator.hasNext()) {
                  valuationitemsKeyIterator =
                      ((Map) valuationitemsIterator.next()).entrySet()
                          .iterator();
                  while (valuationitemsKeyIterator.hasNext()) {
                    Map.Entry valuationitemsPair =
                        valuationitemsKeyIterator.next();
                    if (valuationitemsPair.getKey().equals( "itemName")) {
                      String valuationitemName =
                          (String) valuationitemsPair.getValue();
                      singleValuationitems.add(valuationitemName);
                    } else {
                      ErrorLogging.log(
                          "ERROR: JSON Parse: Valuation items: unrecognized input key: "
                              + valuationitemsPair.getKey());
                    }
                  }
                }
                simulationValuationitems.add(singleValuationitems);
              } else {
                ErrorLogging.log(
                    "ERROR: JSON Parse: Valuation: unrecognized input key: "
                        + valuationPair.getKey());
              }
            }
          }
        }  else if (pair.getKey().equals("endowment")) {
          endowmentIterator = ((JSONArray) pair.getValue()).iterator();
          while (endowmentIterator.hasNext()) {
            endowmentKeyIterator =
                ((Map) endowmentIterator.next()).entrySet().iterator();
            while (endowmentKeyIterator.hasNext()) {
              Map.Entry endowmentPair = endowmentKeyIterator.next();
              if (endowmentPair.getKey().equals("distribution")) {
                String distribution = (String) endowmentPair.getValue();
                simulationEndowmentDistributions.add(distribution);
              } else if (endowmentPair.getKey().equals("generator")) {
                List<String> singleEndowmentGeneratorNames =
                    new LinkedList<String>();
                List<List<Double>> singleEndowmentGeneratorParameters =
                    new LinkedList<List<Double>>();
                generatorIterator = ((JSONArray) endowmentPair.getValue()).iterator();
                while (generatorIterator.hasNext()) {
                  generatorKeyIterator =
                      ((Map) generatorIterator.next()).entrySet().iterator();
                  while (generatorKeyIterator.hasNext()) {
                    Map.Entry generatorPair = generatorKeyIterator.next();
                    if (generatorPair.getKey().equals("name")) {
                      String generatorName = (String) generatorPair.getValue();
                      singleEndowmentGeneratorNames.add(generatorName);
                    } else if (generatorPair.getKey().equals( "parameters")) {
                      List<Double> generatorParams = (List<Double>)generatorPair.getValue(); 
                      singleEndowmentGeneratorParameters.add(generatorParams);
                    }
                  }
                }
                simulationEndowmentGeneratorNames
                    .add(singleEndowmentGeneratorNames);
                simulationEndowmentGeneratorParameters.add(singleEndowmentGeneratorParameters); 
              } else if (endowmentPair.getKey().equals("items")) {
                List<String> singleEndowmentitems =
                    new LinkedList<String>();

                endowmentitemsIterator =
                    ((JSONArray) endowmentPair.getValue()).iterator();
                while (endowmentitemsIterator.hasNext()) {
                  endowmentitemsKeyIterator =
                      ((Map) endowmentitemsIterator.next()).entrySet()
                          .iterator();
                  while (endowmentitemsKeyIterator.hasNext()) {
                    Map.Entry endowmentitemsPair =
                        endowmentitemsKeyIterator.next();
                    if (endowmentitemsPair.getKey().equals( "itemName")) {
                      String endowmentitemName =
                          (String) endowmentitemsPair.getValue();
                      singleEndowmentitems.add(endowmentitemName);
                    } else {
                      ErrorLogging.log(
                          "ERROR: JSON Parse: Endowment items: unrecognized input key: "
                              + endowmentitemsPair.getKey());
                    }
                  }
                }
                simulationEndowmentitems.add(singleEndowmentitems);
              } else {
                ErrorLogging.log(
                    "ERROR: JSON Parse: Valuation: unrecognized input key: "
                        + endowmentPair.getKey());
              }
            }
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
                List<List<String>> simMarketitems =
                    new LinkedList<List<String>>();

                simMarketIterator = ((JSONArray) seqMarketPair.getValue()).iterator();
                while (simMarketIterator.hasNext()) {
                  Map<String, String> singleMarketRules =
                      new HashMap<String, String>();
                  List<String> singleMarketitems =
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
                    } else if (simMarketPair.getKey().equals("items")) {
                      marketitemsIterator =
                          ((JSONArray) simMarketPair.getValue()).iterator();
                      while (marketitemsIterator.hasNext()) {
                        marketitemsKeyIterator =
                            ((Map) marketitemsIterator.next()).entrySet()
                                .iterator();
                        while (marketitemsKeyIterator.hasNext()) {
                          Map.Entry marketitemsPair =
                              marketitemsKeyIterator.next();
                          if (marketitemsPair
                              .getKey().equals( "itemName")) {
                            String marketitemName =
                                (String) marketitemsPair.getValue();
                            singleMarketitems.add(marketitemName);
                          } else {
                            ErrorLogging.log(
                                "ERROR: JSON Parse: Marketitems: unrecognized input key: "
                                    + marketitemsPair.getKey());
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
                  simMarketitems.add(singleMarketitems);
                }
                simulationMarketRules.add(simMarketRules);
                simulationMarketitems.add(simMarketitems);
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
      items.add(simulationitems);

      marketRules.add(simulationMarketRules);
      marketitems.add(simulationMarketitems);
      valuationDistributions.add(simulationValuationDistributions);
      valuationitems.add(simulationValuationitems);
      valuationGeneratorNames.add(simulationValuationGeneratorNames);
      valuationGeneratorParameters.add(simulationValuationGeneratorParameters);
      endowmentDistributions.add(simulationEndowmentDistributions);
      endowmentItems.add(simulationEndowmentitems);
      endowmentGeneratorNames.add(simulationEndowmentGeneratorNames);
      endowmentGeneratorParameters.add(simulationEndowmentGeneratorParameters);
      
    }
    
    
    System.out.println(items);
    System.out.println(marketRules);
    System.out.println(marketitems);
    System.out.println(valuationDistributions);
    System.out.println(valuationitems);
    System.out.println(valuationGeneratorNames);
    System.out.println(valuationGeneratorParameters);
    System.out.println(endowmentDistributions);
    System.out.println(endowmentItems);
    System.out.println(endowmentGeneratorNames);
    System.out.println(endowmentGeneratorParameters);
    
    
    
    // parse the strings into classes if necessary, and put into configs
    
    // item configs

    List<List<IItemConfig>> tConfigs =
        new LinkedList<List<IItemConfig>>();
    for (List<Map<String, String>> simulationitems : items) {
      List<IItemConfig> simulationTConfigs =
          new LinkedList<IItemConfig>();
      for (Map<String, String> aitem : simulationitems) {
        IItemConfig tConfig =
            new ItemConfig(aitem.get("itemName"),
                Integer.parseInt(aitem.get("numItems")));
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
      List<List<String>> simulationValuationitems =
          valuationitems.get(i);
      List<List<String>> simulationValuationGeneratorNames =
          valuationGeneratorNames.get(i);
      List<List<List<Double>>> simulationValuationGeneratorParams =
          valuationGeneratorParameters.get(i);
      for (int j = 0; j < simulationValuationDistributions.size(); j++) {
        String valuationDistributionString =
            simulationValuationDistributions.get(j);
        Class<?> distributionClass =
            Class.forName("brown.auction.value.distribution.library."
                + valuationDistributionString);
        Constructor<?> distributionCons =
            distributionClass.getConstructor(ICart.class, List.class);
        
        
        List<String> singleValuationitems =
            simulationValuationitems.get(j);

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
            singleValuationitems, distributionCons, generators);
        simulationValConfigs.add(valConfig);
      }
      valuationConfigs.add(simulationValConfigs);
    }

    // endowment configs

    List<List<IEndowmentConfig>> endowmentConfigs =
        new LinkedList<List<IEndowmentConfig>>();
    for (int i = 0; i < endowmentDistributions.size(); i++) {
      List<IEndowmentConfig> simulationEndowmentConfigs =
          new LinkedList<IEndowmentConfig>();
      List<String> simulationEndowmentDistributions =
          endowmentDistributions.get(i);
      List<List<String>> simulationEndowmentItems =
          endowmentItems.get(i);
      List<List<String>> simulationEndowmentGeneratorNames =
          endowmentGeneratorNames.get(i);
      List<List<List<Double>>> simulationEndowmentGeneratorParams =
          endowmentGeneratorParameters.get(i);
      for (int j = 0; j < simulationEndowmentDistributions.size(); j++) {
        String endowmentDistributionstring =
            simulationEndowmentDistributions.get(j);
        Class<?> distributionClass =
            Class.forName("brown.auction.endowment.distribution.library."
                + endowmentDistributionstring);
        Constructor<?> distributionCons =
            distributionClass.getConstructor(ICart.class, List.class);
        
        
        List<String> singleEndowmentItems =
            simulationEndowmentItems.get(j);

        List<String> singleEndowmentGeneratorNames =
            simulationEndowmentGeneratorNames.get(j);

        List<List<Double>> singleEndowmentGeneratorParams =
            simulationEndowmentGeneratorParams.get(j);

        Map<Constructor<?>, List<Double>> endowmentGenerators =
            new HashMap<Constructor<?>, List<Double>>();

        for (int k = 0; k < singleEndowmentGeneratorNames.size(); k++) {
          String generatorName = singleEndowmentGeneratorNames.get(k);
          Class<?> generatorClass = Class.forName(
              "brown.auction.value.generator.library." + generatorName);
          Constructor<?> generatorCons =
              generatorClass.getConstructor(List.class);

          List<Double> generatorParams = new LinkedList<Double>();
          List<Double> generatorStringParams =
              singleEndowmentGeneratorParams.get(k);
          for (Double param : generatorStringParams) {
            generatorParams.add(param);
          }
          // TODO: need to make this not a map so we can have dup generators
          endowmentGenerators.put(generatorCons, generatorParams);
        }
        IEndowmentConfig endowConfig = new EndowmentConfig(
            singleEndowmentItems, distributionCons, endowmentGenerators);
        simulationEndowmentConfigs.add(endowConfig);
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
      List<List<List<String>>> simulationMarketitems =
          marketitems.get(i);
      for (int j = 0; j < simulationMarketRules.size(); j++) {
        List<IMarketConfig> simMarketConfigs = new LinkedList<IMarketConfig>();
        List<Map<String, String>> simultaneousMarketRules =
            simulationMarketRules.get(j);
        List<List<String>> simultaneousMarketitems =
            simulationMarketitems.get(j);
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

          List<String> singleMarketItems =
              simultaneousMarketitems.get(k);

          IMarketConfig singleMarketConfig =
              new MarketConfig(marketRule, singleMarketItems);
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
    outerParams.put("numTotalRuns", ((Long) jo.get("numTotalRuns")).intValue()); 
    outerParams.put("startingDelayTime", ((Long) jo.get("startingDelayTime")).intValue()); 
    outerParams.put("simulationDelayTime", ((Long) jo.get("simulationDelayTime")).intValue()); 
    
    return outerParams; 
    
  }
  
}
