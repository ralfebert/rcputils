package de.ralfebert.rcputils.properties;

public abstract class BaseValue<T> implements IValue {

	@SuppressWarnings("unchecked")
	public final Object getValue(Object element) {
		return get((T) element);
	}

	@SuppressWarnings("unchecked")
	public void setValue(Object element, Object value) {
		set((T) element, value);
	}

	public abstract Object get(T element);

	public Object set(T element, Object value) {
		throw new UnsupportedOperationException("Overwrite set() in BaseValueHandler to set values!");
	}
}
