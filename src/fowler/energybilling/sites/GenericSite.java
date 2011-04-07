package fowler.energybilling.sites;

import fowler.energybilling.Dollars;
import fowler.energybilling.NoReadingsException;

public abstract class GenericSite extends Site {

	public GenericSite() {
		super();
	}

	protected abstract Dollars charge(int usage);

	public Dollars charge() {
		try {
			return charge(getRecentUsage());
		} catch (NoReadingsException e) {
			return new Dollars(0);
		}
	}

}