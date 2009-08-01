package de.ralfebert.rcputils.properties;

/**
 * An IValueFormatter describes a conversion for a value in both directions.
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
public interface IValueFormatter<FROM, TO> {

	public TO format(FROM obj);

	public FROM parse(TO obj);

}
