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
package de.ralfebert.rcputils.properties;

/**
 * Base class for IValue. This is for values you implement yourself. The generic
 * argument T refers the type of the element this value can be used on.
 * Implementing set is optional.
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
public abstract class BaseValue<T> implements IValue {

	@SuppressWarnings("unchecked")
	public final Object getValue(Object element) {
		return get((T) element);
	}

	@SuppressWarnings("unchecked")
	public void setValue(Object element, Object value) {
		set((T) element, value);
	}

	public abstract Object get(T element);

	public Object set(T element, Object value) {
		throw new UnsupportedOperationException("Overwrite value.set() to set values!");
	}
}
