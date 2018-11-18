package brown.system.client;

import brown.platform.messages.library.ErrorMessage;
import brown.platform.messages.library.RegistrationMessage;
import brown.platform.messages.library.StringMessage;

/**
 * Agents are responsible for receiving messages from the server,
 * constructing bids, and sending them back to the server.
 * @author andrew
 *
 */
public interface IClient { 

  /**
   * Gets called when the server sends a registration method back to the agent (2nd part of handshake)
   * @param registration - includes the agent's new ID
   */
  void onRegistration(RegistrationMessage registration);
 
  /**
   * Whenever a message is rejected, a rejection message is sent
   * @param message - includes a string about rejection
   */
  void onErrorMessage(ErrorMessage message);

  void onStringMessage(StringMessage message);
}

  
 