package fowler.energybilling;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.*;

import fowler.energybilling.Dollars;
import fowler.energybilling.Reading;
import fowler.energybilling.Registry;
import fowler.energybilling.Zone;
import fowler.energybilling.sites.BusinessSite;
import fowler.energybilling.sites.DisabilitySite;
import fowler.energybilling.sites.LifelineSite;
import fowler.energybilling.sites.ResidentialSite;
import fowler.energybilling.sites.TimedSite;

/* Jana Koehler, Dec 13 2010
 * Several things one can do with this example code
 * - rebuild the class hierarchy and improve the charge methods as in Fowler's original sample text
 * - get rid of the deprecated Date class, probably replace it by Calendar
 * - fix the summerdays calculation in disabilitysite.charge as it gives negative values
 * - improve and embed a rounding method into Dollars calculations)
 * - improve on the tests in general, for example set up a suite and have separate test classes, one for each site
 */



public class EngergyBillingTests {

	@Before
	public void setUp() throws Exception {

		Zone zoneA = new Zone("A", 0.06, 0.07, new Date(1997, 5, 15), new Date(
				1997, 9, 10));
		Zone zoneB = new Zone("B", 0.07, 0.06, new Date(1997, 6, 5), new Date(
				1997, 8, 31));
		Zone zoneC = new Zone("C", 0.065, 0.065, new Date(1997, 6, 5),
				new Date(1997, 8, 31));

		Registry.add(zoneA);
		Registry.add(zoneB);
		Registry.add(zoneC);
		// Registry.add("Unit", new Unit ("USD")); from Fowler's Original text

	}

	@Test
	public void testRegistry() {
		assertFalse("Registry is empty", Registry.isEmpty());
		assertTrue("Registry contains 3 zones", Registry.size() == 3);

	}

	
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
	
	
	@Test
	public void DisabilitySite0() throws NoReadingsException {
		TimedSite subject = new DisabilitySite(Registry.get("A"));
		subject.addReading(new Reading(10, new Date(1997, 1, 1)));
		subject.addReading(new Reading(10, new Date(1997, 2, 1)));
		assertTrue(subject.charge().getAmount() == 0.0);
	}
	
	//typical boundaries for the disability site: used more than 200, which is removed by the cap
	//otherwise, some readings within the summer and winter period dates could be added to see if this makes a difference
	//the original code by Fowler only charges 0 in all cases
	
	@Test
	public void DisabilitySite199Winter() throws NoReadingsException {
		TimedSite subject = new DisabilitySite(Registry.get("A"));
		subject.addReading(new Reading(100, new Date(1997, 1, 1)));
		subject.addReading(new Reading(299, new Date(1997, 2, 1)));
		//System.out.println("199WinterCharge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(4.97).getAmount(), subject.charge().getAmount());
	}
	
	@Test
	public void DisabilitySite199Summer() throws NoReadingsException {
		TimedSite subject = new DisabilitySite(Registry.get("B"));
		subject.addReading(new Reading(300, new Date(1997, 6, 5)));
		subject.addReading(new Reading(499, new Date(1997, 31, 8)));
		//System.out.println("199SummerCharge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(5.29).getAmount(), subject.charge().getAmount());
	}
	
	@Test
	//the summerdays calculation yield -134 for this case - this clearly an error, but we ignore it for now
	public void DisabilitySite199WholeYear() throws NoReadingsException {
		TimedSite subject = new DisabilitySite(Registry.get("A"));
		subject.addReading(new Reading(20000, new Date(1997, 1, 1)));
		subject.addReading(new Reading(20199, new Date(1997, 12, 31)));
		//System.out.println("199WholeYearCharge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(-21.69).getAmount(), subject.charge().getAmount());
	}
	
	@Test
	public void DisabilitySite4000WholeYear() throws NoReadingsException {
		TimedSite subject = new DisabilitySite(Registry.get("B"));
		subject.addReading(new Reading(1000, new Date(1997, 1, 1)));
		subject.addReading(new Reading(5000, new Date(1997, 12, 31)));
		//System.out.println("4000WholeYearCharge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(125.66).getAmount(), subject.charge().getAmount());
	}
	
	
	
	@Test
	public void BusinessSite0() throws NoReadingsException {
		BusinessSite subject = new BusinessSite();
		subject.addReading(new Reading(10, new Date(1997, 1, 1)));
		subject.addReading(new Reading(10, new Date(1997, 2, 1)));
		assertTrue(subject.charge().getAmount() == 0.0);
	}
	
	
	@Test
	public void BusinessSite4000WholeYear() throws NoReadingsException {
		BusinessSite subject = new BusinessSite();
		subject.addReading(new Reading(1000, new Date(1997, 1, 1)));
		subject.addReading(new Reading(5000, new Date(1997, 12, 31)));
		//System.out.println("4000WholeYearCharge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(24.5).getAmount(), subject.charge().getAmount());
	}
	
	
	@Test
	public void BusinessSite199Winter() throws NoReadingsException {
		BusinessSite subject = new BusinessSite();
		subject.addReading(new Reading(100, new Date(1997, 1, 1)));
		subject.addReading(new Reading(299, new Date(1997, 2, 1)));
		//System.out.println("199WinterCharge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(1.88).getAmount(), subject.charge().getAmount());
	}
	
	@Test
	public void BusinessSite199Summer() throws NoReadingsException {
		BusinessSite subject = new BusinessSite();
		subject.addReading(new Reading(300, new Date(1997, 6, 15)));
		subject.addReading(new Reading(499, new Date(1997, 31, 8)));
		//System.out.println("199SummerCharge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(1.88).getAmount(), subject.charge().getAmount());
	}
	
	
	@Test
	public void ResidentialSite199Summer() throws NoReadingsException {
		ResidentialSite subject = new ResidentialSite(Registry.get("A"));
		subject.addReading(new Reading(300, new Date(1997, 6, 15)));
		subject.addReading(new Reading(499, new Date(1997, 31, 8)));
		//System.out.println("199SummerCharge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(4.32).getAmount(), subject.charge().getAmount());
	}
	
	
	@Test
	public void ResidentialSite4000WholeYear() throws NoReadingsException {
		ResidentialSite subject = new ResidentialSite(Registry.get("B"));
		subject.addReading(new Reading(1000, new Date(1997, 1, 1)));
		subject.addReading(new Reading(5000, new Date(1997, 12, 31)));
		//System.out.println("4000WholeYearCharge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(713.5).getAmount(), subject.charge().getAmount());
	}
	
	@After
	public void tearDown() throws Exception {
	}

}
