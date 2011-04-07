package fowler.energybilling;

public abstract class GenericSite extends Site {

	public GenericSite() {
		super();
	}

	protected abstract Dollars charge(int usage);

	public Dollars charge() throws NoReadingsException {
		int usage = getRecentReading().amount() - getRecentReading(1).amount();
		return charge(usage);
	}

}