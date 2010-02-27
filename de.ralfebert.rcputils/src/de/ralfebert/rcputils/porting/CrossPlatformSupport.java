package de.ralfebert.rcputils.porting;

import org.eclipse.swt.SWT;

public class CrossPlatformSupport {

	/**
	 * Determines if the given style bits is supported on the current Platform.
	 * Information about this is to be added as needed. Returns always true if
	 * not known.
	 */
	public static boolean isStyleSupported(int style) {
		if ((style & (SWT.SEARCH | SWT.ICON_SEARCH | SWT.ICON_CANCEL)) > 0) {
			return isCocoa();
		}
		return true;
	}

	private static boolean isCocoa() {
		return SWT.getPlatform().equals("cocoa");
	}

}
