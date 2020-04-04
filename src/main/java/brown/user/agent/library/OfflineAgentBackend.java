package brown.user.agent.library;

import java.util.concurrent.LinkedBlockingQueue;

import org.spectrumauctions.sats.core.model.gsvm.GSVMBidder;
import org.spectrumauctions.sats.core.model.gsvm.GSVMBidderSetup;
import org.spectrumauctions.sats.core.model.lsvm.LocalSynergyValueModel;

import brown.communication.messages.IAgentToServerMessage;
import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.IInformationMessage;
import brown.communication.messages.IServerToAgentMessage;
import brown.communication.messages.ISimulationReportMessage;
import brown.communication.messages.IStatusMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.IValuationMessage;
import brown.communication.messages.library.AckMessage;
import brown.communication.messages.library.RegistrationMessage;
import brown.communication.messageserver.IOfflineMessageServer;
import brown.logging.library.SystemLogging;
import brown.user.agent.IAgent;
import brown.user.agent.IAgentBackend;

public class OfflineAgentBackend extends AbsAgentBackend implements IAgentBackend {
  
  protected IOfflineMessageServer messageServer;

  private final LinkedBlockingQueue<IServerToAgentMessage> tasks;
  
  
  public OfflineAgentBackend(IOfflineMessageServer messageServer, IAgent agent) {
    super(agent);
    this.messageServer = messageServer;
    this.tasks = new LinkedBlockingQueue<>();

    // "listener"
    new Thread(new Runnable() {

      @Override
      public void run() {
        while (true) {
          IServerToAgentMessage message;
          try {
            message = OfflineAgentBackend.this.tasks.take();
            message.agentDispatch(OfflineAgentBackend.this);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

        }
      }
    }).start();

    this.sendMessage(new RegistrationMessage(-1, agent.getAgentName()));
  }
  
  @Override
  public void onBankUpdate(IBankUpdateMessage bankUpdate) {
    this.agent.onBankUpdate(bankUpdate);
    this.sendMessage(new AckMessage(0, this.ID, bankUpdate.getMessageID()));
  }

  @Override
  public void onInformationMessage(IInformationMessage informationMessage) {
    this.agent.onInformationMessage(informationMessage);
    this.sendMessage(new AckMessage(0, this.ID, informationMessage.getMessageID()));
  }

  @Override
  public void onTradeRequestMessage(ITradeRequestMessage tradeRequestMessage) {
    this.agent.onTradeRequestMessage(tradeRequestMessage);
    this.sendMessage(new AckMessage(0, this.ID, tradeRequestMessage.getMessageID()));
  }

  @Override
  public void onValuationMessage(IValuationMessage valuationMessage) {
    this.agent.onValuationMessage(valuationMessage);
    this.sendMessage(new AckMessage(0, this.ID, valuationMessage.getMessageID()));
  }

  @Override
  public void
      onSimulationReportMessage(ISimulationReportMessage reportMessage) {
    this.agent.onSimulationReportMessage(reportMessage);
    this.sendMessage(new AckMessage(0, this.ID, reportMessage.getMessageID()));
  }

  
  @Override
  public void onStatusMessage(IStatusMessage message) {
    SystemLogging
        .log("[x] rej: " + message.getStatus() + ", agent ID: " + this.ID);
    this.sendMessage(new AckMessage(0, this.ID, message.getMessageID()));
  }
  
  @Override
  public synchronized void sendMessage(IAgentToServerMessage message) {
    this.messageServer.receiveMessage(this, message);
  }

  public synchronized void receiveMessage(IServerToAgentMessage message) {
    try {
      this.tasks.put(message);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  

}
