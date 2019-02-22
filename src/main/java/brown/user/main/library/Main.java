package brown.user.main.library;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import brown.logging.library.ErrorLogging;
import brown.logging.library.TestLogging;

/**
 * runs a simple simultaneous first price auction.
 * @author acoggins
 *
 */
public class Main {

  /**
   *
   * @param args
   * 0: numruns : int
   * 1: delayTime: int
   * SimpleTradeable
   * 2: tradeabletype : string
   * 3: numtradeables : int
   * 4: distribution
   * 5: generator
   * SimpleTradeable
   * 6: endowment tradeable type
   * 7: endowment num tradeables
   * 8: endowment money
   * 9: allocationrule
   * 10: paymentrule
   * 11: queryrule
   * 12: activityrule
   * 13: ir policy
   * 14: termination condition
   * @throws InterruptedException 
   * @throws IllegalArgumentException 
   */

  public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
          InstantiationException, IllegalAccessException, InvocationTargetException, IllegalArgumentException, InterruptedException {
     
    Integer numRuns;
    Integer delayTime;
    String tTypeString;
    Integer numTradeables;
    String distributionString;
    String generatorString;
    String endowmenttTypeString;
    Integer endowmentNumTradeables;
    Integer endowmentMoney;
    String aRuleString; 
    String pRuleString; 
    String qRuleString;
    String actRuleString; 
    String irPolicyString; 
    String tConditionString; 

    List<SimulationConfig> configs = new LinkedList<>();
    
    String inputType = args[0]; 
    TestLogging.log(inputType); 
    if (inputType.equals("args")) {
      numRuns = new Integer(args[1]);
      delayTime = new Integer(args[2]);
      tTypeString = args[3];
      numTradeables = new Integer(args[4]);
      distributionString = args[5];
      generatorString = args[6];
      endowmentNumTradeables = new Integer(args[7]);
      endowmentMoney = new Integer(args[8]);
      aRuleString = args[9];
      pRuleString = args[10];
      qRuleString = args[11];
      actRuleString = args[12];
      irPolicyString = args[13];
      tConditionString = args[14];

      CommandLineParser parser = new CommandLineParser(); 
      SimulationConfig runConfig = parser.parseCommandLine(numRuns, delayTime, tTypeString, numTradeables, distributionString, generatorString,
          endowmentNumTradeables, endowmentMoney, aRuleString, pRuleString, qRuleString, actRuleString, irPolicyString, tConditionString); 
      configs.add(runConfig); 
    } else if (inputType == "json") {
      numRuns = new Integer(args[0]);
      delayTime = new Integer(args[1]);
      tTypeString = args[2];
      numTradeables = new Integer(args[3]);
      distributionString = args[4];
      generatorString = args[5];
      endowmenttTypeString = args[6];
      endowmentNumTradeables = new Integer(args[7]);
      endowmentMoney = new Integer(args[8]);
      aRuleString = args[9];
      pRuleString = args[10];
      qRuleString = args[11];
      actRuleString = args[12];
      irPolicyString = args[13];
      tConditionString = args[14];
    } else {
      numRuns = 0;
      delayTime = 0;
      tTypeString = "";
      numTradeables = 0;
      distributionString = "";
      generatorString = "";
      endowmenttTypeString = "";
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
    configRun.run(delayTime, numRuns);
  }
}