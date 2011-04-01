package fowler.energybilling;

public abstract class GenericSite extends Site {

	public GenericSite() {
		super();
	}

	protected abstract Dollars charge(int usage);

	public void addReading(Reading newReading) {
		Reading[] newArray = new Reading[_readings.length + 1];
		System.arraycopy(_readings, 0, newArray, 1, _readings.length);
		newArray[0] = newReading;
		_readings = newArray;
	}

	public Dollars charge() {
		int usage = _readings[0].amount() - _readings[1].amount();
		return charge(usage);
	}

}