package de.ralfebert.rcputils.builder.table;

import java.util.Collection;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import de.ralfebert.rcputils.builder.table.sorting.ColumnComparator;
import de.ralfebert.rcputils.builder.table.sorting.ColumnSortSelectionListener;


public class TableViewerBuilder {

	private final TableViewer tableViewer;
	private final Table table;

	TableViewerBuilder(TableViewer tableViewer) {
		this.tableViewer = tableViewer;
		this.table = tableViewer.getTable();

		if (this.table.getParent().getChildren().length != 1) {
			throw new RuntimeException(
					"The parent composite around the table may only contain the table itself - otherweise TableColumnLayout will not work");
		}

		this.table.getParent().setLayout(new TableColumnLayout());
	}

	public TableViewerBuilder(Composite parent, int style) {
		this(new TableViewer(parent, style));
	}

	public TableViewerBuilder(Composite parent) {
		this(new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION));
	}

	TableViewer getTableViewer() {
		return tableViewer;
	}

	Table getTable() {
		return table;
	}

	void activateSorting() {
		TableColumn[] columns = table.getColumns();
		for (final TableColumn column : columns) {
			column.addSelectionListener(new ColumnSortSelectionListener(tableViewer, column));
		}

		tableViewer.setComparator(new ColumnComparator());
	}

	public ColumnBuilder createColumn(String label) {
		return new ColumnBuilder(this, label);
	}

	TableColumnLayout getTableLayout() {
		Layout layout = table.getParent().getLayout();
		Assert.isTrue(layout instanceof TableColumnLayout, "Table parent layout needs to be a TableColumnLayout!");
		return (TableColumnLayout) layout;
	}

	public TableViewer build(Collection<?> input) {
		build();
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(input);

		return tableViewer;
	}

	public TableViewer build() {
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		activateSorting();
		return tableViewer;
	}

}