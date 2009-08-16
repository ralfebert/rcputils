package de.ralfebert.rcputils.builder.ctxmenu;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.menus.CommandContributionItem;

/**
 * ContextMenuBuilder is an utility class to create context menus for structured
 * viewers.
 * 
 * @author Ralf Ebert
 * @see http://www.ralfebert.de/blog/eclipsercp/commands_context_menu/
 */
public class ContextMenuBuilder {

	/**
	 * Creates an empty context menu for a structured viewer and registers it
	 * for the given workbench part site. The created menu is intended to be
	 * filled using contributions to "org.eclipse.ui.menus" with locationURI
	 * "popup:<viewid>". If defaultItemHandling = true, the menu is extended so
	 * that the first command contribution whose contribution id ends with
	 * ".default" is set as default item for the menu and handles the
	 * double-click in the structured viewer.
	 */
	public static void menuForViewer(StructuredViewer viewer, IWorkbenchPartSite site, boolean defaultItemHandling) {
		final Control control = viewer.getControl();

		MenuManager menuManager = new MenuManager();
		menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

		Menu menu = menuManager.createContextMenu(control);
		control.setMenu(menu);

		site.registerContextMenu(menuManager, viewer);
		site.setSelectionProvider(viewer);

		if (defaultItemHandling) {
			menu.addMenuListener(new MenuAdapter() {

				@Override
				public void menuShown(MenuEvent event) {
					Menu menu = (Menu) event.widget;
					for (MenuItem menuItem : menu.getItems()) {
						if (menuItem.getData() instanceof CommandContributionItem) {
							CommandContributionItem cci = (CommandContributionItem) menuItem.getData();
							if (cci.getId() != null && cci.getId().endsWith(".default")) {
								menu.setDefaultItem(menuItem);
								break;
							}
						}
					}
				}
			});

			viewer.addDoubleClickListener(new IDoubleClickListener() {

				public void doubleClick(DoubleClickEvent event) {
					Menu menu = control.getMenu();
					if (menu != null) {
						menu.notifyListeners(SWT.Show, new Event());
						if (menu.getDefaultItem() != null) {
							menu.getDefaultItem().notifyListeners(SWT.Selection, new Event());
						}
					}
				}
			});
		}
	}

}
