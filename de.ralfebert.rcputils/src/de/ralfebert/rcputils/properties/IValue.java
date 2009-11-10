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
 * IValue describes a value that can be get from / set to an object.
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
public interface IValue {

	public Object getValue(Object element);

	public void setValue(Object element, Object value);

}
