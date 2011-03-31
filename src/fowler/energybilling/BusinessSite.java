package fowler.energybilling;

public class BusinessSite extends Site {
	private int lastReading;
	private Reading[] _readings = new Reading[1000];
	private static final double START_RATE = 0.09;
	static final double END_RATE = 0.05;
	static final int END_AMOUNT = 1000;

	public void addReading(Reading newReading) {
		_readings[++lastReading] = newReading;
	}

	public Dollars charge() {
		int usage = _readings[lastReading].amount()
				- _readings[lastReading - 1].amount();
		return charge(usage);
	}

	private Dollars charge(int usage) {
		Dollars result;
		if (usage == 0)
			return new Dollars(0);
		double t1 = START_RATE - ((END_RATE * END_AMOUNT) - START_RATE)
				/ (END_AMOUNT - 1);
		double t2 = ((END_RATE * END_AMOUNT) - START_RATE)
				* Math.min(END_AMOUNT, usage) / (END_AMOUNT - 1);
		double t3 = Math.max(usage - END_AMOUNT, 0) * END_RATE;
		result = new Dollars(t1 + t2 + t3);
		result = result.plus(new Dollars(usage * 0.0175));
		Dollars base = new Dollars(result.min(new Dollars(50)).times(0.07));
		if (result.isGreaterThan(new Dollars(50))) {
			base = new Dollars(base.plus(result.min(new Dollars(75))
					.minus(new Dollars(50)).times(0.06)));
		}
		if (result.isGreaterThan(new Dollars(75))) {
			base = new Dollars(base.plus(result.minus(new Dollars(75)).times(
					0.05)));
		}
		result = result.plus(base);
		result = result.round(2);
		return result;
	}

}
