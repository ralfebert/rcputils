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

import java.util.Iterator;
import java.util.LinkedHashSet;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;
import org.eclipse.core.databinding.observable.list.ListDiffVisitor;
import org.eclipse.core.runtime.Assert;

/**
 * ChangeTracker allows to observe all model or target observables in a
 * DataBindingContext. Can be used to set the dirty flag of an editor when
 * something changes.
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
public class ChangeTracker {

	private final LinkedHashSet<IChangeListener> changeListeners = new LinkedHashSet<IChangeListener>();

	private final IChangeListener CHANGE_LISTENER = new IChangeListener() {

		public void handleChange(ChangeEvent event) {
			for (IChangeListener changeListener : changeListeners) {
				changeListener.handleChange(event);
			}
		}

	};

	private final ListDiffVisitor BINDINGS_MODEL_DIFF_VISITOR = new ListDiffVisitor() {

		@Override
		public void handleRemove(int index, Object element) {
			Assert.isTrue(element instanceof Binding, element + " is not a Binding");
			removeObservable(((Binding) element).getModel());
		}

		@Override
		public void handleAdd(int index, Object element) {
			Assert.isTrue(element instanceof Binding, element + " is not a Binding");
			addObservable(((Binding) element).getModel());
		}
	};

	private final IListChangeListener BINDINGS_MODEL_CHANGE_LISTENER = new IListChangeListener() {

		public void handleListChange(ListChangeEvent event) {
			event.diff.accept(BINDINGS_MODEL_DIFF_VISITOR);
		}

	};

	private final ListDiffVisitor BINDINGS_TARGET_DIFF_VISITOR = new ListDiffVisitor() {

		@Override
		public void handleRemove(int index, Object element) {
			Assert.isTrue(element instanceof Binding, element + " is not a Binding");
			removeObservable(((Binding) element).getTarget());
		}

		@Override
		public void handleAdd(int index, Object element) {
			Assert.isTrue(element instanceof Binding, element + " is not a Binding");
			addObservable(((Binding) element).getTarget());
		}
	};

	private final IListChangeListener BINDINGS_TARGET_CHANGE_LISTENER = new IListChangeListener() {

		public void handleListChange(ListChangeEvent event) {
			event.diff.accept(BINDINGS_TARGET_DIFF_VISITOR);
		}

	};

	public void addObservable(IObservable observable) {
		observable.addChangeListener(CHANGE_LISTENER);
	}

	public void removeObservable(IObservable observable) {
		observable.removeChangeListener(CHANGE_LISTENER);
	}

	@SuppressWarnings("unchecked")
	public void trackModelObservables(DataBindingContext bindings) {
		for (Iterator<Binding> iterator = bindings.getBindings().iterator(); iterator.hasNext();) {
			Binding binding = iterator.next();
			addObservable(binding.getModel());
		}

		bindings.getBindings().addListChangeListener(BINDINGS_MODEL_CHANGE_LISTENER);
	}

	@SuppressWarnings("unchecked")
	public void trackTargetObservables(DataBindingContext bindings) {
		for (Iterator<Binding> iterator = bindings.getBindings().iterator(); iterator.hasNext();) {
			Binding binding = iterator.next();
			addObservable(binding.getTarget());
		}

		bindings.getBindings().addListChangeListener(BINDINGS_TARGET_CHANGE_LISTENER);
	}

	public void addChangeListener(IChangeListener changeListener) {
		changeListeners.add(changeListener);
	}

	public void removeChangeListener(IChangeListener changeListener) {
		changeListeners.remove(changeListener);
	}

}
