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

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;

import de.ralfebert.rcputils.properties.IValue;
import de.ralfebert.rcputils.properties.IValueFormatter;
import de.ralfebert.rcputils.properties.PropertyCellLabelProvider;
import de.ralfebert.rcputils.properties.PropertyEditingSupport;
import de.ralfebert.rcputils.properties.PropertyValue;
import de.ralfebert.rcputils.tables.format.Formatter;
import de.ralfebert.rcputils.tables.format.StringValueFormatter;
import de.ralfebert.rcputils.tables.sort.SortColumnComparator;

/**
 * ColumnBuilder is responsible to build a column for {@link TableViewerBuilder}
 * Methods are chainable so you can construct table columns in a single line.
 * After customizing the column by calling methods, call build() once to create
 * the actual column.
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
@SuppressWarnings("unchecked")
public class ColumnBuilder {

	private final TableViewerBuilder builder;
	private final String columnHeaderText;
	private IValue valueHandler;
	private IValueFormatter valueFormatter;
	private ICellFormatter cellFormatter;
	private CellLabelProvider customLabelProvider;
	private Integer widthPixel;
	private Integer widthPercent;
	private int align = SWT.LEFT;
	private CellEditor editor;
	private IValue sortBy;
	private boolean defaultSort;
	private IValueFormatter editorFormat;

	ColumnBuilder(TableViewerBuilder builder, String columnHeaderText) {
		this.builder = builder;
		this.columnHeaderText = columnHeaderText;
	}

	/**
	 * Binds this column to the given property.
	 */
	public ColumnBuilder bindToProperty(String propertyName) {
		return bindToValue(new PropertyValue(propertyName));
	}

	/**
	 * Binds the column to an arbitrary value.
	 */
	public ColumnBuilder bindToValue(IValue valueHandler) {
		this.valueHandler = valueHandler;
		return this;
	}

	/**
	 * Sets a formatter for this column that is responsible to convert the value
	 * into a String. The 'parse' method of the CellFormatter is not required
	 * for this. See {@link Formatter} for commonly-used formatters.
	 */
	public ColumnBuilder format(IValueFormatter valueFormatter) {
		this.valueFormatter = valueFormatter;
		return this;
	}

	/**
	 * A cell formatter allows to format the cell besides the textual value, for
	 * example to customize colors or set images.
	 */
	public ColumnBuilder format(ICellFormatter cellFormatter) {
		this.cellFormatter = cellFormatter;
		return this;
	}

	/**
	 * If your column is not text based (for example a column with images that
	 * are owner-drawn), you can use a custom CellLabelProvider instead of a
	 * value and a value formatter.
	 */
	public ColumnBuilder setCustomLabelProvider(CellLabelProvider customLabelProvider) {
		this.customLabelProvider = customLabelProvider;
		return this;
	}

	/**
	 * Sets column width in percent
	 */
	public ColumnBuilder setPercentWidth(int width) {
		this.widthPercent = width;
		return this;
	}

	/**
	 * Sets column width in pixel
	 */
	public ColumnBuilder setPixelWidth(int width) {
		this.widthPixel = width;
		return this;
	}

	/**
	 * Sets alignment of column cell texts to be centered.
	 */
	public ColumnBuilder alignCenter() {
		this.align = SWT.CENTER;
		return this;
	}

	/**
	 * Sets alignment of column cell texts to be right-aligned.
	 */
	public ColumnBuilder alignRight() {
		this.align = SWT.RIGHT;
		return this;
	}

	/**
	 * Makes this column editable. Using this method you get a text editor
	 * without any formatting applied, to the value type needs to be String.
	 */
	public ColumnBuilder makeEditable() {
		return makeEditable(new TextCellEditor(builder.getTable()), StringValueFormatter.INSTANCE);
	}

	/**
	 * Makes this column editable. Using this method you get a text editor. The
	 * given valueFormatter will be responsible for formatting the value to a
	 * String and parsing it back to a new value.
	 */
	public ColumnBuilder makeEditable(IValueFormatter valueFormatter) {
		return makeEditable(new TextCellEditor(builder.getTable()), valueFormatter);
	}

	/**
	 * Makes the column cells editable using a custom cell editor. No formatting
	 * is applied, the editor will see the value as it is.
	 */
	public ColumnBuilder makeEditable(CellEditor cellEditor) {
		return makeEditable(cellEditor, null);
	}

	/**
	 * Makes the column cells editable using a custom cell editor. The given
	 * valueFormatter will be responsible for formatting the value for the
	 * editor and converting it back to a new value.
	 */
	public ColumnBuilder makeEditable(CellEditor cellEditor, IValueFormatter valueFormatter) {
		if (cellEditor.getControl().getParent() != builder.getTable())
			throw new RuntimeException("Parent of cell editor needs to be the table!");
		this.editor = cellEditor;
		this.editorFormat = valueFormatter;
		return this;
	}

	/**
	 * Sets a custom value to sort by. Implement yourself our use PropertyValue
	 * to sort by a custom property value.
	 */
	public ColumnBuilder sortBy(IValue sortBy) {
		this.sortBy = sortBy;
		return this;
	}

	/**
	 * Sets this column as default sort column
	 */
	public ColumnBuilder useAsDefaultSortColumn() {
		this.defaultSort = true;
		return this;
	}

	/**
	 * Builds the column and returns the TableViewerColumn
	 */
	public TableViewerColumn build() {
		// create column
		TableViewerColumn viewerColumn = new TableViewerColumn(builder.getTableViewer(), align);
		TableColumn column = viewerColumn.getColumn();
		column.setText(columnHeaderText);

		// set label provider
		if (customLabelProvider != null) {
			if (cellFormatter != null) {
				throw new RuntimeException("If you specify a custom label provider, it is not allowed "
						+ "to specify a cell formatter. You need to do the formatting in your labelprovider!");
			}
			viewerColumn.setLabelProvider(customLabelProvider);
		} else {
			viewerColumn.setLabelProvider(new PropertyCellLabelProvider(valueHandler, valueFormatter, cellFormatter));
		}

		// activate column sorting
		if (sortBy == null) {
			sortBy = valueHandler;
		}
		if (sortBy != null) {
			column.setData(SortColumnComparator.SORT_BY, sortBy);
			column.addSelectionListener(builder.getSortSelectionListener());
			if (defaultSort) {
				builder.getTable().setSortColumn(column);
				builder.getTable().setSortDirection(SWT.UP);
			}
		}

		// set column layout data
		if (widthPixel != null && widthPercent != null) {
			throw new RuntimeException("You can specify a width in pixel OR in percent, but not both!");
		}
		if (widthPercent == null) {
			// default width of 100px if nothing specified
			builder.getTableLayout().setColumnData(column, new ColumnPixelData(widthPixel == null ? 100 : widthPixel));
		} else {
			builder.getTableLayout().setColumnData(column, new ColumnWeightData(widthPercent));
		}

		// set editing support
		if (editor != null) {
			if (valueHandler == null) {
				throw new RuntimeException(
						"makeEditable() requires that the column is bound to some value using bindTo...()");
			}

			viewerColumn.setEditingSupport(new PropertyEditingSupport(builder.getTableViewer(), valueHandler,
					editorFormat, editor));
		}

		return viewerColumn;
	}
}
