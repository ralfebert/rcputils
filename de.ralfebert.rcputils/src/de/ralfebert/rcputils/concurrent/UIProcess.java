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
package de.ralfebert.rcputils.concurrent;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.progress.UIJob;

/**
 * UIProcess is a Job base class which allows to update the UI after the job was
 * completed. Implement the "runInBackground" method with the long-running
 * operation to be executed in a background thread. Implement the
 * "runInUIThread" with the code to be executed after runInBackground has been
 * completed on the UI thread.
 * 
 * @author Ralf Ebert
 */
public abstract class UIProcess extends Job {

	private final class UpdateUiJob extends UIJob {

		private UpdateUiJob(Display jobDisplay, String name) {
			super(jobDisplay, name);
		}

		@Override
		public IStatus runInUIThread(IProgressMonitor monitor) {
			UIProcess.this.runInUIThread();
			return Status.OK_STATUS;
		}

	}

	private final Display display;

	public UIProcess(Display display, String jobName) {
		super(jobName);
		this.display = display;
	}

	@Override
	protected final IStatus run(IProgressMonitor monitor) {
		runInBackground(monitor);
		new UpdateUiJob(display, "Update UI for " + getName()).schedule();
		return Status.OK_STATUS;
	}

	/**
	 * @see Job#run(IProgressMonitor monitor)
	 */
	protected abstract void runInBackground(IProgressMonitor monitor);

	/**
	 * @see UIJob#runInUIThread(IProgressMonitor)
	 */
	protected abstract void runInUIThread();

}
