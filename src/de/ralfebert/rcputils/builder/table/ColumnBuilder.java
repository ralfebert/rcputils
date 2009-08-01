package de.ralfebert.rcputils.builder.table;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;

import de.ralfebert.rcputils.builder.table.format.StringValueFormatter;
import de.ralfebert.rcputils.builder.table.sorting.ColumnComparator;
import de.ralfebert.rcputils.properties.IValue;
import de.ralfebert.rcputils.properties.IValueFormatter;
import de.ralfebert.rcputils.properties.PropertyCellLabelProvider;
import de.ralfebert.rcputils.properties.PropertyEditingSupport;
import de.ralfebert.rcputils.properties.internal.PropertyValue;

@SuppressWarnings("unchecked")
public class ColumnBuilder {

	private final TableViewerBuilder builder;
	private final String label;
	private Integer widthPixel;
	private Integer widthPercent;
	private CellEditor editor;
	private ICellFormatter cellFormatter;
	private CellLabelProvider customLabelProvider;
	private IValue valueHandler;
	private IValueFormatter valueFormatter;
	private int align = SWT.LEFT;
	private IValue sortBy;
	private boolean defaultSort;
	private IValueFormatter editorFormat;

	ColumnBuilder(TableViewerBuilder builder, String label) {
		this.builder = builder;
		this.label = label;
	}

	public ColumnBuilder bindToProperty(String propertyName) {
		return bindToValue(new PropertyValue(propertyName));
	}

	public ColumnBuilder bindToValue(IValue valueHandler) {
		this.valueHandler = valueHandler;
		return this;
	}

	public ColumnBuilder format(ICellFormatter cellFormatter) {
		this.cellFormatter = cellFormatter;
		return this;
	}

	public ColumnBuilder format(IValueFormatter valueFormatter) {
		this.valueFormatter = valueFormatter;
		return this;
	}

	public ColumnBuilder setPercentWidth(int width) {
		this.widthPercent = width;
		return this;
	}

	public ColumnBuilder setPixelWidth(int width) {
		this.widthPixel = width;
		return this;
	}

	public ColumnBuilder alignCenter() {
		this.align = SWT.CENTER;
		return this;
	}

	public ColumnBuilder alignRight() {
		this.align = SWT.RIGHT;
		return this;
	}

	public ColumnBuilder makeEditable() {
		return makeEditable(new TextCellEditor(builder.getTable()), StringValueFormatter.INSTANCE);
	}

	public ColumnBuilder makeEditable(IValueFormatter valueFormatter) {
		return makeEditable(new TextCellEditor(builder.getTable()), valueFormatter);
	}

	public ColumnBuilder makeEditable(CellEditor cellEditor) {
		return makeEditable(cellEditor, null);
	}

	public ColumnBuilder makeEditable(CellEditor cellEditor, IValueFormatter valueFormatter) {
		if (cellEditor.getControl().getParent() != builder.getTable())
			throw new RuntimeException("Parent of cell editor needs to be the table!");
		this.editor = cellEditor;
		this.editorFormat = valueFormatter;
		return this;
	}

	public ColumnBuilder sortBy(IValue sortBy) {
		this.sortBy = sortBy;
		return this;
	}

	public ColumnBuilder setCustomLabelProvider(CellLabelProvider customLabelProvider) {
		this.customLabelProvider = customLabelProvider;
		return this;
	}

	public ColumnBuilder useAsDefaultSortColumn() {
		this.defaultSort = true;
		return this;
	}

	public TableViewerColumn build() {
		TableViewerColumn viewerColumn = new TableViewerColumn(builder.getTableViewer(), align);
		viewerColumn.getColumn().setText(label);

		if (customLabelProvider != null) {
			if (cellFormatter != null)
				throw new RuntimeException(
						"If you specify a custom label provider, it is not allowed to specify a cell formatter - you need to do the formatting in your labelprovider!");
			viewerColumn.setLabelProvider(customLabelProvider);
		} else {
			viewerColumn.setLabelProvider(new PropertyCellLabelProvider(valueHandler, valueFormatter, cellFormatter));
		}

		if (sortBy == null) {
			sortBy = valueHandler;
		}

		if (sortBy != null) {
			viewerColumn.getColumn().setData(ColumnComparator.SORT_BY, sortBy);
		}

		if (widthPixel != null && widthPercent != null) {
			throw new RuntimeException("You can specify a width in pixel OR in percent, but not both!");
		}

		if (widthPercent == null) {
			// default width if nothing is specified
			if (widthPixel == null)
				widthPixel = 100;
			builder.getTableLayout().setColumnData(viewerColumn.getColumn(), new ColumnPixelData(widthPixel));
		} else {
			builder.getTableLayout().setColumnData(viewerColumn.getColumn(), new ColumnWeightData(widthPercent));
		}

		if (editor != null) {
			if (valueHandler == null) {
				throw new RuntimeException(
						"To use makeEditable() you need to specifiy a value provider or bind the column to a property!");
			}

			viewerColumn.setEditingSupport(new PropertyEditingSupport(builder.getTableViewer(), valueHandler,
					editorFormat, editor));
		}

		if (defaultSort) {
			builder.getTable().setSortColumn(viewerColumn.getColumn());
			builder.getTable().setSortDirection(SWT.UP);
		}

		return viewerColumn;
	}

}
