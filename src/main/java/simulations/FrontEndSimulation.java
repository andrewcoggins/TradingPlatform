package simulations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;

import brown.agent.library.Lab02Agent;
import brown.exceptions.AgentCreationException;
import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.SSSPRules;
import brown.server.library.RunServer;
import brown.server.library.SimulMarkets;
import brown.server.library.Simulation;
import brown.setup.library.SSSPSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.config.AdditiveUniformConfig;
import freemarker.template.Configuration;
import freemarker.template.Version;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;

/**
 * Runs a simulation from a web browser.
 * @author andrew
 */
public class FrontEndSimulation {
  
  
  //want a server that will appear in a web page. 
  public static void main(String[] args) { 
    runSparkServer(); 
  }
  
  private static void runSparkServer() {
    String localhost = "127.0.0.1";
    Spark.ipAddress(localhost);
    Spark.port(4567);
    Spark.exception(Exception.class, new ExceptionPrinter());
    FreeMarkerEngine freeMarker = createEngine();
    Spark.get("/tradingplatform", new SubmitHandler(), freeMarker);
    Spark.get("/results", new ResultsHandler(), freeMarker);
  }
  
  private static class SubmitHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) throws Exception {
      QueryParamsMap inputFields = req.queryMap();
      // what else do we want? 
      //capture the strings, input them into the server, start a game with them. 
      Map<String, Object> vars = ImmutableMap.of("title", "Trading Platform");
      return new ModelAndView(vars, "tradingplatform.ftl");
    }
  }
  
  private static class ResultsHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) throws Exception {
      QueryParamsMap inputFields = req.queryMap();
      // not worrying right now if they are well formed or not.
      String rules = inputFields.value("rules");
      Integer sims = Integer.valueOf(inputFields.value("numSims"));
      Integer numTradeables = Integer.valueOf(inputFields.value("numTradeables"));
      Integer lag = Integer.valueOf(inputFields.value("lag"));
      String config = inputFields.value("valConfig");
      String agentType = inputFields.value("agent");
      String result = runThreads(rules, sims, numTradeables, lag, config, agentType);
      //capture the input fields, make sure they are well-formed, and then run threads. s
      Map<String, Object> vars = ImmutableMap.of("title", "Trading Platform Results", "result", result);
      return new ModelAndView(vars, "results.ftl");
    }
  }
  
  private static String runThreads(String rules, int numSims, int numTradeables, int lag, String config, String agentType) {
    ServerRunnable server = new ServerRunnable(rules, numSims, numTradeables, lag, config); 
    AgentRunnable agent = new AgentRunnable(agentType); 
    Thread serverThread = new Thread(server);
    Thread agentThread = new Thread(agent);
    //TODO seperate agents.
    Thread agentThreadTwo = new Thread(agent);
    serverThread.start(); 
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    agentThread.start(); 
    agentThreadTwo.start(); 
    //TODO: do this better
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    // any interrupts?
    BufferedReader reader;
    try {
      reader = new BufferedReader(new FileReader("loggedResults.txt"));
      String read = "";
      String newRead = reader.readLine(); 
      //System.out.println("READ" + newRead);
      while(newRead != null) { 
        read += newRead + "\n"; 
        newRead = reader.readLine();
      }
      reader.close();
      return read;
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }  
    return ""; 
  }
  
  
  private static class ServerRunnable implements Runnable {
    
    private String rules;
    private int numSims; 
    private int numTradeables; 
    private int lag; 
    private String config;
    private int DELAYTIME = 3;
    
    public ServerRunnable(String rules, int numSims, int numTradeables, int lag, String config) { 
      this.rules = rules; 
      this.numSims = numSims; 
      this.numTradeables = numTradeables; 
      this.lag = lag; 
      this.config = config; 
    }
    
    
    @Override
    public void run() {
      //not specifying a server in particular; specifying the FIELDS. 
      Set<ITradeable> allTradeables = new HashSet<ITradeable>(); 
      List<ITradeable> allTradeablesList = new LinkedList<ITradeable>();
      // add tradeables.
      for (int i = 0; i < numTradeables; i++) {
        allTradeables.add(new SimpleTradeable(i));
        allTradeablesList.add(new SimpleTradeable(i));
      }
      // one market in this game.
      List<AbsMarketPreset> oneMarket = new LinkedList<AbsMarketPreset>();
      //modify to vary with input rules
      oneMarket.add(new SSSPRules(1));
      SimulMarkets markets = new SimulMarkets(oneMarket);
      List<SimulMarkets> seq = new LinkedList<SimulMarkets>();  
      seq.add(markets);
      // modify to vary with input
      Simulation testSim = new Simulation(seq,new AdditiveUniformConfig(allTradeables),
          allTradeablesList,1.,new LinkedList<ITradeable>());    
      // initialize the server.
      // modify to vary with input
      RunServer testServer = new RunServer(2121, new SSSPSetup());
      // run
      try {
        testServer.runSimulation(testSim, numSims, this.DELAYTIME, lag, "loggedResults.txt");
      } catch (InterruptedException e) {
        System.out.println("ERROR: Interrupted Exception");
        e.printStackTrace();
      }
    }
  }
  
  private static class AgentRunnable implements Runnable {

    private String agentType; 
    
    public AgentRunnable(String agentType) {
      this.agentType = agentType;
    }
    @Override
    public void run() {
      try {
        //enumerate these
        new Lab02Agent("localhost", 2121);
      } catch (AgentCreationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      while (true){} 
    }
  }
  
  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration(new Version(2,3,23));
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.\n",
                        templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private static final int INTERNAL_SERVER_ERROR = 500;
  /** A handler to print an Exception as text into the Response.
   */
  private static class ExceptionPrinter implements ExceptionHandler<Exception> {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(INTERNAL_SERVER_ERROR);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }
}