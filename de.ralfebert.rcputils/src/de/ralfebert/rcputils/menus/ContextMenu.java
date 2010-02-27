package de.ralfebert.rcputils.menus;

import org.eclipse.core.commands.common.CommandException;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.menus.CommandContributionItem;

import de.ralfebert.rcputils.internal.RcpUtilsPlugin;

/**
 * ContextMenu creates a context menu for a structured viewer and registers it
 * with the workbench. It can assist with handling the default menu item.
 * 
 * @author Ralf Ebert
 * @see http://www.ralfebert.de/blog/eclipsercp/commands_context_menu/
 */
public class ContextMenu {

	private static final String MENU_ID_DEFAULT = ".default";

	private final Menu menu;
	private final IWorkbenchPartSite site;
	private final StructuredViewer viewer;
	private final boolean defaultItemHandling = false;

	/**
	 * Creates an empty context menu for a structured viewer and registers it
	 * for the given workbench part site. The created menu is intended to be
	 * filled using contributions to "org.eclipse.ui.menus" with locationURI
	 * "popup:<viewid>". If defaultItemHandling = true,
	 */
	public ContextMenu(StructuredViewer viewer, final IWorkbenchPartSite site) {
		this.viewer = viewer;
		this.site = site;

		MenuManager menuManager = new MenuManager();
		menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

		this.menu = menuManager.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);

		site.registerContextMenu(menuManager, viewer);
		site.setSelectionProvider(viewer);
	}

	@Deprecated
	public ContextMenu(StructuredViewer viewer, final IWorkbenchPartSite site, boolean defaultItemHandling) {
		this(viewer, site);
		setDefaultItemHandling(defaultItemHandling);
	}

	/**
	 * Activates default item handling. When set to true, the menu is extended
	 * so that the first command contribution whose contribution id ends with
	 * ".default" is set as default item for the menu. The double click event is
	 * handled by executing the default command.
	 */
	public void setDefaultItemHandling(boolean defaultItemHandling) {
		if (this.defaultItemHandling != defaultItemHandling) {
			if (defaultItemHandling) {
				menu.addMenuListener(listenerSetDefaultItem);
				viewer.addDoubleClickListener(listenerExecuteDefaultCommand);
			} else {
				menu.removeMenuListener(listenerSetDefaultItem);
				viewer.removeDoubleClickListener(listenerExecuteDefaultCommand);
			}
		}
	}

	private MenuItem getDefaultMenuItem() {
		for (MenuItem menuItem : menu.getItems()) {
			if (menuItem.getData() instanceof CommandContributionItem) {
				CommandContributionItem contributionItem = (CommandContributionItem) menuItem.getData();
				if (contributionItem.getId() != null && contributionItem.getId().endsWith(MENU_ID_DEFAULT)) {
					return menuItem;
				}
			}
		}
		return null;
	}

	private final MenuListener listenerSetDefaultItem = new MenuAdapter() {

		@Override
		public void menuShown(MenuEvent event) {
			menu.setDefaultItem(getDefaultMenuItem());
		}

	};

	private final IDoubleClickListener listenerExecuteDefaultCommand = new IDoubleClickListener() {

		public void doubleClick(DoubleClickEvent event) {
			menu.notifyListeners(SWT.Show, new Event());
			MenuItem defaultItem = getDefaultMenuItem();
			if (defaultItem != null) {
				CommandContributionItem contribution = (CommandContributionItem) defaultItem.getData();
				IHandlerService handlerService = (IHandlerService) site.getService(IHandlerService.class);
				try {
					handlerService.executeCommand(contribution.getCommand(), null);
				} catch (CommandException e) {
					RcpUtilsPlugin.logException(e);
				}
			}
		}
	};

}