package de.ralfebert.rcputils.builder.table;

import org.eclipse.jface.viewers.ViewerCell;

/**
 * An ICellFormatter is responsible for formatting a cell. Should be used to
 * apply additional formatting to the cell, like setting colors / images.
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
public interface ICellFormatter {

	public void formatCell(ViewerCell cell, Object value);

}
