package fowler.energybilling.sites;

import org.joda.time.Interval;

import fowler.energybilling.Dollars;
import fowler.energybilling.Zone;

public class ResidentialSite extends TimedSite {

	public ResidentialSite(Zone zone) {
		super(zone);
	}

	@Override
	protected Dollars charge(int usage, Interval interval) {
		Dollars result = calculateSummerWinterRate(usage, interval);

		// SG: This is what is was imho meant to be calculcated.
	 	// The original code line resulted in times(TAX_RATE * 2) instead.
		result.times(1 + TAX_RATE);

		result.plus(new Dollars(usage * FUEL).times(1 + TAX_RATE));
		result.round(2);
		return result;
	}
}
