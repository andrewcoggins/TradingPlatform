package brown.auction.value.generator.library;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class UniformValGeneratorTest {
	@Test
	public void testUniformValGenerator() {
		 UniformValGenerator t = new UniformValGenerator(4.0, 0.0);
//		 System.out.println(t.makeValuation());
		 assertThat(t.makeValuation()).isBetween(0.0, 4.0);
	}
	
	 public static void main(String[] args) {
		 
		 UniformValGeneratorTest t = new UniformValGeneratorTest();
		 t.testUniformValGenerator();
		 
	    }
	
}
