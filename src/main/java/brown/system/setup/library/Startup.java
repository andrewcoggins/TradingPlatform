package brown.system.setup.library;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;

import brown.logging.library.ErrorLogging;
import brown.system.setup.ISetup;

public final class Startup implements ISetup {

  /**
   * register all classes with kryo
   * 
   * @param kryo the Kryo object.
   * @return
   */
  public static boolean start(Kryo kryo) {
    String PATH = "src/main/java/";
    try {
      kryo.register(HashMap.class);
      kryo.register(LinkedList.class);
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

  /**
   * helper that returns every java class starting at an input directory.
   * 
   * @param path the starting path for the search
   * @return every java class starting at a directory.
   * @throws IOException
   */
  public static List<String> getJavaFiles(String path) throws IOException {
    List<String> output = new LinkedList<String>();
    Files.walk(Paths.get(path)).filter(Files::isRegularFile)
        .forEach(s -> output.add(s.toString().replaceAll(path, "")
            .replaceAll(".java", "").replaceAll("/", ".")));
    return output;
  }

}
