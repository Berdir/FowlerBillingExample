package fowler.energybilling.sites;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.junit.Test;

import fowler.energybilling.Dollars;
import fowler.energybilling.Reading;

public class BusinessTests {
	@Test
	public void BusinessSite0() {
		BusinessSite subject = new BusinessSite();
		subject.addReading(new Reading(10, new DateTime(1997, 1, 1, 0, 0, 0, 0)));
		subject.addReading(new Reading(10, new DateTime(1997, 2, 1, 0, 0, 0, 0)));
		assertTrue(subject.charge().getAmount() == 0.0);
	}

	@Test
	public void BusinessSite4000WholeYear() {
		BusinessSite subject = new BusinessSite();
		subject.addReading(new Reading(1000, new DateTime(1997, 1, 1, 0, 0, 0,
				0)));
		subject.addReading(new Reading(5000, new DateTime(1997, 12, 31, 0, 0,
				0, 0)));
		// System.out.println("4000WholeYearCharge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(24.5).getAmount(), subject.charge()
				.getAmount());
	}

	@Test
	public void BusinessSite199Winter() {
		BusinessSite subject = new BusinessSite();
		subject.addReading(new Reading(100,
				new DateTime(1997, 1, 1, 0, 0, 0, 0)));
		subject.addReading(new Reading(299,
				new DateTime(1997, 2, 1, 0, 0, 0, 0)));
		// System.out.println("199WinterCharge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(1.88).getAmount(), subject.charge()
				.getAmount());
	}

	@Test
	public void BusinessSite199Summer() {
		BusinessSite subject = new BusinessSite();
		subject.addReading(new Reading(300, new DateTime(1997, 6, 15, 0, 0, 0,
				0)));
		subject.addReading(new Reading(499, new DateTime(1997, 8, 31, 0, 0, 0,
				0)));
		// System.out.println("199SummerCharge is: "+subject.charge().getAmount());
		assertEquals(new Dollars(1.88).getAmount(), subject.charge()
				.getAmount());
	}
}
