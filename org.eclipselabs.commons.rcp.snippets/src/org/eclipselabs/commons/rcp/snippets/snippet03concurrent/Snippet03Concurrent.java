package org.eclipselabs.commons.rcp.snippets.snippet03concurrent;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.layout.RowLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipselabs.commons.rcp.concurrent.UIProcess;

public class Snippet03Concurrent extends ViewPart {

	private Button runUIProcessButton;

	@Override
	public void createPartControl(Composite parent) {
		RowLayoutFactory.fillDefaults().applyTo(parent);

		runUIProcessButton = new Button(parent, SWT.NONE);
		runUIProcessButton.setText("Run UIProcess");

		runUIProcessButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				runUIProcess();
			}
		});

	}

	private void runUIProcess() {
		new UIProcess(runUIProcessButton.getDisplay(), "Calculating") {

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
				runUIProcessButton.setText(String.valueOf(value));
			}

		}.schedule();
	}

	@Override
	public void setFocus() {
	}

}
