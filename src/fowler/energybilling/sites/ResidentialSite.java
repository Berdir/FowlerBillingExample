package fowler.energybilling.sites;

import java.util.Date;

import fowler.energybilling.Dollars;
import fowler.energybilling.Zone;

public class ResidentialSite extends TimedSite {

	private static final double TAX_RATE = 0.05;
	public ResidentialSite(Zone zone) {
		_zone = zone;
	}

	protected Dollars charge(int usage, Date start, Date end) {
		Dollars result = calculateSummerWinterRate(usage, start, end);
		
		result = result.plus(new Dollars(result.times(TAX_RATE))); 
		
		Dollars fuel = new Dollars(usage * 0.0175);
		result = result.plus(fuel);
		
		result = new Dollars(result.plus(fuel.times(TAX_RATE))); 
		
		result = result.round(2);
		return result;
	}

	protected Dollars calculateSummerWinterRate(int usage, Date start, Date end) {
		Dollars result;
		double summerFraction = _zone.getSummerFraction(start, end);

		result = new Dollars((usage * _zone.getSummerRate() * summerFraction)
				+ (usage * _zone.getWinterRate() * (1 - summerFraction)));
		return result;
	}
}
