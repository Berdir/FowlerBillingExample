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
		// JK: don't charge more than the CAP (which is set to 200)
		int usage = Math.min(fullUsage, CAP);
		
		Dollars result = calculateSummerWinterRate(usage, start, end);
		// if the full usage is below the cap the max yields zero
		result.plus(new Dollars(Math.max(fullUsage - usage, 0) * 0.062));

		result.plus(result.times(TAX_RATE));
		Dollars fuel = new Dollars(fullUsage * FUEL);
		result.plus(fuel);
		result.plus(fuel.times(TAX_RATE).min(FUEL_TAX_CAP));
		result.round(2);
		return result;
	}
}