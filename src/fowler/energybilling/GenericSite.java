package fowler.energybilling;

public abstract class GenericSite extends Site {

	public GenericSite() {
		super();
	}

	protected abstract Dollars charge(int usage);

	public Dollars charge() throws NoReadingsException {
		int size = _readings.size();
		if (size < 2) {
			throw new NoReadingsException();
		}
		int usage = _readings.get(size - 1).amount() - _readings.get(size - 2).amount();
		return charge(usage);
	}

}