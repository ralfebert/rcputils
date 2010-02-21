package de.ralfebert.rcputils.wired;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.riena.core.wire.Wire;
import org.eclipse.riena.core.wire.WirePuller;
import org.osgi.framework.FrameworkUtil;

public abstract class WiredHandler extends AbstractHandler {

	private WirePuller wire;

	public WiredHandler() {
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
