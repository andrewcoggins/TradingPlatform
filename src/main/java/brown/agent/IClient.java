package brown.agent;

import brown.messages.library.ErrorMessage;
import brown.messages.library.RegistrationMessage;
import brown.messages.library.StringMessage;

/**
 * Agents are responsible for receiving messages from the server,
 * constructing bids, and sending them back.
 * @author andrew
 *
 */
public interface IClient { 

  /**
   * Gets called when the server sends a registration method back to the agent (2nd part of handshake)
   * @param registration - includes the agent's new ID
   */
  public void onRegistration(RegistrationMessage registration);
 
  /**
   * Whenever a message is rejected, a rejection message is sent
   * @param message - includes a string about rejection
   */
  public void onErrorMessage(ErrorMessage message);

  public void onStringMessage(StringMessage message);
}

  
 