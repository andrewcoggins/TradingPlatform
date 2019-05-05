package brown.system.setup.library;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import brown.user.main.library.ConfigRun;
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
  public static boolean start(Kryo kryo)
      throws IOException, ClassNotFoundException {
    String PATH = "src/main/java";
    List<String> classesToReflect = getJavaFiles(PATH);
    for (String className : classesToReflect) {
      try {
        Class<?> tpClass = Class.forName(className);
        kryo.register(tpClass);
      } catch (ClassNotFoundException c) {
        ErrorLogging.log("ERROR: java startup: " + c.toString());
      }
    }
    return true;
  }

  @Override
  public void setup(Kryo kryo) throws IOException, ClassNotFoundException {
    start(kryo);
  }

  public static List<String> getJavaFiles(String path) throws IOException {
    List<String> output = new LinkedList<String>();
    Files.walk(Paths.get(path)).filter(Files::isRegularFile)
        .forEach(s -> output.add(s.toString().replaceAll("src/main/java/", "")
            .replaceAll(".java", "").replaceAll("/", ".")));
    return output;
  }

}
