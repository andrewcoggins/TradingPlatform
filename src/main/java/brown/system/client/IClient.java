package brown.system.client;

import brown.communication.messages.IRegistrationResponseMessage;
import brown.communication.messages.IStatusMessage;

/**
 * IClient is the kryo client object.
 * It underlies all agent classes.
 * 
 * @author andrewcoggins
 *
 */
public interface IClient {

  /**
   * Receives a RegistrationResponse from MessageServer.
   * 
   * @param registration
   */
  void onRegistrationResponse(IRegistrationResponseMessage registration);

  /**
   * Receives a StatusMessage from MessageServer. 
   * Prints the contents of the message.
   * 
   * @param message
   */
  void onStatusMessage(IStatusMessage message);

}
