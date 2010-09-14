package org.eclipselabs.rcputils.selection;

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.google.common.collect.Iterators;

/**
 * Helper methods for working with JFace selections.
 */
public class SelectionUtils {

	/**
	 * Returns a list of all objects of selectedObjectClass contained in the
	 * given selection, for example:
	 * 
	 * <code>
	 * for(Car car : SelectionUtils.getIterable(carViewer, Car.class)) {
	 * 	// ...
	 * }
	 * </code>
	 */
	public static final <A> Iterable<A> getIterable(final ISelection selection, final Class<A> selectedObjectClass) {
		if (selection == null || selection.isEmpty() || !(selection instanceof IStructuredSelection))
			return Collections.emptyList();

		return new Iterable<A>() {
			public Iterator<A> iterator() {
				Iterator<?> iterator = ((IStructuredSelection) selection).iterator();
				return Iterators.filter(iterator, selectedObjectClass);
			}
		};
	}
}