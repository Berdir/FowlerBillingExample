package fowler.energybilling;

import java.util.Date;

public class DisabilitySite {
	private Reading[] _readings = new Reading[1000];
	private static final Dollars FUEL_TAX_CAP = new Dollars(0.10);
	private static final double TAX_RATE = 0.05;
	private Zone _zone; // zone must be initialized!!!
	private static final int CAP = 200;

	public DisabilitySite(Zone zone) {
		this._zone = zone;

	}

	public void addReading(Reading newReading) {
		int i;
		for (i = 0; _readings[i] != null; i++);
		_readings[i] = newReading;
	}

	// JK: usage and date calculation seem to be ok
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

	private Dollars charge(int fullUsage, Date start, Date end) {
		Dollars result;
		double summerFraction;
		// JK: don't charge more than the CAP (which is set to 200)
		int usage = Math.min(fullUsage, CAP);
		// JK: now we need the summer/winter time information from the zones
		if (start.after(_zone.getSummerEnd()) || end.before(_zone.getSummerStart()))
			summerFraction = 0;
		else if (!start.before(_zone.getSummerStart()) && !start.after(_zone.getSummerEnd())
				&& !end.before(_zone.getSummerStart()) && !end.after(_zone.getSummerEnd()))
			summerFraction = 1;
		else {
			double summerDays;
			if (start.before(_zone.getSummerStart()) || start.after(_zone.getSummerEnd())) {
				// end is in the summer
				summerDays = dayOfYear(end) - dayOfYear(_zone.getSummerStart()) + 1;
			} else {
				// start is in summer
				summerDays = dayOfYear(_zone.getSummerEnd()) - dayOfYear(start) + 1;
			}
			summerFraction = summerDays / (dayOfYear(end) - dayOfYear(start) + 1);
		}
		result = new Dollars((usage * _zone.getSummerRate() * summerFraction)
				+ (usage * _zone.getWinterRate() * (1 - summerFraction)));
		// if the full usage is below the cap the max yields zero
		result = result
				.plus(new Dollars(Math.max(fullUsage - usage, 0) * 0.062));

		result = result.plus(new Dollars(result.times(TAX_RATE)));
		Dollars fuel = new Dollars(fullUsage * 0.0175);
		result = result.plus(fuel);
		result = new Dollars(result.plus(fuel.times(TAX_RATE).min(FUEL_TAX_CAP)));
		result = result.round(2);
		return result;
	}

	int dayOfYear(Date arg) {
		int result;
		switch (arg.getMonth()) {
		case 0:
			result = 0;
			break;
		case 1:
			result = 31;
			break;
		case 2:
			result = 59;
			break;
		case 3:
			result = 90;
			break;
		case 4:
			result = 120;
			break;
		case 5:
			result = 151;
			break;
		case 6:
			result = 181;
			break;
		case 7:
			result = 212;
			break;
		case 8:
			result = 243;
			break;
		case 9:
			result = 273;
			break;
		case 10:
			result = 304;
			break;
		case 11:
			result = 334;
			break;
		default:
			throw new IllegalArgumentException();
		}
		result += arg.getDate();
		// check leap year
		if ((arg.getYear() % 4 == 0)
				&& ((arg.getYear() % 100 != 0) || ((arg.getYear() + 1900) % 400 == 0))) {
			result++;
		}
		return result;
	}

}