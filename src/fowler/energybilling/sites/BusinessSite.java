package fowler.energybilling.sites;

import fowler.energybilling.Dollars;

public class BusinessSite extends GenericSite {
	private static final double START_RATE = 0.09;
	static final double END_RATE = 0.05;
	static final int END_AMOUNT = 1000;

	@Override
	protected Dollars charge(int usage) {
		if (usage == 0) {
			return new Dollars(0);
		}
		Dollars result = getDollarsFromUsage(usage);
		result = calculcateBaseDollars(usage, result);
		result.round(2);
		return result;
	}

	private Dollars calculcateBaseDollars(int usage, Dollars result) {
		result = result.plus(new Dollars(usage * 0.0175));
		Dollars base = result.min(new Dollars(50)).times(0.07);
		
		if (result.isGreaterThan(new Dollars(50))) {
			base = base.plus(result.min(new Dollars(75))
					.minus(new Dollars(50)).times(0.06));
		}
		if (result.isGreaterThan(new Dollars(75))) {
			base = base.plus(result.minus(new Dollars(75)).times(
					0.05));
		}
		result = result.plus(base);
		return result;
	}

	private Dollars getDollarsFromUsage(int usage) {
		Dollars result;
		double t1 = START_RATE - ((END_RATE * END_AMOUNT) - START_RATE)
				/ (END_AMOUNT - 1);
		double t2 = ((END_RATE * END_AMOUNT) - START_RATE)
				* Math.min(END_AMOUNT, usage) / (END_AMOUNT - 1);
		double t3 = Math.max(usage - END_AMOUNT, 0) * END_RATE;
		result = new Dollars(t1 + t2 + t3);
		return result;
	}

}
