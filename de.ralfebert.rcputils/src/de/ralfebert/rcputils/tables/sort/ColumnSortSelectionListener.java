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
package de.ralfebert.rcputils.tables.sort;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * ColumnSortSelectionListener is a selection listener for {@link TableColumn}
 * objects. When a column is selected (= header is clicked), it switches the
 * sort direction if the column is already active sort column, otherwise it sets
 * the active sort column.
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
public final class ColumnSortSelectionListener extends SelectionAdapter {

	private final TableViewer viewer;

	public ColumnSortSelectionListener(TableViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		TableColumn column = (TableColumn) e.getSource();
		Table table = column.getParent();
		boolean alreadyActiveSortColumn = (column == table.getSortColumn());
		if (alreadyActiveSortColumn) {
			table.setSortDirection(table.getSortDirection() == SWT.DOWN ? SWT.UP : SWT.DOWN);
		} else {
			table.setSortColumn(column);
			table.setSortDirection(SWT.UP);
		}
		viewer.refresh();
	}

}