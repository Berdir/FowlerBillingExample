package fowler.energybilling.sites;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import fowler.energybilling.Dollars;
import fowler.energybilling.NoReadingsException;
import fowler.energybilling.Zone;

public abstract class TimedSite extends Site {

	protected Zone _zone;

	public TimedSite(Zone zone) {
		super();
		_zone = zone;
	}

	protected Dollars calculateSummerWinterRate(int usage, Interval interval) {
		Dollars result;
		double summerFraction = _zone.getSummerFraction(interval.getStart(),
				interval.getStart());
		result = new Dollars((usage * _zone.getSummerRate() * summerFraction)
				+ (usage * _zone.getWinterRate() * (1 - summerFraction)));
		return result;
	}

	public Dollars charge() {
		try {
			DateTime end = getRecentReading().date();
			DateTime start = getRecentReading(1).date();

			start = start.plusDays(1);

			Interval interval = new Interval(start, end);
			return charge(getRecentUsage(), interval);
		} catch (NoReadingsException e) {
			return new Dollars(0);
		}
	}

	protected abstract Dollars charge(int fullUsage, Interval interval);

}