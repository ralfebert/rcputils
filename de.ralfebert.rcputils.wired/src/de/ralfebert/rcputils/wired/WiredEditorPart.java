package de.ralfebert.rcputils.wired;

import org.eclipse.riena.core.wire.Wire;
import org.eclipse.riena.core.wire.WirePuller;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.osgi.framework.FrameworkUtil;

public abstract class WiredEditorPart<INPUT extends IEditorInput> extends EditorPart {

	private WirePuller wire;

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	@Override
	public final void createPartControl(Composite parent) {
		createUi(parent);
		wire = Wire.instance(this).andStart(FrameworkUtil.getBundle(this.getClass()).getBundleContext());
	}

	protected abstract void createUi(Composite parent);

	@Override
	public void dispose() {
		super.dispose();
		if (wire != null) {
			wire.stop();
			wire = null;
		}
	}

	@Override
	public void doSaveAs() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public INPUT getEditorInput() {
		return (INPUT) super.getEditorInput();
	}

}
