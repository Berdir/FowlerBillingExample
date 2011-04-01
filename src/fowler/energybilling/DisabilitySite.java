package fowler.energybilling;

import java.util.Date;

public class DisabilitySite extends TimedSite {
	private static final Dollars FUEL_TAX_CAP = new Dollars(0.10);
	private static final double TAX_RATE = 0.05;
	private Zone _zone; // zone must be initialized!!!
	private static final int CAP = 200;

	public DisabilitySite(Zone zone) {
		this._zone = zone;

	}

	@Override
	protected Dollars charge(int fullUsage, Date start, Date end) {
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

}