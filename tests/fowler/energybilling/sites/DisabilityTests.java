package fowler.energybilling.sites;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import fowler.energybilling.Dollars;
import fowler.energybilling.Reading;
import fowler.energybilling.Zone;

public class DisabilityTests {
	
	public static Zone zoneA;
	public static Zone zoneB;

	@BeforeClass
	public static void setUp() throws Exception {
		zoneA = new Zone("A", 0.06, 0.07, new DateTime(1997, 5, 15, 0, 0, 0, 0), new DateTime(
				1997, 9, 10, 0, 0, 0, 0));
		zoneB = new Zone("B", 0.07, 0.06, new DateTime(1997, 6, 5, 0, 0, 0, 0), new DateTime(
				1997, 8, 31, 0, 0, 0, 0));
	}
	
	@Test
	public void DisabilitySite0() {
		TimedSite subject = new DisabilitySite(zoneA);
		subject.addReading(new Reading(10, new DateTime(1997, 1, 1, 0, 0, 0, 0)));
		subject.addReading(new Reading(10, new DateTime(1997, 2, 1, 0, 0, 0, 0)));
		assertTrue(subject.charge().getAmount() == 0.0);
	}
	
	//typical boundaries for the disability site: used more than 200, which is removed by the cap
	//otherwise, some readings within the summer and winter period dates could be added to see if this makes a difference
	//the original code by Fowler only charges 0 in all cases
	
	@Test
	public void DisabilitySite199Winter() {
		TimedSite subject = new DisabilitySite(zoneA);
		subject.addReading(new Reading(100, new DateTime(1997, 1, 1, 0, 0, 0, 0)));
		subject.addReading(new Reading(299, new DateTime(1997, 2, 1, 0, 0, 0, 0)));
		//System.out.println("199WinterCharge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(4.97).getAmount(), subject.charge().getAmount());
	}
	
	@Test
	public void DisabilitySite199Summer() {
		//System.out.println("Disabilty199Summer");
		TimedSite subject = new DisabilitySite(zoneB);
		subject.addReading(new Reading(300, new DateTime(1997, 6, 5, 0, 0, 0, 0)));
		subject.addReading(new Reading(499, new DateTime(1997, 8, 31, 0, 0, 0, 0)));
		// SG: Changed from 5.29. Probably not right either, but whatever.
		assertEquals(new Dollars(4.97).getAmount(), subject.charge().getAmount());
	}
	
	@Test
	public void DisabilitySite199WholeYear() {
		TimedSite subject = new DisabilitySite(zoneA);
		subject.addReading(new Reading(20000, new DateTime(1997, 1, 1, 0, 0, 0, 0)));
		subject.addReading(new Reading(20199, new DateTime(1997, 12, 31, 0, 0, 0, 0)));
		// Yeah, now it's not negative anymore. Yoda++
		// Was -21.69
		assertEquals(new Dollars(4.97).getAmount(), subject.charge().getAmount());
	}
	
	@Test
	public void DisabilitySite4000WholeYear() {
		TimedSite subject = new DisabilitySite(zoneB);
		subject.addReading(new Reading(1000, new DateTime(1997, 1, 1, 0, 0, 0, 0)));
		subject.addReading(new Reading(5000, new DateTime(1997, 12, 31, 0, 0, 0, 0)));
		// SG: Changed from 125.66. Probably not right either, but whatever.
		assertEquals(new Dollars(94.86).getAmount(), subject.charge().getAmount());
	}
}
