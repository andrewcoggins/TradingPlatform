package brown.communication.messages.library;

import com.esotericsoftware.kryonet.Connection;

import brown.communication.messages.IAgentToServerMessage;
import brown.communication.messageserver.IOfflineMessageServer;
import brown.communication.messageserver.IOnlineMessageServer;
import brown.user.agent.IAgent;

public abstract class AbsAgentToServerMessage extends AbsMessage implements IAgentToServerMessage {
	private Integer responseID;
	
	public AbsAgentToServerMessage(Integer messageID, Integer agentID, Integer responseID) {
		super(messageID, agentID);
		this.responseID = responseID;
	}

	public AbsAgentToServerMessage(Integer messageID, Integer agentID) {
		this(messageID, agentID, null);
	}

	@Override
	public abstract void serverDispatch(Connection connection, IOnlineMessageServer server); 
	
	@Override
	public abstract void offlineServerDispatch(IAgent connection, IOfflineMessageServer server);
	
	@Override
	public Integer getCorrespondingMessageID() {
		return this.responseID;
	}

}
