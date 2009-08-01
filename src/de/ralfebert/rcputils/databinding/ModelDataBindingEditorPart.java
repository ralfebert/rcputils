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
public abstract class ModelDataBindingEditorPart<INPUT extends IEditorInput, MODEL> extends DataBindingEditorPart {

	/**
	 * Stores the model value. This is an observable itself so we can exchange
	 * the whole model, for example after save if a new object is retrieved by
	 * saving.
	 */
	private WritableValue modelValue;
	private DataBindingContext dataBindingContext;

	@SuppressWarnings("unchecked")
	@Override
	public final INPUT getEditorInput() {
		return (INPUT) super.getEditorInput();
	}

	@Override
	public final void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		modelValue = new WritableValue();
		modelValue.setValue(onLoad(input));
	}

	@Override
	public final void doSave(IProgressMonitor monitor) {
		dataBindingContext.updateModels();
		modelValue.setValue(onSave(getModelObject(), monitor));
		dataBindingContext.updateTargets();
		dirty.setDirty(false);
	}

	@SuppressWarnings("unchecked")
	private final MODEL getModelObject() {
		return (MODEL) modelValue.getValue();
	}

	@Override
	public final void createPartControl(Composite parent) {

		// Create UI
		onCreatePartControl(parent);

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
	protected abstract MODEL onLoad(IEditorInput input);

	/**
	 * Implement onSave to persist the model object. You can return a new model
	 * object.
	 */
	protected abstract MODEL onSave(MODEL modelObject, IProgressMonitor monitor);

	/**
	 * Implement onCreatePartControl to create your UI.
	 */
	protected abstract void onCreatePartControl(Composite parent);

	/**
	 * Implement onBind to bind the model to the UI. Use detail values like
	 * PojoObservables.observeDetailValue to refer to the model properties.
	 */
	protected abstract void onBind(DataBindingContext dataBindingContext, IObservableValue model);

}
