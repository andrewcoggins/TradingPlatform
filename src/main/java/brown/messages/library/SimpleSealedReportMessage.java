package brown.messages.library;

import java.util.Map;

public class SimpleSealedReportMessage extends GameReportMessage{
  private final Integer winner;
  private Integer numPlayers;
  
  // void kryo
  public SimpleSealedReportMessage(){
    this.winner = null;
    this.numPlayers = null;
  }

  public SimpleSealedReportMessage(Integer winner, Integer numPlayers) {
    this.winner = winner;
    this.numPlayers = numPlayers;
  }
  
  @Override
  public GameReportMessage sanitize(Integer agent,
      Map<Integer, Integer> privateToPublic) {
    if (!agent.equals(this.winner)){
      return new SimpleSealedReportMessage(privateToPublic.get(winner), numPlayers);
    } else {
      return this;
    }
  }
  
  public Integer getWinner() {
    return this.winner;
    }
  
  public Integer getNumPlayers() {
    return this.numPlayers;
  }  
}
