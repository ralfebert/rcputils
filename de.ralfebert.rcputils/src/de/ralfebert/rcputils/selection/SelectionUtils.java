package de.ralfebert.rcputils.selection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * Helper methods for working with JFace selections.
 */
public class SelectionUtils {

	/**
	 * Returns a list of all objects of selectedObjectClass contained in the
	 * given selection.
	 */
	public static final <A> Iterable<A> getIterable(ISelection selection,
			Class<A> selectedObjectClass) {
		if (selection.isEmpty())
			return Collections.emptyList();

		List<A> selectedObjects = new ArrayList<A>();
		if (selection instanceof IStructuredSelection) {
			Iterator<?> iterator = ((IStructuredSelection) selection)
					.iterator();
			while (iterator.hasNext()) {
				Object obj = iterator.next();
				if (selectedObjectClass.isAssignableFrom(obj.getClass())) {
					@SuppressWarnings("unchecked")
					A selectedObject = (A) obj;
					selectedObjects.add(selectedObject);
				}

			}
		}

		return selectedObjects;
	}
}