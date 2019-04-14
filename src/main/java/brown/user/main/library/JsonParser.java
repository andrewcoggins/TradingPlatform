package brown.user.main.library;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import brown.logging.library.ErrorLogging;
import brown.user.main.IJsonParser;
import brown.user.main.ISimulationConfig;

public class JsonParser implements IJsonParser {

  @SuppressWarnings("unchecked")
  @Override
  public List<ISimulationConfig> parseJSON(String fileName)
      throws FileNotFoundException, IOException, ParseException {
    // TODO Auto-generated method stub
    Object obj = new JSONParser().parse(new FileReader(fileName));

    JSONObject jo = (JSONObject) obj;

    // TODO: get the strings
    String name = (String) jo.get("name");
    String startingDelayTime = (String) jo.get("startingDelayTime");
    String simulationDelayTime = (String) jo.get("simulationDelayTime");

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
    Iterator<Map.Entry> generatorParamsKeyIterator;

    while (simulationIterator.hasNext()) {
      keyIterator = ((Map) simulationIterator.next()).entrySet().iterator();
      while (keyIterator.hasNext()) {
        Map.Entry pair = keyIterator.next();
        if (pair.getKey() == "numRuns") {
          // TODO: capture
          String numRuns = (String) pair.getValue();
        } else if (pair.getKey() == "tradeables") {
          tradeableIterator = ((JSONArray) pair.getValue()).iterator();
          while (tradeableIterator.hasNext()) {
            tradeableKeyIterator =
                ((Map) tradeableIterator.next()).entrySet().iterator();
            while (tradeableKeyIterator.hasNext()) {
              Map.Entry tradeablePair = tradeableKeyIterator.next();
              if (tradeablePair.getKey() == "tradeableName") {
                // TODO: capture
                String tradeableName = (String) tradeablePair.getValue();
              } else if (tradeablePair.getKey() == "tradeableType") {
                // TODO: capture
                String tradeableType = (String) tradeablePair.getValue();
              } else if (tradeablePair.getKey() == "numTradeables") {
                // TODO: capture
                String numTradeables = (String) tradeablePair.getValue();
              } else {
                ErrorLogging.log(
                    "ERROR: JSON Parse: Tradeable: unrecognized input key: "
                        + tradeablePair.getKey());
              }
            }
          }
        } else if (pair.getKey() == "valuation") {
          valuationIterator = ((JSONArray) pair.getValue()).iterator();
          while (valuationIterator.hasNext()) {
            valuationKeyIterator =
                ((Map) valuationIterator.next()).entrySet().iterator();
            while (valuationKeyIterator.hasNext()) {
              Map.Entry valuationPair = valuationKeyIterator.next();
              if (valuationPair.getKey() == "distribution") {
                // TODO: capture
                String distribution = (String) valuationPair.getValue();
              } else if (valuationPair.getKey() == "generator") {
                generatorIterator = ((JSONArray) pair.getValue()).iterator();
                while (generatorIterator.hasNext()) {
                  generatorKeyIterator =
                      ((Map) generatorIterator.next()).entrySet().iterator();
                  while (generatorKeyIterator.hasNext()) {
                    Map.Entry generatorPair = valuationKeyIterator.next();
                    if (generatorPair.getKey() == "name") {
                      // TODO: capture
                      String generatorName = (String) generatorPair.getValue(); 
                    } else if (generatorPair.getKey() == "parameters") {
                      generatorParamsKeyIterator = ((Map) generatorPair.getValue()).entrySet().iterator(); 
                      while (generatorParamsKeyIterator.hasNext()) {
                        Map.Entry generatorParamsPair = generatorParamsKeyIterator.next(); 
                      }          
                    }
                  }
                }
              } else if (valuationPair.getKey() == "tradeables") {
                valuationTradeablesIterator =
                    ((JSONArray) pair.getValue()).iterator();
                while (valuationTradeablesIterator.hasNext()) {
                  valuationTradeablesKeyIterator =
                      ((Map) valuationTradeablesIterator.next()).entrySet()
                          .iterator();
                  while (valuationTradeablesKeyIterator.hasNext()) {
                    Map.Entry valuationTradeablesPair =
                        valuationKeyIterator.next();
                    if (valuationTradeablesPair.getKey() == "tradeableName") {
                      // TODO: capture
                      String valuationTradeableName =
                          (String) valuationTradeablesPair.getValue();
                    } else {
                      ErrorLogging.log(
                          "ERROR: JSON Parse: Valuation Tradeables: unrecognized input key: "
                              + valuationTradeablesPair.getKey());
                    }
                  }
                }
              } else {
                ErrorLogging.log(
                    "ERROR: JSON Parse: Valuation: unrecognized input key: "
                        + valuationPair.getKey());
              }
            }
          }
        } else if (pair.getKey() == "endowments") {
          endowmentIterator = ((JSONArray) pair.getValue()).iterator();
          while (endowmentIterator.hasNext()) {
            endowmentKeyIterator =
                ((Map) endowmentIterator.next()).entrySet().iterator();
            while (endowmentKeyIterator.hasNext()) {
              Map.Entry endowmentPair = endowmentKeyIterator.next();
              if (endowmentPair.getKey() == "tradeable") {
                endowmentTradeablesIterator =
                    ((JSONArray) pair.getValue()).iterator();
                while (endowmentTradeablesIterator.hasNext()) {
                  endowmentTradeablesKeyIterator =
                      ((Map) endowmentTradeablesIterator.next()).entrySet()
                          .iterator();
                  while (endowmentTradeablesKeyIterator.hasNext()) {
                    Map.Entry endowmentTradeablesPair =
                        endowmentKeyIterator.next();
                    if (endowmentTradeablesPair.getKey() == "tradeableName") {
                      // TODO: capture
                      String endowmentTradeableName =
                          (String) endowmentTradeablesPair.getValue();
                    } else if (endowmentTradeablesPair
                        .getKey() == "numTradeables") {
                      // TODO: capture
                      String endowmentNumTradeables =
                          (String) endowmentTradeablesPair.getValue();
                    } else {
                      ErrorLogging.log(
                          "ERROR: JSON Parse: Endowment Tradeables: unrecognized input key: "
                              + endowmentTradeablesPair.getKey());
                    }
                  }
                }
              } else if (endowmentPair.getKey() == "money") {
                // TODO: capture
                String money = (String) endowmentPair.getValue();
              } else if (endowmentPair.getKey() == "frequency") {
                // TODO: capture
                String frequency = (String) endowmentPair.getValue();
              } else {
                ErrorLogging.log(
                    "ERROR: JSON Parse: Endowments: unrecognized input key: "
                        + endowmentPair.getKey());
              }
            }
          }
        } else if (pair.getKey() == "seqmarket") {
          seqMarketIterator = ((JSONArray) pair.getValue()).iterator();
          while (seqMarketIterator.hasNext()) {
            seqMarketKeyIterator =
                ((Map) seqMarketIterator.next()).entrySet().iterator();
            while (seqMarketKeyIterator.hasNext()) {
              Map.Entry seqMarketPair = seqMarketKeyIterator.next();
              if (seqMarketPair.getKey() == "simMarket") {
                simMarketIterator = ((JSONArray) pair.getValue()).iterator();
                while (simMarketIterator.hasNext()) {
                  simMarketKeyIterator =
                      ((Map) simMarketIterator.next()).entrySet().iterator();
                  while (simMarketKeyIterator.hasNext()) {
                    Map.Entry simMarketPair = seqMarketKeyIterator.next();
                    if (simMarketPair.getKey() == "marketRules") {
                      marketRulesKeyIterator = ((Map) simMarketPair.getValue())
                          .entrySet().iterator();
                      while (marketRulesKeyIterator.hasNext()) {
                        Map.Entry marketRulesPair =
                            marketRulesKeyIterator.next();
                        if (marketRulesPair.getKey() == "aRule") {
                          // TODO: capture
                          String aRule = (String) marketRulesPair.getValue();
                        } else if (marketRulesPair.getKey() == "pRule") {
                          // TODO: capture
                          String pRule = (String) marketRulesPair.getValue();
                        } else if (marketRulesPair.getKey() == "qRule") {
                          // TODO: capture
                          String qRule = (String) marketRulesPair.getValue();
                        } else if (marketRulesPair.getKey() == "actRule") {
                          // TODO: capture
                          String actRule = (String) marketRulesPair.getValue();
                        } else if (marketRulesPair.getKey() == "irPolicy") {
                          // TODO: capture
                          String irPolicy = (String) marketRulesPair.getValue();
                        } else if (marketRulesPair
                            .getKey() == "innerIRPolicy") {
                          // TODO: capture
                          String innerIRPolicy =
                              (String) marketRulesPair.getValue();
                        } else if (marketRulesPair.getKey() == "tCondition") {
                          // TODO: capture
                          String tCondition =
                              (String) marketRulesPair.getValue();
                        } else {
                          ErrorLogging.log(
                              "ERROR: JSON Parse: MarketRules: unrecognized input key: "
                                  + marketRulesPair.getKey());
                        }
                      }
                    } else if (simMarketPair.getKey() == "tradeables") {
                      marketTradeablesIterator =
                          ((JSONArray) pair.getValue()).iterator();
                      while (marketTradeablesIterator.hasNext()) {
                        marketTradeablesKeyIterator =
                            ((Map) marketTradeablesIterator.next()).entrySet()
                                .iterator();
                        while (marketTradeablesKeyIterator.hasNext()) {
                          Map.Entry marketTradeablesPair =
                              marketTradeablesKeyIterator.next();
                          if (marketTradeablesPair
                              .getKey() == "tradeableName") {
                            // TODO: capture
                            String marketTradeableName =
                                (String) marketTradeablesPair.getValue();
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
                }
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
    }

    // TODO: reflect the strings into classes.

    return null;
  }

}
