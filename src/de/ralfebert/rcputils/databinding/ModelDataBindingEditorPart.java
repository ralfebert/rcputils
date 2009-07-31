package de.ralfebert.rcputils.databinding;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

public abstract class ModelDataBindingEditorPart<INPUT extends IEditorInput, MODEL> extends DataBindingEditorPart {

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
		onCreatePartControl(parent);
		dataBindingContext = new DataBindingContext();
		onBind(dataBindingContext, modelValue);

		ChangeTracker changeTracker = new ChangeTracker();
		changeTracker.trackTargetObservables(dataBindingContext);
		changeTracker.addChangeListener(dirty);
	}

	protected abstract MODEL onLoad(IEditorInput input);

	protected abstract MODEL onSave(MODEL modelObject, IProgressMonitor monitor);

	protected abstract void onBind(DataBindingContext dataBindingContext, IObservableValue model);

	protected abstract void onCreatePartControl(Composite parent);

}
