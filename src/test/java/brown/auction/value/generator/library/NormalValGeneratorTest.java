package brown.auction.value.generator.library;

import brown.auction.rules.library.ClockAllocationTest;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

public class NormalValGeneratorTest {

		@Test
		public void testNormalValGenerator() {
			NormalValGenerator t = new NormalValGenerator(4.0, 1.0);
			 System.out.println(t.makeValuation());
//			 assertThat(t.makeValuation()).isBetween(0.0, 4.0);
		}
		

	 public static void main(String[] args) {
		 NormalValGeneratorTest t = new NormalValGeneratorTest();
		 t.testNormalValGenerator();
	    }
	
}
