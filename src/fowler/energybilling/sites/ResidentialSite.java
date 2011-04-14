package fowler.energybilling.sites;

import java.util.Date;

import fowler.energybilling.Dollars;
import fowler.energybilling.Zone;

public class ResidentialSite extends TimedSite {

	public ResidentialSite(Zone zone) {
		super(zone);
	}

	protected Dollars charge(int usage, Date start, Date end) {
		Dollars result = calculateSummerWinterRate(usage, start, end);

		// SG: This is what is was imho meant to be calculcated.
	 	// The original code line resulted in times(TAX_RATE * 2) instead.
		result.times(1 + TAX_RATE);

		result.plus(new Dollars(usage * FUEL).times(1 + TAX_RATE));
		result.round(2);
		return result;
	}
}
