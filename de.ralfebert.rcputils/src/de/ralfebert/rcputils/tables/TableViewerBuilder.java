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
package de.ralfebert.rcputils.tables;

import java.util.Collection;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Table;

import de.ralfebert.rcputils.tables.sort.ColumnSortSelectionListener;
import de.ralfebert.rcputils.tables.sort.SortColumnComparator;

/**
 * A convenient builder class for creating TableViewers with support for nested
 * properties, sorting and editing.
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 * @see http://www.ralfebert.de/blog/eclipsercp/tableviewerbuilder/
 */
public class TableViewerBuilder {

	private final TableViewer tableViewer;
	private final Table table;
	private final SelectionListener sortSelectionListener;

	/**
	 * Creates a new TableViewerBuilder.
	 * 
	 * This instantly creates a Table widget and a TableViewer. The given parent
	 * Composite needs to be empty, because `TableColumnLayout` is used
	 * internally.
	 */
	public TableViewerBuilder(Composite parent, int style) {
		// check parent
		if (parent.getChildren().length > 0) {
			throw new RuntimeException("The parent composite for the table needs to be empty for TableColumnLayout.");
		}

		this.tableViewer = new TableViewer(parent, style);
		this.table = tableViewer.getTable();

		// set TableColumnLayout to table parent
		this.table.getParent().setLayout(new TableColumnLayout());

		// headers / lines visible by default
		this.table.setHeaderVisible(true);
		this.table.setLinesVisible(true);

		// sorting
		this.sortSelectionListener = new ColumnSortSelectionListener(this.tableViewer);
		tableViewer.setComparator(new SortColumnComparator());
	}

	/**
	 * Creates a new TableViewerBuilder with default SWT styles.
	 */
	public TableViewerBuilder(Composite parent) {
		this(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
	}

	/**
	 * Creates a new ColumnBuilder that can be used to configure the table
	 * column. When you have finished configuring the column, call build() on
	 * the ColumnBuilder to create the actual column.
	 */
	public ColumnBuilder createColumn(String columnHeaderText) {
		return new ColumnBuilder(this, columnHeaderText);
	}

	/**
	 * Sets the given collection as input object and an
	 * {@link ArrayContentProvider} as content provider for the
	 * {@link TableViewer}.
	 */
	public void setInput(Collection<?> input) {
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setInput(input);
	}

	/**
	 * Returns the JFace TableViewer.
	 */
	public TableViewer getTableViewer() {
		return tableViewer;
	}

	/**
	 * Returns the SWT Table.
	 */
	public Table getTable() {
		return table;
	}

	TableColumnLayout getTableLayout() {
		Layout layout = table.getParent().getLayout();
		Assert.isTrue(layout instanceof TableColumnLayout, "Table parent layout needs to be a TableColumnLayout!");
		return (TableColumnLayout) layout;
	}

	SelectionListener getSortSelectionListener() {
		return sortSelectionListener;
	}

}