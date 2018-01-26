package brown.agent;

import brown.channels.agent.library.LemonadeChannel;
import brown.exceptions.AgentCreationException;
import brown.messages.library.BankUpdateMessage;
import brown.messages.library.GameReportMessage;
import brown.messages.library.LemonadeReportMessage;
import brown.setup.ISetup;
import brown.setup.Logging;

/**
 * Abstract agent for the lemonade game.
 * All lemonade agents will extend this class.
 * @author andrew
 *
 */
public abstract class AbsLemonadeAgent extends AbsAgent implements ILemonadeAgent{
  protected LemonadeReportMessage latestGameReport;
  
  public AbsLemonadeAgent(String host, int port, ISetup gameSetup)
      throws AgentCreationException {
    super(host, port, gameSetup);
  }
  
  public abstract void onLemonade(LemonadeChannel channel);
  
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