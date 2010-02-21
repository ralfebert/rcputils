package de.ralfebert.rcpsnippets.snippet02stocks;

import java.math.BigDecimal;
import java.util.Collection;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import de.ralfebert.rcputils.databinding.Realms;
import de.ralfebert.rcputils.random.RandomData;
import de.ralfebert.rcputils.tables.TableViewerBuilder;

public class Snippet02Stocks extends ViewPart {

	@Override
	public void createPartControl(Composite parent) {

		TableViewerBuilder t = new TableViewerBuilder(parent);

		t.createColumn("Stock").build();
		t.createColumn("Rate").alignRight().build();

		ViewerSupport.bind(t.getTableViewer(), createStocks(), BeanProperties.values(new String[] { "name", "rate" }));

	}

	private IObservableList createStocks() {
		final WritableList stocks = new WritableList(Realms.WHATEVER);

		final RandomData rand = new RandomData();
		for (int i = 0; i < 50; i++) {
			stocks.add(new Stock(rand.someCharacters(4, RandomData.UPPERCASE_CHARS), new BigDecimal(rand.someNumber(1d,
					200d))));
			rand.newData();
		}

		Job job = new Job("stock exchange") {

			@Override
			@SuppressWarnings("unchecked")
			protected IStatus run(IProgressMonitor monitor) {

				for (Stock stock : (Collection<Stock>) stocks) {
					rand.newData();
					stock.setRate(stock.getRate().add(
							new BigDecimal(rand.someNumber(-20, 20)).divide(new BigDecimal(100))));
				}

				this.schedule(200);

				return Status.OK_STATUS;
			}

		};

		job.schedule();

		return stocks;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
