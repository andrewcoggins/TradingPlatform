package brown.user.agent.library;

import brown.communication.messages.library.BankUpdateMessage;
import brown.communication.messages.library.GameReportMessage;
import brown.communication.messages.library.LemonadeReportMessage;
import brown.logging.library.Logging;
import brown.system.setup.ISetup;
import brown.user.agent.ILemonadeAgent;

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