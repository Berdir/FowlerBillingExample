package fowler.energybilling;

import java.util.Date;

public abstract class TimedSite extends Site {

	public TimedSite() {
		super();
	}

	protected abstract Dollars charge(int fullUsage, Date start, Date end);

	public void addReading(Reading newReading) {
		int i;
		for (i = 0; _readings[i] != null; i++);
		_readings[i] = newReading;
	}

	public Dollars charge() {
		int i;
		for (i = 0; _readings[i] != null; i++);
		// JK i - 1 is the last reading, i-2 the one before
		int usage = _readings[i - 1].amount() - _readings[i - 2].amount();
		Date end = _readings[i - 1].date();
		Date start = _readings[i - 2].date();
		start.setDate(start.getDate() + 1); // set to beginning of period
		return charge(usage, start, end);
	}

}