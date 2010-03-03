package de.ralfebert.rcputils.selection;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.junit.Test;

public class SelectionUtilsTest {

	@Test
	public void testGetIterableEmpty() {
		assertSelection(null, String.class);
		assertSelection(new StructuredSelection(), String.class);
	}

	@Test
	public void testGetIterableElementType() {
		StructuredSelection selection = new StructuredSelection(new Object[] { "a", 1, "b" });
		assertSelection(selection, String.class, "a", "b");
		assertSelection(selection, Integer.class, 1);
	}

	private void assertSelection(ISelection selection, Class<?> elementType, Object... expectedElements) {
		assertEquals(Arrays.asList(expectedElements), getIterableContents(SelectionUtils.getIterable(selection,
				elementType)));
	}

	private <E> List<E> getIterableContents(Iterable<E> iterable) {
		List<E> result = new ArrayList<E>();
		for (E e : iterable) {
			result.add(e);
		}
		return result;
	}

}
