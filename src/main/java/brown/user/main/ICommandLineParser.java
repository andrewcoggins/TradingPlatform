package brown.user.main;

import java.lang.reflect.InvocationTargetException;

import brown.user.main.library.SimulationConfig;

public interface ICommandLineParser extends IInputParser {
  
  public SimulationConfig parseCommandLine(int numRuns, int delayTime, String tTypeString, int numTradeables, 
      String distributionString, String generatorString, int endowmentNumTradeables, 
      double endowmentMoney, String aRuleString, String pRuleString, String qRuleString, String actRuleString, 
      String irPolicyString, String tConditionString) throws ClassNotFoundException, NoSuchMethodException, SecurityException, 
  InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
  
  
}