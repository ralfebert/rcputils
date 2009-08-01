package de.ralfebert.rcputils.properties;

public interface IValueFormatter<FROM, TO> {

	public TO format(FROM obj);

	public FROM parse(TO obj);

}
