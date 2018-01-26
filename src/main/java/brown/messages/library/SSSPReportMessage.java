package brown.messages.library;

import java.util.Map;

public class SSSPReportMessage extends GameReportMessage{

  @Override
  public GameReportMessage sanitize(Integer agent,
      Map<Integer, Integer> privateToPublic) {
    return this;
  }

}
