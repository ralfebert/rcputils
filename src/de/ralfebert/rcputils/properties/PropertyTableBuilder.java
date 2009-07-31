package de.ralfebert.rcputils.properties;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class PropertyTableBuilder {

	private final class CellValueViewerComparator extends ViewerComparator {

		@SuppressWarnings("unchecked")
		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {

			if (table.getSortDirection() == SWT.UP) {
				Object tmp = e1;
				e1 = e2;
				e2 = tmp;
			}

			TableColumn column = table.getSortColumn();
			if (column == null)
				return 0;

			int columnIndex = ArrayUtils.indexOf(table.getColumns(), column);
			if (columnIndex < 0)
				return 0;

			CellLabelProvider labelProvider = tableViewer.getLabelProvider(columnIndex);

			if (labelProvider == null)
				return 0;

			if (labelProvider instanceof ICellValueProvider) {
				Object v1 = ((ICellValueProvider) labelProvider).getValue(e1);
				Object v2 = ((ICellValueProvider) labelProvider).getValue(e2);
				if (v1 instanceof Comparable && v2 instanceof Comparable) {
					return ((Comparable) v1).compareTo(v2);
				}
			}

			return super.compare(viewer, e1, e2);
		}
	}

	private final class ColumnSortSelectionListener extends SelectionAdapter {

		private final TableColumn column;

		private ColumnSortSelectionListener(TableColumn column) {
			this.column = column;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			boolean alreadyActiveSortColumn = (column == table.getSortColumn());
			if (alreadyActiveSortColumn) {
				table.setSortDirection(table.getSortDirection() == SWT.UP ? SWT.DOWN : SWT.UP);
			} else {
				table.setSortColumn(column);
				table.setSortDirection(SWT.DOWN);
			}
			tableViewer.refresh();
		}

	}

	private final TableViewer tableViewer;
	private final Table table;

	public PropertyTableBuilder(TableViewer tableViewer) {
		this.tableViewer = tableViewer;
		this.table = tableViewer.getTable();
	}

	public PropertyTableBuilder(Composite parent) {
		this.tableViewer = new TableViewer(parent);
		this.table = this.tableViewer.getTable();
	}

	public TableViewer getTableViewer() {
		return tableViewer;
	}

	public Table getTable() {
		return table;
	}

	public void activateSorting() {
		TableColumn[] columns = table.getColumns();
		for (final TableColumn column : columns) {
			column.addSelectionListener(new ColumnSortSelectionListener(column));
		}

		tableViewer.setComparator(new CellValueViewerComparator());
	}

	public void activateDefaults() {
		// set some reasonable defaults
		tableViewer.setContentProvider(new ArrayContentProvider());
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		activateSorting();
	}

}
