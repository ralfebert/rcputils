package de.ralfebert.rcputils.builder.table;

import org.eclipse.jface.viewers.ViewerCell;

public interface ICellFormatter {

	public void formatCell(ViewerCell cell, Object value);

}
