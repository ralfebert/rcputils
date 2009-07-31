package de.ralfebert.rcputils.databinding;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public abstract class DataBindingEditorPart extends EditorPart {

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
		setSite(site);
		setInput(input);
		partNameObservable = new PartNameObservableValue();
	}

	@Override
	public final boolean isDirty() {
		return dirty.isDirty();
	}

	@Override
	public void doSaveAs() {
		throw new UnsupportedOperationException();
	}

	protected IObservableValue getPartNameObservable() {
		return partNameObservable;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

}