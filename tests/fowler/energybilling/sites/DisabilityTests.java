package fowler.energybilling.sites;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import fowler.energybilling.Dollars;
import fowler.energybilling.NoReadingsException;
import fowler.energybilling.Reading;
import fowler.energybilling.Zone;

public class DisabilityTests {
	
	public static Zone zoneA;
	public static Zone zoneB;

	@BeforeClass
	public static void setUp() throws Exception {
		zoneA = new Zone("A", 0.06, 0.07, new Date(1997, 5, 15), new Date(
				1997, 9, 10));
		zoneB = new Zone("B", 0.07, 0.06, new Date(1997, 6, 5), new Date(
				1997, 8, 31));
	}
	
	@Test
	public void DisabilitySite0() throws NoReadingsException {
		TimedSite subject = new DisabilitySite(zoneA);
		subject.addReading(new Reading(10, new Date(1997, 1, 1)));
		subject.addReading(new Reading(10, new Date(1997, 2, 1)));
		assertTrue(subject.charge().getAmount() == 0.0);
	}
	
	//typical boundaries for the disability site: used more than 200, which is removed by the cap
	//otherwise, some readings within the summer and winter period dates could be added to see if this makes a difference
	//the original code by Fowler only charges 0 in all cases
	
	@Test
	public void DisabilitySite199Winter() throws NoReadingsException {
		TimedSite subject = new DisabilitySite(zoneA);
		subject.addReading(new Reading(100, new Date(1997, 1, 1)));
		subject.addReading(new Reading(299, new Date(1997, 2, 1)));
		//System.out.println("199WinterCharge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(4.97).getAmount(), subject.charge().getAmount());
	}
	
	@Test
	public void DisabilitySite199Summer() throws NoReadingsException {
		TimedSite subject = new DisabilitySite(zoneB);
		subject.addReading(new Reading(300, new Date(1997, 6, 5)));
		subject.addReading(new Reading(499, new Date(1997, 31, 8)));
		//System.out.println("199SummerCharge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(5.29).getAmount(), subject.charge().getAmount());
	}
	
	@Test
	//the summerdays calculation yield -134 for this case - this clearly an error, but we ignore it for now
	public void DisabilitySite199WholeYear() throws NoReadingsException {
		TimedSite subject = new DisabilitySite(zoneA);
		subject.addReading(new Reading(20000, new Date(1997, 1, 1)));
		subject.addReading(new Reading(20199, new Date(1997, 12, 31)));
		//System.out.println("199WholeYearCharge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(-21.69).getAmount(), subject.charge().getAmount());
	}
	
	@Test
	public void DisabilitySite4000WholeYear() throws NoReadingsException {
		TimedSite subject = new DisabilitySite(zoneB);
		subject.addReading(new Reading(1000, new Date(1997, 1, 1)));
		subject.addReading(new Reading(5000, new Date(1997, 12, 31)));
		//System.out.println("4000WholeYearCharge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(125.66).getAmount(), subject.charge().getAmount());
	}
}
