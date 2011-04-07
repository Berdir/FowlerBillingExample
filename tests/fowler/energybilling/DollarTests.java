package fowler.energybilling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DollarTests {

	@Test
	public void testDollars() {
		Dollars d1 = new Dollars(5.0);	
		Dollars cap = new Dollars(200.0);
		assertTrue("Dollars plus",(new Dollars(0.0).plus(d1)).getAmount() == 5.0);
		assertTrue("Dollars minus", (new Dollars(0.0).minus(d1)).getAmount() == -5.0);
		assertTrue("Dollars times", (new Dollars(0.0).times(1.0)).getAmount() == 0.0);
		assertTrue("Dollars min", (new Dollars(0.0).min(d1)).getAmount() == 0.0);
		assertTrue("Dollars max", (new Dollars(0.0).max(d1)).getAmount() == 5.0);
		Dollars x = d1.plus(d1.times(2.0)).min(cap);
		assertTrue("Dollars nested", x.getAmount()==20.0);
		Dollars y = new Dollars(d1);
		assertEquals(y.getAmount(),d1.getAmount());
		
	}
}
