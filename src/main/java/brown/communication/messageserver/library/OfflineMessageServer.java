package brown.communication.messageserver.library;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import brown.communication.messages.IAgentToServerMessage;
import brown.communication.messages.IRegistrationMessage;
import brown.communication.messages.IServerToAgentMessage;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.RegistrationResponseMessage;
import brown.communication.messageserver.IOfflineMessageServer;
import brown.logging.library.ErrorLogging;
import brown.logging.library.PlatformLogging;
import brown.platform.managers.ISimulationManager;
import brown.user.agent.IAgentBackend;

public class OfflineMessageServer implements IOfflineMessageServer {

	private ISimulationManager manager;
	private Map<Integer, IAgentBackend> agentConnections;

	//  private Map<String, Integer> messagesReceived; 
	private Set<Integer> messagesAwaiting;

	private static int ID = 0;

	private final int IDMULTIPLIER = 1000000000;

	public OfflineMessageServer(ISimulationManager manager) {
		this.manager = manager;
		this.agentConnections = new ConcurrentHashMap<Integer, IAgentBackend>();
		this.messagesAwaiting = new HashSet<>();
		// final IOfflineMessageServer aServer = this;

		PlatformLogging.log("[-] server started");
	}

	@Override
	public void receiveMessage(IAgentBackend connection, IAgentToServerMessage message) {
		// maintain ordering from OfflineSimulationManager.
		synchronized (this.manager) {
			synchronized (this) {
				if (message.getCorrespondingMessageID() == null) {
					message.offlineServerDispatch(connection, this);
					return;
				}

				if (this.messagesAwaiting.contains(message.getCorrespondingMessageID())) {
					message.offlineServerDispatch(connection, this);
					this.messagesAwaiting.remove(message.getCorrespondingMessageID());
					this.manager.notify();
					return;
				} 
			}
		}
	}

	@Override
	public void onRegistration(IAgentBackend connection,
			IRegistrationMessage registrationMessage) {
		// this will run in the agent thread.
		// once all have been received (?) do a notify.
		PlatformLogging
		.log("[x] new registration");
		if (registrationMessage.getMessageID() == null) {
			PlatformLogging
			.log("[x] Server-onRegistration: encountered null registration");
			return;
		}

		Integer agentPrivateID = -1;
		Collection<Integer> allIds = this.agentConnections.keySet();
		if (!allIds.contains(agentPrivateID)) {
			agentPrivateID = ((int) (Math.random() * IDMULTIPLIER));
			while (allIds.contains(agentPrivateID)) {
				agentPrivateID = ((int) (Math.random() * IDMULTIPLIER));
			}

			this.agentConnections.put(agentPrivateID, (IAgentBackend) connection);

			Integer agentID =
					this.manager.handleRegistration(registrationMessage, agentPrivateID);
			this.sendMessage(agentPrivateID,
					new RegistrationResponseMessage(0, agentID,
							this.manager.getAgentIDs().get(agentID),
							registrationMessage.getName(),
							this.manager.getSimulationJsonFileName()), false);
		} else {
			ErrorLogging
			.log("[x] Server-onRegistration: encountered redundant registration");
		}
	}

	@Override
	public void onBid(IAgentBackend connection, ITradeMessage bidMessage) {
		PlatformLogging.log("[-] bid recieved from " + bidMessage.getAgentID());
		this.manager.giveTradeMessage(bidMessage);
	}

	@Override
	public synchronized void sendMessage(Integer agentPrivateID,
			IServerToAgentMessage message, boolean wait) {
		message.setMessageID(ID++);
		if (wait) {
			this.messagesAwaiting.add(message.getMessageID());
		}
		this.agentConnections.get(agentPrivateID).receiveMessage(message); 
	}

	@Override
	public void stopMessageServer() {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized boolean ready() {
		return this.messagesAwaiting.isEmpty();
	}

}
