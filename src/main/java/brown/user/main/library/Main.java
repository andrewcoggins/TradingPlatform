package brown.user.main.library;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;

import brown.logging.library.ErrorLogging;
import brown.logging.library.TestLogging;
import brown.user.main.IJsonParser;
import brown.user.main.ISimulationConfig;

/**
 * runs a simple simultaneous first price auction.
 * 
 * @author acoggins
 *
 */
public class Main {

  /**
   *
   * @param args 0: numruns : int 1: delayTime: int SimpleTradeable 2:
   *          tradeabletype : string 3: numtradeables : int 4: distribution 5:
   *          generator SimpleTradeable 6: endowment tradeable type 7: endowment
   *          num tradeables 8: endowment money 9: allocationrule 10:
   *          paymentrule 11: queryrule 12: activityrule 13: ir policy 14:
   *          termination condition
   * @throws InterruptedException
   * @throws IllegalArgumentException
   */

  public static void main(String[] args)
      throws ClassNotFoundException, NoSuchMethodException,
      InstantiationException, IllegalAccessException, InvocationTargetException,
      IllegalArgumentException, InterruptedException, FileNotFoundException, IOException, ParseException {

    Integer numTotalRuns;
    Integer startingDelayTime;
    Integer simulationDelayTime;
    String tTypeString;
    Integer numTradeables;
    String distributionString;
    String generatorString;
    Integer endowmentNumTradeables;
    Integer endowmentMoney;
    String aRuleString;
    String pRuleString;
    String qRuleString;
    String actRuleString;
    String irPolicyString;
    String innerIRPolicyString;
    String tConditionString;

    List<ISimulationConfig> configs = new LinkedList<>();

    String inputType = args[0];
    TestLogging.log(inputType);
    if (inputType.equals("args")) {
      numTotalRuns = new Integer(args[1]);
      startingDelayTime = new Integer(args[2]);
      simulationDelayTime = new Integer(args[3]);
      tTypeString = args[4];
      numTradeables = new Integer(args[5]);
      distributionString = args[6];
      generatorString = args[7];
      endowmentNumTradeables = new Integer(args[8]);
      endowmentMoney = new Integer(args[9]);
      aRuleString = args[10];
      pRuleString = args[12];
      qRuleString = args[13];
      actRuleString = args[14];
      irPolicyString = args[14];
      innerIRPolicyString = args[15];
      tConditionString = args[16];

      CommandLineParser parser = new CommandLineParser();
      SimulationConfig runConfig = parser.parseCommandLine(numTotalRuns,
          startingDelayTime, simulationDelayTime, tTypeString, numTradeables,
          distributionString, generatorString, endowmentNumTradeables,
          endowmentMoney, aRuleString, pRuleString, qRuleString, actRuleString,
          irPolicyString, innerIRPolicyString, tConditionString);
      configs.add(runConfig);
    } else if (inputType == "json") {
       String fileName = args[1]; 
       IJsonParser jsonParser = new JsonParser(); 
       List<ISimulationConfig> runConfig = jsonParser.parseJSON(fileName); 
       configs.addAll(runConfig); 
       Map<String, Integer> outerParams = jsonParser.parseJSONOuterParameters(fileName); 
       startingDelayTime = outerParams.get("startingDelayTime");
       simulationDelayTime = outerParams.get("simulationDelayTime");
       numTotalRuns = outerParams.get("numTotalRuns"); 
    } else {
      numTotalRuns = 0;
      startingDelayTime = 0;
      simulationDelayTime = 0;
      tTypeString = "";
      numTradeables = 0;
      distributionString = "";
      generatorString = "";
      endowmentNumTradeables = 0;
      endowmentMoney = 0;
      aRuleString = "";
      pRuleString = "";
      qRuleString = "";
      actRuleString = "";
      irPolicyString = "";
      tConditionString = "";
      ErrorLogging.log("ERROR: no input specification given.");
      System.exit(1);
    }

    ConfigRun configRun = new ConfigRun(configs);
    configRun.run(startingDelayTime, simulationDelayTime, numTotalRuns);
  }
}
