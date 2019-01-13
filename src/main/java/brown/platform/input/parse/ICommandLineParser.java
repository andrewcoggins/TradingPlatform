package brown.platform.input.parse;

import java.lang.reflect.InvocationTargetException;

import brown.platform.input.config.library.SimulationConfig;

public interface ICommandLineParser extends IInputParser {
  
  public SimulationConfig parseCommandLine(int numRuns, int delayTime, String tTypeString, int numTradeables, 
      String distributionString, String generatorString, String endowmentTTypeString, int endowmentNumTradeables, 
      int endowmentMoney, String aRuleString, String pRuleString, String qRuleString, String actRuleString, 
      String irPolicyString, String tConditionString) throws ClassNotFoundException, NoSuchMethodException, SecurityException, 
  InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
  
  
}