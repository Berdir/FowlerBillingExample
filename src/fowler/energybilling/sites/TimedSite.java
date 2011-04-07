package fowler.energybilling.sites;

import java.util.Date;

import fowler.energybilling.Dollars;
import fowler.energybilling.NoReadingsException;

public abstract class TimedSite extends Site {

	public TimedSite() {
		super();
	}

	protected abstract Dollars charge(int fullUsage, Date start, Date end);

	public Dollars charge() {
		try {
			Date end = getRecentReading().date();
			Date start = getRecentReading(1).date();
			start.setDate(start.getDate() + 1); // set to beginning of period
			return charge(getRecentUsage(), start, end);
		} catch (NoReadingsException e) {
			return new Dollars(0);
		}
	}

}