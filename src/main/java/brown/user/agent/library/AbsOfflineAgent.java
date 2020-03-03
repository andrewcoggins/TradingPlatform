package brown.user.agent.library;

import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IRegistrationResponseMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.IStatusMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.communication.messages.library.RegistrationMessage;
import brown.communication.messageserver.IOfflineMessageServer;
import brown.user.agent.IAgent;

public abstract class AbsOfflineAgent implements IAgent {

  private IOfflineMessageServer messageServer; 
  
  public AbsOfflineAgent(IOfflineMessageServer messageServer) {
    this.messageServer = messageServer; 
    messageServer.onRegistration(this, new RegistrationMessage(-1));
  }
  
  public void onBankUpdate(IBankUpdateMessage bankUpdate) {
    
  }

  public void onInformationMessage(IInformationMessage informationMessage) {
    
  }
  
  public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage) {
    
  }
  
  public void onValuationMessage(IValuationMessage valuationMessage) {
    
  }

  public void onSimulationReportMessage(ISimulationReportMessage reportMessage) {
    
  }
  
  public void
      onRegistrationResponse(IRegistrationResponseMessage registrationMessage) {
    
  }

  public void onStatusMessage(IStatusMessage message) {
    
  }

}
