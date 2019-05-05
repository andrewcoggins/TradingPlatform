package brown.system.setup.library;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import com.esotericsoftware.kryo.Kryo;

import brown.logging.library.ErrorLogging;
import brown.system.setup.ISetup;

public final class Startup implements ISetup {

  /**
   * registers classes with kryo
   * 
   * @param kryo instance of the kryo object
   * @return
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public static boolean start(Kryo kryo) {
    String PATH = "src/main/java/";
    try {
      List<String> classesToReflect = getJavaFiles(PATH);
      for (String className : classesToReflect) {
        Class<?> tpClass = Class.forName(className);
        kryo.register(tpClass);
    }
    return true;
    } catch (IOException a) {
      ErrorLogging.log("ERROR: java startup: " + a.toString());
    } catch (ClassNotFoundException b) {
      ErrorLogging.log("ERROR: java startup: " + b.toString());
    }
    return false; 
  }

  @Override
  public void setup(Kryo kryo) {
    start(kryo);
  }

  public static List<String> getJavaFiles(String path) throws IOException {
    List<String> output = new LinkedList<String>();
    Files.walk(Paths.get(path)).filter(Files::isRegularFile)
        .forEach(s -> output.add(s.toString().replaceAll(path, "")
            .replaceAll(".java", "").replaceAll("/", ".")));
    return output;
  }

}
