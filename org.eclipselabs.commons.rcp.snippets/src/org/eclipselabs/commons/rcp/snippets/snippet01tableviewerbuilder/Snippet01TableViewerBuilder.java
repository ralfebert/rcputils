package org.eclipselabs.commons.rcp.snippets.snippet01tableviewerbuilder;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.eclipselabs.commons.rcp.properties.BaseValue;
import org.eclipselabs.commons.rcp.random.RandomData;
import org.eclipselabs.commons.rcp.tables.ColumnBuilder;
import org.eclipselabs.commons.rcp.tables.ICellFormatter;
import org.eclipselabs.commons.rcp.tables.TableViewerBuilder;
import org.eclipselabs.commons.rcp.tables.format.Formatter;
import org.eclipselabs.commons.rcp.tables.format.StringValueFormatter;

public class Snippet01TableViewerBuilder extends ViewPart {

	private TableViewer tableViewer;

	@Override
	public void createPartControl(Composite parent) {

		TableViewerBuilder t = new TableViewerBuilder(parent);

		ColumnBuilder id = t.createColumn("ID");
		id.bindToProperty("id");
		id.setPixelWidth(50);
		id.build();

		ColumnBuilder city = t.createColumn("City");
		city.bindToProperty("name");
		city.setPercentWidth(60);
		city.useAsDefaultSortColumn();
		city.makeEditable();
		city.build();

		ColumnBuilder population = t.createColumn("Population");
		population.bindToProperty("stats.population");
		population.format(Formatter.forInt(new DecimalFormat("#,##0")));
		population.format(new ICellFormatter() {

			public void formatCell(ViewerCell cell, Object value) {
				int population = (Integer) value;
				int color = (population > 5000000) ? SWT.COLOR_RED : SWT.COLOR_BLACK;
				cell.setForeground(cell.getControl().getDisplay().getSystemColor(color));
			}

		});
		population.alignRight();
		population.makeEditable(Formatter.forInt());
		population.build();

		ColumnBuilder area = t.createColumn("Area");
		area.bindToProperty("stats.areaKm2");
		area.alignRight();
		area.format(Formatter.forDouble(new DecimalFormat("0.00 km²")));
		area.makeEditable(Formatter.forDouble(new DecimalFormat("0.00")));
		area.build();

		ColumnBuilder density = t.createColumn("People/km²");
		density.bindToValue(new BaseValue<City>() {
			@Override
			public Object get(City city) {
				return city.getStats().getPopulation() / city.getStats().getAreaKm2();
			}
		});
		density.format(Formatter.forDouble(new DecimalFormat("0")));
		density.alignRight();
		density.build();

		ColumnBuilder foundingDate = t.createColumn("Founding date");
		foundingDate.bindToProperty("foundingDate");
		StringValueFormatter dateFormat = Formatter.forDate(SimpleDateFormat.getDateInstance(SimpleDateFormat.MEDIUM));
		foundingDate.format(dateFormat);
		foundingDate.alignCenter();
		foundingDate.setPixelWidth(100);
		foundingDate.makeEditable(dateFormat);
		foundingDate.build();

		ColumnBuilder neighborCity = t.createColumn("Neighbor city");
		neighborCity.bindToProperty("neighborCity");
		neighborCity.setPixelWidth(100);
		ComboBoxViewerCellEditor cityComboEditor = new ComboBoxViewerCellEditor(t.getTable(), SWT.READ_ONLY);
		cityComboEditor.setContenProvider(new ArrayContentProvider());
		cityComboEditor.setLabelProvider(new LabelProvider());
		cityComboEditor.setInput(RandomData.CITIES);
		neighborCity.makeEditable(cityComboEditor);
		neighborCity.build();

		t.setInput(createSomeData());
		tableViewer = t.getTableViewer();

	}

	private List<City> createSomeData() {
		List<City> data = new ArrayList<City>();
		RandomData randomData = new RandomData();
		for (int id = 1; id <= 50000; id++) {
			CityStats stats = new CityStats(randomData.someNumber(10000, 10000000), randomData.someNumber(100d, 800d));
			data.add(new City(id, randomData.someCity(), randomData.someDate(1200, 1600), stats, randomData.someCity()));
			randomData.newData();
		}
		return data;
	}

	@Override
	public void setFocus() {
		tableViewer.getTable().setFocus();
	}

}