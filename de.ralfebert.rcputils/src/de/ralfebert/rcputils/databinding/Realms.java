/*******************************************************************************
 * Copyright (c) 2008 Ralf Ebert
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Ralf Ebert - initial API and implementation
 *******************************************************************************/
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
