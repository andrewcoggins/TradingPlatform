package brown.server.library;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import freemarker.template.Configuration;
import freemarker.template.Version;
import joptsimple.OptionParser;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;

public class WebServer {
  
  
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
  }
  
  private static class SubmitHandler implements TemplateViewRoute {

    @Override
    public ModelAndView handle(Request req, Response res) throws Exception {
      QueryParamsMap inputFields = req.queryMap();
      //capture the strings, input them into the server, start a game with them. 
      
      return null;
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