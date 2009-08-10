/*******************************************************************************
 * Copyright (c) 2008 Ralf Ebert
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Ralf Ebert - initial API and implementation
 *******************************************************************************/
package de.ralfebert.rcputils.handler;

import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.State;
import org.eclipse.jface.menus.IMenuStateIds;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.commands.IElementUpdater;
import org.eclipse.ui.menus.UIElement;

/**
 * Use this handler for style="toggle" command contributions. You need to
 * declare a state for your command to use ToggleHandler:
 * 
 * <pre>
 * &lt;command id=&quot;somecommand&quot; name=&quot;SomeCommand&quot;&gt;
 * 	 &lt;state class=&quot;org.eclipse.jface.commands.ToggleState&quot; id=&quot;STYLE&quot;/&gt;
 * &lt;/command&gt;
 * </pre>
 * 
 * The id="STYLE" was chosen because of IMenuStateIds.STYLE - maybe this will
 * work without any Handler foo in later Eclipse versions.
 * 
 * See http://www.ralfebert.de/blog/eclipsercp/togglehandler/
 * http://eclipsesource.com/blogs/2009/01/15/toggling-a-command-contribution/
 * 
 * @author Ralf Ebert
 */
public abstract class ToggleHandler extends AbstractHandler implements IElementUpdater {

	private String commandId;

	public final Object execute(ExecutionEvent event) throws ExecutionException {
		ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		this.commandId = event.getCommand().getId();

		// update toggled state
		State state = event.getCommand().getState(IMenuStateIds.STYLE);
		if (state == null)
			throw new ExecutionException(
					"You need to declare a ToggleState with id=STYLE for your command to use ToggleHandler!");
		boolean currentState = (Boolean) state.getValue();
		boolean newState = !currentState;
		state.setValue(newState);

		// trigger element update
		executeToggle(event, newState);
		commandService.refreshElements(event.getCommand().getId(), null);

		// return value is reserved for future apis
		return null;
	}

	protected abstract void executeToggle(ExecutionEvent event, boolean checked);

	/**
	 * Update command element with toggle state
	 */
	@SuppressWarnings("unchecked")
	public void updateElement(UIElement element, Map parameters) {
		if (this.commandId != null) {
			ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(
					ICommandService.class);
			Command command = commandService.getCommand(commandId);
			State state = command.getState(IMenuStateIds.STYLE);
			if (state != null)
				element.setChecked((Boolean) state.getValue());
		}
	}

}