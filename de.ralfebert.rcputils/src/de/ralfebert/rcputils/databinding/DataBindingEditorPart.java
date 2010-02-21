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

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import de.ralfebert.rcputils.parts.AbstractEditorPart;

/**
 * DataBindingEditorPart is a base class for EditorParts that want to use
 * DataBinding. Provides a observable for partname and has a dirty attribute
 * that can be registered as change listener to ChangeTracker.
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
public abstract class DataBindingEditorPart<INPUT extends IEditorInput> extends AbstractEditorPart<INPUT> {

	protected final DirtyFlag dirty = new DirtyFlag();

	private IObservableValue partNameObservable;

	private final class PartNameObservableValue extends AbstractObservableValue {

		public PartNameObservableValue() {
			super(SWTObservables.getRealm(getSite().getShell().getDisplay()));
		}

		@Override
		protected Object doGetValue() {
			return getPartName();
		}

		@Override
		protected void doSetValue(Object value) {
			setPartName(String.valueOf(value));
		}

		public Object getValueType() {
			return String.class;
		}
	}

	protected final class DirtyFlag implements IChangeListener {

		private boolean dirty = false;

		public boolean isDirty() {
			return dirty;
		}

		public void setDirty(boolean dirty) {
			if (this.dirty != dirty) {
				this.dirty = dirty;
				firePropertyChange(PROP_DIRTY);
			}
		}

		public void handleChange(ChangeEvent event) {
			setDirty(true);
		}
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		partNameObservable = new PartNameObservableValue();
	}

	@Override
	public final boolean isDirty() {
		return dirty.isDirty();
	}

	protected IObservableValue getPartNameObservable() {
		return partNameObservable;
	}

}