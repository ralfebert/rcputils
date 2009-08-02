package de.ralfebert.rcputils.databinding;

import org.eclipse.core.databinding.observable.Realm;

public class Realms {

	/**
	 * A realm that will accept any thread
	 */
	public static final Realm WHATEVER = new Realm() {

		@Override
		public boolean isCurrent() {
			return true;
		}
	};

}
