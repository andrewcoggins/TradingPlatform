package brown.agent.library;

import brown.agent.AbsLab02Agent;
import brown.channels.library.AuctionChannel;
import brown.exceptions.AgentCreationException;
import brown.logging.Logging;
import brown.messages.library.GameReportMessage;
import brown.messages.library.SimpleSealedReportMessage;
import brown.setup.library.SSSPSetup;

public class Lab02Agent extends AbsLab02Agent {

	public Lab02Agent(String host, int port) throws AgentCreationException {
		super(host, port, new SSSPSetup());
	}

	@Override
	public void onAuction(AuctionChannel channel) {
		// TODO: decide how to bid
		
		// this just bids your valuation
		this.submitBid(channel, this.getValuation());
	}
		
  @Override
  public void onGameReport(GameReportMessage gameReport) {
    Logging.log("Game report received");
    SimpleSealedReportMessage report = (SimpleSealedReportMessage) gameReport;
    report.getWinner(); // get who won last game
    report.getNumPlayers(); //  get the number of players last round
  } 
	
	public static void main(String[] args) throws AgentCreationException {
		new Lab02Agent("localhost", 2121);
    new Lab02Agent("localhost", 2121);		
    new Lab02Agent("localhost", 2121);
    new Lab02Agent("localhost", 2121);  
    new Lab02Agent("localhost", 2121);
    new Lab02Agent("localhost", 2121);     
		while (true) {}
	}
}
