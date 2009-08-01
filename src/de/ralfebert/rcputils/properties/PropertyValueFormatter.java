package de.ralfebert.rcputils.properties;

import de.ralfebert.rcputils.internal.RcpUtilsPlugin;
import de.ralfebert.rcputils.properties.internal.PropertyValue;

/**
 * LabelProvider is a LabelProvider that gets labels using a bean property (can
 * be nested with ".")
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
@SuppressWarnings("unchecked")
public class PropertyValueFormatter implements IValueFormatter {

	private final IValue valueHandler;

	public PropertyValueFormatter(String propertyName) {
		this.valueHandler = new PropertyValue(propertyName);
	}

	public Object format(Object obj) {
		try {
			return valueHandler.getValue(obj);
		} catch (Exception e) {
			RcpUtilsPlugin.logException(e);
			return null;
		}
	}

	public Object parse(Object obj) {
		return obj;
	}
}
