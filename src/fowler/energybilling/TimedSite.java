package fowler.energybilling;

import java.util.Date;

public abstract class TimedSite extends Site {

	public TimedSite() {
		super();
	}

	protected abstract Dollars charge(int fullUsage, Date start, Date end);

	public Dollars charge() throws NoReadingsException {
		int size = _readings.size();
		if (size < 2) {
			throw new NoReadingsException();
		}
		
		int usage = _readings.get(size - 1).amount() - _readings.get(size - 2).amount();
		Date end = _readings.get(size - 1).date();
		Date start = _readings.get(size - 2).date();
		start.setDate(start.getDate() + 1); // set to beginning of period
		return charge(usage, start, end);
	}

}