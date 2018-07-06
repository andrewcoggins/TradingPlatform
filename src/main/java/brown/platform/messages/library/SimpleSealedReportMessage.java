package brown.platform.messages.library;

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
}
