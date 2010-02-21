package de.ralfebert.rcputils.wired;

import org.eclipse.riena.core.wire.Wire;
import org.eclipse.riena.core.wire.WirePuller;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.FrameworkUtil;

public abstract class WiredViewPart extends ViewPart {

	private WirePuller wire;

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

}