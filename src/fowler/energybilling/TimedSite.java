package fowler.energybilling;

import java.util.Date;

public abstract class TimedSite extends Site {

	public TimedSite() {
		super();
	}

	protected abstract Dollars charge(int fullUsage, Date start, Date end);

	public Dollars charge() throws NoReadingsException {
		Date end = getRecentReading().date();
		Date start = getRecentReading(1).date();
		start.setDate(start.getDate() + 1); // set to beginning of period
		return charge(getRecentUsage(), start, end);
	}

}