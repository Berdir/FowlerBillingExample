package fowler.energybilling;

public abstract class GenericSite extends Site {

	public GenericSite() {
		super();
	}

	protected abstract Dollars charge(int usage);

	public Dollars charge() {
		int size = _readings.size();
		if (size < 2) {
			// @todo Replace with better Exceptions, tests currently expect a NullPointer exception. 
			throw new NullPointerException();
		}
		int usage = _readings.get(size - 1).amount() - _readings.get(size - 2).amount();
		return charge(usage);
	}

}