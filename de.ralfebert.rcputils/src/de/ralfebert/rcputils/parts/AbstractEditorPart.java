package de.ralfebert.rcputils.parts;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public abstract class AbstractEditorPart<INPUT extends IEditorInput> extends EditorPart {

	private ResourceManager resources;

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	@Override
	public final void createPartControl(Composite parent) {
		this.resources = new LocalResourceManager(JFaceResources.getResources(), parent);
		onBeforeUi(parent);
		onCreateUi(parent);
		onAfterUi(parent);
	}

	protected void onBeforeUi(Composite parent) {

	}

	protected void onAfterUi(Composite parent) {

	}

	/**
	 * Implement onCreateUi to create your UI.
	 */
	protected abstract void onCreateUi(Composite parent);

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void doSaveAs() {
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("unchecked")
	public INPUT getEditorInput() {
		return (INPUT) super.getEditorInput();
	}

	protected Display getDisplay() {
		return getSite().getShell().getDisplay();
	}

	protected ResourceManager getResources() {
		return resources;
	}

}
