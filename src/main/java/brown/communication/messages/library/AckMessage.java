package brown.communication.messages.library;

import com.esotericsoftware.kryonet.Connection;

import brown.communication.messages.IAgentToServerMessage;
import brown.communication.messageserver.IOfflineMessageServer;
import brown.communication.messageserver.IOnlineMessageServer;
import brown.user.agent.IAgent;
import brown.user.agent.IAgentBackend;

/**
 * Trade message is sent by the agent to the server specifying an action withing
 * a trading environment This may be a bid or some other action.
 * 
 * @author andrew
 *
 */
public class AckMessage extends AbsAgentToServerMessage implements IAgentToServerMessage {

	public AckMessage() {
		super(null, null);
	}

	public AckMessage(Integer messageID, Integer agentID, Integer responseID) {
		super(messageID, agentID, responseID);
	}

	public Integer getAgentID() {
		return this.agentID;
	}

	@Override
	public void serverDispatch(Connection connection, IOnlineMessageServer server) {
		// noop
	}

	@Override
	public String toString() {
		return "AckMessage [agentID=" + agentID + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agentID == null) ? 0 : agentID.hashCode());
		result = prime * result + ((messageID == null) ? 0 : messageID.hashCode());
		result = prime * result + ((getCorrespondingMessageID() == null) ? 0 : getCorrespondingMessageID().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AckMessage other = (AckMessage) obj;
		if (agentID == null) {
			if (other.agentID != null)
				return false;
		} else if (!agentID.equals(other.agentID))
			return false;
		if (messageID == null) {
			if (other.messageID != null)
				return false;
		} else if (!messageID.equals(other.messageID))
			return false;
		if (getCorrespondingMessageID() == null) {
			if (other.getCorrespondingMessageID() != null)
				return false;
		} else if (!getCorrespondingMessageID().equals(other.getCorrespondingMessageID()))
			return false;
		return true;
	}

	@Override
	public void offlineServerDispatch(IAgentBackend connection, IOfflineMessageServer server) {
		// noop
	}

}
