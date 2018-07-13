package brown.auction.rules.library; 

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.prevstate.library.PriceDiscoveryInfo;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.platform.messages.library.GameReportMessage;
import brown.platform.messages.library.SMRAReportMessage;

/**
 * IR policy for SMRA auctions. Generates game report messages with the reserve prices for 
 * all goods. Generates a summary state giving all of the reserve prices to the next auction.
 * @author acoggins
 *
 */
public class SMRAPolicy implements IInformationRevelationPolicy {

  @Override
  public void setReport(IMarketState state) {
    // game reports for everyone.
    // give everyone the reserve prices for the goods.
    Map<Integer, List<GameReportMessage>> reports = new HashMap<Integer, List<GameReportMessage>>(); 
    for (List<Integer> group : state.getGroups()) {
      for (Integer agent : group) {
        List<GameReportMessage> currList = reports.getOrDefault(agent, new LinkedList<GameReportMessage>());
        currList.add(new SMRAReportMessage(state.getReserve()));
        reports.put(agent, currList); 
      }
    }
    state.setReport(reports); 
  }

  @Override
  public void constructSummaryState(IMarketState state) {
    // construct the summary state. 
    PriceDiscoveryInfo reserves = new PriceDiscoveryInfo(state.getReserve()); 
    state.setSummaryState(reserves);   
  }

}