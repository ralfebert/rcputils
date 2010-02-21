package de.ralfebert.rcputils.wired;

import org.eclipse.riena.core.wire.Wire;
import org.eclipse.riena.core.wire.WirePuller;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.FrameworkUtil;

public abstract class WiredViewPart extends ViewPart {

	private WirePuller wire;

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		wire = Wire.instance(this).andStart(FrameworkUtil.getBundle(this.getClass()).getBundleContext());
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