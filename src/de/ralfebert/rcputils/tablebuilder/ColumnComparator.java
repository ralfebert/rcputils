package de.ralfebert.rcputils.tablebuilder;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import de.ralfebert.rcputils.properties.IReadableValue;

public final class ColumnComparator extends ViewerComparator {

	public static final String SORT_BY = ColumnComparator.class.getName() + ".sortBy";

	@SuppressWarnings("unchecked")
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {

		Table table = (Table) viewer.getControl();
		if (table.getSortDirection() == SWT.DOWN) {
			Object tmp = e1;
			e1 = e2;
			e2 = tmp;
		}

		TableColumn column = table.getSortColumn();
		if (column == null)
			return super.compare(viewer, e1, e2);

		IReadableValue sortBy = (IReadableValue) column.getData(SORT_BY);

		if (sortBy == null)
			return super.compare(viewer, e1, e2);

		Object v1 = sortBy.getValue(e1);
		Object v2 = sortBy.getValue(e2);
		if (v1 instanceof Comparable && v2 instanceof Comparable) {
			return ((Comparable) v1).compareTo(v2);
		}

		return super.compare(viewer, e1, e2);

	}
}