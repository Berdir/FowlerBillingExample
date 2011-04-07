package fowler.energybilling.sites;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import fowler.energybilling.Dollars;
import fowler.energybilling.NoReadingsException;
import fowler.energybilling.Reading;

public class LifeLineTests {

	//Testing the calculations of the charge method - the original code had some problem with nullpointers when just copied. 
	//We resolved this with minor changes in the method's code, but get very different calculated values back.
	//However, we don't care about this as it is irrelevant for refactoring exercises.
	// The lifelinesite charge method distinguishes consumptions below and above
	// 100 and 200(see calculation of base in charge statement)
	//The boundary is above 100 or 200, thus the boundaries are not 99, 100, 101, but just 100, 101
	//an important is that 0 consumption should yield a 0 charge

	// the readings are the same, therefore no energy has been consumed - this should return a 0 charge
	@Test
	public void LifeLineSite0() throws NoReadingsException {
		LifelineSite subject = new LifelineSite();
		subject.addReading(new Reading(10, new Date(1997, 1, 1)));
		subject.addReading(new Reading(10, new Date(1997, 2, 1)));
		assertTrue(subject.charge().getAmount() == 0.0);
	}

	

	@Test
	public void LifeLineSite100() throws NoReadingsException {
		LifelineSite subject = new LifelineSite();
		subject.addReading(new Reading(10, new Date(1997, 1, 1)));
		subject.addReading(new Reading(110, new Date(1997, 1, 2)));
		//System.out.println("100Charge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(1.83).getAmount(), subject.charge().getAmount());
	}

	@Test
	public void LifeLineSite101() throws NoReadingsException {
		LifelineSite subject = new LifelineSite();
		subject.addReading(new Reading(1000, new Date("1 Jan 1997")));
		subject.addReading(new Reading(1101, new Date("1 Feb 1997")));
	//	System.out.println("101Charge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(1.85).getAmount(), subject.charge().getAmount());
	}

	
	@Test
	public void LifeLineSite200() throws NoReadingsException {
		LifelineSite subject = new LifelineSite();
		subject.addReading(new Reading(0, new Date("1 Jan 1997")));
		subject.addReading(new Reading(200, new Date("1 Feb 1997")));
		//System.out.println("200Charge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(3.67).getAmount(), subject.charge().getAmount());
	}

	@Test
	public void LifeLineSite201() throws NoReadingsException {
		LifelineSite subject = new LifelineSite();
		subject.addReading(new Reading(50, new Date("1 Jan 1997")));
		subject.addReading(new Reading(251, new Date("1 Feb 1997")));
		//System.out.println("201Charge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(3.7).getAmount(), subject.charge().getAmount());
	}

	@Test
	public void LifeLineSiteMax() throws NoReadingsException {
		LifelineSite subject = new LifelineSite();
		subject.addReading(new Reading (0, new Date ("1 Jan 1997")));
		subject.addReading(new Reading (Integer.MAX_VALUE, new Date ("1 Feb 1997")));
		//System.out.println("MaxCharge is: "+subject.charge().getAmount());
		assertEquals (new Dollars(2.147483647E7).getAmount(), subject.charge().getAmount());
		}
	
	
	//trying to charge in case of no readings leads to a null pointer exception
	//this can be intended or a bug, the first of the two tests can detect the bug, the second takes the exception as ok
	
	@Ignore  //only use when the null pointer has been fixed as an error
	public void testNoReadings() throws NoReadingsException {
		LifelineSite subject = new LifelineSite();
		assertEquals (new Dollars(0), subject.charge());
		}
	
	@Test
	public void NoReadingsCatchException() {
		LifelineSite subject = new LifelineSite();
		try {
		subject.charge();
		assert(false);
		} catch (NoReadingsException e) {}
		}
}
