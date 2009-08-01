package de.ralfebert.rcputils.internal;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

public class RcpUtilsPlugin extends Plugin {

	public static final String PLUGIN_ID = "de.ralfebert.rcputils";

	private static RcpUtilsPlugin plugin;

	public RcpUtilsPlugin() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static RcpUtilsPlugin getDefault() {
		return plugin;
	}

	public static void logException(Exception e) {
		getDefault().getLog().log(new Status(Status.ERROR, RcpUtilsPlugin.PLUGIN_ID, -1, e.getMessage(), e));
	}
}
