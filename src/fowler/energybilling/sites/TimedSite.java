package fowler.energybilling.sites;

import java.util.Date;

import fowler.energybilling.Dollars;
import fowler.energybilling.NoReadingsException;
import fowler.energybilling.Zone;

public abstract class TimedSite extends Site {

	protected static final double FUEL = 0.0175;
	protected Zone _zone;

	public TimedSite(Zone zone) {
		super();
		_zone = zone;
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

	protected Dollars calculateSummerWinterRate(int usage, Date start,
			Date end) {
				Dollars result;
				double summerFraction = _zone.getSummerFraction(start, end);
				result = new Dollars((usage * _zone.getSummerRate() * summerFraction)
						+ (usage * _zone.getWinterRate() * (1 - summerFraction)));
				return result;
			}

}