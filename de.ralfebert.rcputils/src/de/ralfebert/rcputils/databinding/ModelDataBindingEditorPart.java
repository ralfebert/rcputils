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

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

/**
 * ModelDataBindingEditorPart is base class for editors that use data binding to
 * edit a single model object.
 * 
 * The actual editor implementation goes into the methods onLoad (loading the
 * model object from the editor input), onCreatePartControl (creating the UI),
 * onBind (binding the UI to the model), onSave (saving the model object).
 * 
 * This resembles the typical structure for editors that edit a single model
 * object.
 * 
 * Please take care that the UI are the targets when creating your bindings as
 * all changes in target observables (the UI) will cause the editor to get
 * dirty.
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
public abstract class ModelDataBindingEditorPart<INPUT extends IEditorInput, MODEL> extends
		DataBindingEditorPart<INPUT> {

	/**
	 * Stores the model value. This is an observable itself so we can exchange
	 * the whole model, for example after save if a new object is retrieved by
	 * saving.
	 */
	private WritableValue modelValue;
	private DataBindingContext dataBindingContext;

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		modelValue = new WritableValue();
	}

	@Override
	public final void doSave(IProgressMonitor monitor) {
		dataBindingContext.updateModels();
		modelValue.setValue(onSave(getModelObject(), monitor));
		onReload(getModelObject());
		dataBindingContext.updateTargets();
		dirty.setDirty(false);
	}

	protected void onReload(MODEL modelObject) {

	}

	protected final void reload() {
		if (isReady()) {
			modelValue.setValue(onLoad(getEditorInput()));
			onReload(getModelObject());
		}
	}

	@SuppressWarnings("unchecked")
	protected final MODEL getModelObject() {
		return (MODEL) modelValue.getValue();
	}

	@Override
	protected void onAfterUi(Composite parent) {
		super.onAfterUi(parent);

		// Load model
		reload();

		// Bind UI
		dataBindingContext = new DataBindingContext();
		onBind(dataBindingContext, modelValue);

		// Changes in target observables (UI) => editor gets dirty
		ChangeTracker changeTracker = new ChangeTracker();
		changeTracker.trackTargetObservables(dataBindingContext);
		changeTracker.addChangeListener(dirty);
	}

	/**
	 * Implement onLoad to retrieve the model object by the editor input object.
	 */
	protected abstract MODEL onLoad(INPUT input);

	/**
	 * Implement onSave to persist the model object. You can return a new model
	 * object.
	 */
	protected abstract MODEL onSave(MODEL modelObject, IProgressMonitor monitor);

	/**
	 * Implement onBind to bind the model to the UI. Use detail values like
	 * PojoObservables.observeDetailValue to refer to the model properties.
	 */
	protected abstract void onBind(DataBindingContext dataBindingContext, IObservableValue model);

	protected boolean isReady() {
		return true;
	}

}
