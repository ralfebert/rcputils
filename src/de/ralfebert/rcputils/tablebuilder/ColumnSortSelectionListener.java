package de.ralfebert.rcputils.tablebuilder;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public final class ColumnSortSelectionListener extends SelectionAdapter {

	private final TableViewer viewer;
	private final TableColumn column;

	public ColumnSortSelectionListener(TableViewer viewer, TableColumn column) {
		this.viewer = viewer;
		this.column = column;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
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