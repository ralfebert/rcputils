package de.ralfebert.rcputils.snippets.snippet03concurrent;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.ralfebert.rcputils.concurrent.UIProcess;

public class Snippet03Concurrent extends ViewPart {

	private Button runJobButton;

	@Override
	public void createPartControl(Composite parent) {

		runJobButton = new Button(parent, SWT.NONE);
		runJobButton.setText("Run job");

		runJobButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				runJob();
			}
		});

	}

	private void runJob() {
		new UIProcess(runJobButton.getDisplay(), "Calculating") {

			double value = 42;

			@Override
			protected void runInBackground(IProgressMonitor monitor) {
				int totalWork = 8000;
				monitor.beginTask("Calculating", totalWork);
				for (int i = 0; i < totalWork; i++) {
					for (int j = 0; j < totalWork; j++) {
						value = Math.exp(Math.sin(value + j));
					}
					monitor.worked(1);
				}
			}

			@Override
			protected void runInUIThread() {
				runJobButton.setText(String.valueOf(value));
			}

		}.schedule();
	}

	@Override
	public void setFocus() {
	}

}
