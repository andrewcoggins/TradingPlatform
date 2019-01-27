package brown.mechanism.messages.library;

import java.util.Map;

/**
 * Game report for a simple sealed auction.
 * @author kerry
 *
 */
public class SimpleSealedReportMessage extends GameReportMessage {
  private final Integer winner;
  private Integer numPlayers;
  
  /**
   * Void kryo
   */
  public SimpleSealedReportMessage() {
    this.winner = null;
    this.numPlayers = null;
  }

  /**
   * Simple Sealed report message gives the ID of the winner player, and the
   * number of players in the game.
   * @param winner
   * @param numPlayers
   */
  public SimpleSealedReportMessage(Integer winner, Integer numPlayers) {
    this.winner = winner;
    this.numPlayers = numPlayers;
  }
  
  @Override
  public GameReportMessage sanitize(Integer agent,
      Map<Integer, Integer> privateToPublic) {
    if (!agent.equals(this.winner)) {
      return new SimpleSealedReportMessage(privateToPublic.get(winner), numPlayers);
    } else {
      return this;
    }
  }
  
  /**
   * Gets the public ID of the winner agent.
   * @return winner.
   */
  public Integer getWinner() {
    return this.winner;
    }
  
  /**
   * Gets the number of players in the game. 
   * @return number of players. 
   */
  public Integer getNumPlayers() {
    return this.numPlayers;
  }

  @Override
  public String toString() {
    return "SimpleSealedReportMessage [winner=" + winner + ", numPlayers="
        + numPlayers + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((numPlayers == null) ? 0 : numPlayers.hashCode());
    result = prime * result + ((winner == null) ? 0 : winner.hashCode());
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
    SimpleSealedReportMessage other = (SimpleSealedReportMessage) obj;
    if (numPlayers == null) {
      if (other.numPlayers != null)
        return false;
    } else if (!numPlayers.equals(other.numPlayers))
      return false;
    if (winner == null) {
      if (other.winner != null)
        return false;
    } else if (!winner.equals(other.winner))
      return false;
    return true;
  }  
  
}
