package org.eclipselabs.commons.rcp.tables;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipselabs.commons.rcp.tables.TableViewerBuilder;
import org.junit.Before;
import org.junit.Test;

public class TableViewerBuilderTest {

	private Table table;

	class Row {
		private final int id;

		public Row(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}

	@Before
	public void setUp() throws Exception {
		Shell shell = new Shell();
		TableViewerBuilder tb = new TableViewerBuilder(shell);
		tb.createColumn("ID").bindToProperty("id").useAsDefaultSortColumn().build();
		tb.setInput(Arrays.asList(new Row(1), new Row(2), new Row(3)));

		table = tb.getTable();
	}

	@Test
	public void testColumns() {
		assertEquals(1, table.getColumnCount());
		assertEquals("ID", table.getColumn(0).getText());
	}

	@Test
	public void testSort() {
		assertEquals(table.getColumn(0), table.getSortColumn());
		assertEquals(SWT.UP, table.getSortDirection());
	}
}
