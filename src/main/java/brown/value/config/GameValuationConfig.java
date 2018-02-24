package brown.value.config;

import java.util.List;
import java.util.Map;

import brown.messages.library.PrivateInformationMessage;

public interface GameValuationConfig {

  public void initialize();
  
  public Map<Integer,PrivateInformationMessage> generateReport(List<Integer> collection);
}
