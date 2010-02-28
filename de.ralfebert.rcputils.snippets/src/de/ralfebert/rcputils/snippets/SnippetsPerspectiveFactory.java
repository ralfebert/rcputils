package de.ralfebert.rcputils.snippets;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class SnippetsPerspectiveFactory implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		IFolderLayout snippetsFolder = layout.createFolder("snippets", IPageLayout.TOP, 0.35f, layout.getEditorArea());
		snippetsFolder.addView(RcpSnippetsConstants.VIEW_SNIPPET_01_TABLE_VIEWER_BUILDER);
		snippetsFolder.addView(RcpSnippetsConstants.VIEW_SNIPPET_02_STOCKS);
		layout.setEditorAreaVisible(false);
	}

}
