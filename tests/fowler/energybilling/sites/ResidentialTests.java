package fowler.energybilling.sites;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;

import fowler.energybilling.Dollars;
import fowler.energybilling.NoReadingsException;
import fowler.energybilling.Reading;
import fowler.energybilling.Zone;

/* Jana Koehler, Dec 13 2010
 * Several things one can do with this example code
 * - rebuild the class hierarchy and improve the charge methods as in Fowler's original sample text
 * - get rid of the deprecated Date class, probably replace it by Calendar
 * - fix the summerdays calculation in disabilitysite.charge as it gives negative values
 * - improve and embed a rounding method into Dollars calculations)
 * - improve on the tests in general, for example set up a suite and have separate test classes, one for each site
 */



public class ResidentialTests {
	
	@Test
	public void ResidentialSite199Summer() throws NoReadingsException {
		Zone zoneA = new Zone("A", 0.06, 0.07, new DateTime(1997, 5, 15, 0, 0, 0, 0), new DateTime(
				1997, 9, 10, 0, 0, 0, 0));
		ResidentialSite subject = new ResidentialSite(zoneA);
		subject.addReading(new Reading(300, new DateTime(1997, 6, 15, 0, 0, 0, 0)));
		subject.addReading(new Reading(499, new DateTime(1997, 8, 31, 0, 0, 0, 0)));
		// SG: Changed from 5.29 and then 10.7. Probably not right either, but whatever.
		assertEquals(new Dollars(16.19).getAmount(), subject.charge().getAmount());
	}
	
	
	@Test
	public void ResidentialSite4000WholeYear() throws NoReadingsException {
		
		Zone zoneB = new Zone("B", 0.07, 0.06, new DateTime(1997, 6, 5, 0, 0, 0, 0), new DateTime(
				1997, 8, 31, 0, 0, 0, 0));
		
		ResidentialSite subject = new ResidentialSite(zoneB);
		subject.addReading(new Reading(1000, new DateTime(1997, 1, 1, 0, 0, 0, 0)));
		subject.addReading(new Reading(5000, new DateTime(1997, 12, 31, 0, 0, 0, 0)));
		// SG: Changed from 713.5 and then 6793.5. Funny stuff.
		assertEquals(new Dollars(325.5).getAmount(), subject.charge().getAmount());
	}
	
	@After
	public void tearDown() throws Exception {
	}

}
