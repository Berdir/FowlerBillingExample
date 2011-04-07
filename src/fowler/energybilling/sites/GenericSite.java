package fowler.energybilling.sites;

import fowler.energybilling.Dollars;
import fowler.energybilling.NoReadingsException;

public abstract class GenericSite extends Site {

	public GenericSite() {
		super();
	}

	protected abstract Dollars charge(int usage);

	public Dollars charge() throws NoReadingsException {
		return charge(getRecentUsage());
	}

}