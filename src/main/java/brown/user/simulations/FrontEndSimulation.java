package brown.user.simulations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;

import brown.auction.preset.AbsMarketPreset;
import brown.auction.value.config.library.ValConfig;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.platform.server.library.RunServer;
import brown.platform.server.library.SimulMarkets;
import brown.platform.server.library.Simulation;
import brown.system.setup.library.SSSPSetup;
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
      Thread.sleep(7000);
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
      Set<ITradeable> allTradeables = new HashSet<ITradeable>(); 
      List<ITradeable> allTradeablesList = new LinkedList<ITradeable>();
      for (int i = 0; i < numTradeables; i++) {
        allTradeables.add(new SimpleTradeable(i));
        allTradeablesList.add(new SimpleTradeable(i));
      }
      List<AbsMarketPreset> oneMarket = new LinkedList<AbsMarketPreset>();
      try {
        System.out.println("RULES: " + this.rules);
        Thread.sleep(100);
        
        Class<?> rulesClass = Class.forName("brown.market.preset.library." + this.rules);
        Constructor<?> rulesConstructor = rulesClass.getConstructor(Integer.TYPE); 
        oneMarket.add((AbsMarketPreset) rulesConstructor.newInstance(1)); 
        
        Class<?> configClass = Class.forName("brown.value.config." + this.config);
        Constructor<?> configConstructor = configClass.getConstructor(Set.class);
        
        SimulMarkets markets = new SimulMarkets(oneMarket);
        List<SimulMarkets> seq = new LinkedList<SimulMarkets>();  
        seq.add(markets);
        Simulation testSim = new Simulation(seq, (ValConfig) configConstructor.newInstance(allTradeables),
            allTradeablesList,1.,new LinkedList<ITradeable>());    
        RunServer testServer = new RunServer(2121, new SSSPSetup());
        testServer.runSimulation(testSim, numSims, this.DELAYTIME, lag, "loggedResults.txt");
      } catch (Exception e1) {
        e1.printStackTrace();
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
        Class<?> agentClass = Class.forName("brown.agent.library." + this.agentType); 
        Constructor<?> configConstructor = agentClass.getConstructor(String.class, Integer.TYPE);     
        configConstructor.newInstance("localhost", 2121);
      } catch (Exception e) {
        e.printStackTrace();
      }
      while (true){} 
    }
  }
  
  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration(new Version(2,3,23));
    File templates = new File("src/core/resources/spark/template/freemarker");
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