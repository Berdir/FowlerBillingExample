package fowler.energybilling.sites;

import java.util.Date;

import fowler.energybilling.Dollars;
import fowler.energybilling.Zone;

public class DisabilitySite extends TimedSite {
	private static final Dollars FUEL_TAX_CAP = new Dollars(0.10);
	private static final double TAX_RATE = 0.05;
	private static final int CAP = 200;

	public DisabilitySite(Zone zone) {
		super(zone);
	}

	@Override
	protected Dollars charge(int fullUsage, Date start, Date end) {
		Dollars result;
		
		// JK: don't charge more than the CAP (which is set to 200)
		int usage = Math.min(fullUsage, CAP);
		
		result = calculateSummerWinterRate(start, end, usage);
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

	protected Dollars calculateSummerWinterRate(Date start, Date end, int usage) {
		Dollars result;
		double summerFraction = _zone.getSummerFraction(start, end);
		result = new Dollars((usage * _zone.getSummerRate() * summerFraction)
				+ (usage * _zone.getWinterRate() * (1 - summerFraction)));
		return result;
	}

}