package org.eclipselabs.rcputils.wired;

import org.eclipse.riena.core.wire.Wire;
import org.eclipse.riena.core.wire.WirePuller;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipselabs.rcputils.parts.AbstractEditorPart;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;


public abstract class WiredEditorPart<INPUT extends IEditorInput> extends
		AbstractEditorPart<INPUT> {

	private WirePuller wire;

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		Bundle bundle = FrameworkUtil.getBundle(this.getClass());
		if (bundle != null) {
			wire = Wire.instance(this).andStart(bundle.getBundleContext());
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		if (wire != null) {
			wire.stop();
			wire = null;
		}
	}

}