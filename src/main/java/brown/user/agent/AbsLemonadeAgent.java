package brown.user.agent;

import brown.logging.Logging;
import brown.platform.messages.BankUpdateMessage;
import brown.platform.messages.GameReportMessage;
import brown.platform.messages.LemonadeReportMessage;
import brown.system.setup.ISetup;

/**
 * Abstract agent for the lemonade game.
 * All lemonade agents will extend this class.
 * @author andrew
 *
 */
public abstract class AbsLemonadeAgent extends AbsAgent implements ILemonadeAgent {
  
  protected LemonadeReportMessage latestGameReport;
  
  public AbsLemonadeAgent(String host, int port, ISetup gameSetup) {
    super(host, port, gameSetup);
  }
  
  public AbsLemonadeAgent(String host, int port, ISetup gameSetup, String name) {
    super(host, port, gameSetup,name);
  }
  
  @Override 
  public void onBankUpdate(BankUpdateMessage bankUpdate){
    super.onBankUpdate(bankUpdate);
    Logging.log("[Bank Update] Gained " + bankUpdate.moniesChanged + " monies, total: " + monies);     
  }
  
  @Override
  public void onGameReport(GameReportMessage gameReport){
    if (gameReport instanceof LemonadeReportMessage) { 
      this.latestGameReport = (LemonadeReportMessage) gameReport;
    }    
  }  
}