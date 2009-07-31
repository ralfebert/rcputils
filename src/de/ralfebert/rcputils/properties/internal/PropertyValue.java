package de.ralfebert.rcputils.properties.internal;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.internal.databinding.beans.BeanPropertyHelper;

import de.ralfebert.rcputils.properties.IValue;

/**
 * This class allows to use JFace Data Binding properties to get and set nested
 * property values without observing them.
 * 
 * WARNING: This uses internal data binding classes. This enhancement described
 * in https://bugs.eclipse.org/bugs/show_bug.cgi?id=285307 would allow to use
 * official API instead of this class.
 * 
 * @author Ralf Ebert <info@ralfebert.de>
 */
@SuppressWarnings("restriction")
public class PropertyValue implements IValue {

	private final Object[] properties;

	public PropertyValue(String propertyName) {
		properties = split(propertyName);

	}

	private static Object[] split(String propertyName) {
		if (propertyName.indexOf('.') == -1)
			return new Object[] { propertyName };
		List<String> propertyNames = new ArrayList<String>();
		int index;
		while ((index = propertyName.indexOf('.')) != -1) {
			propertyNames.add(propertyName.substring(0, index));
			propertyName = propertyName.substring(index + 1);
		}
		propertyNames.add(propertyName);
		return propertyNames.toArray(new Object[propertyNames.size()]);
	}

	public Object getValue(Object object) {
		Object currentObject = object;
		for (int i = 0; i < properties.length; i++) {
			if (currentObject == null)
				return null;

			if (properties[i] instanceof String) {
				properties[i] = BeanPropertyHelper.getPropertyDescriptor(currentObject.getClass(),
						(String) properties[i]);
			}

			if (properties[i] instanceof PropertyDescriptor) {
				currentObject = BeanPropertyHelper.readProperty(currentObject, (PropertyDescriptor) properties[i]);
			} else {
				throw new RuntimeException("Invalid property: " + properties[i]);
			}

		}

		return currentObject;
	}

	public void setValue(Object object, Object value) {
		Object currentObject = object;
		for (int i = 0; i < properties.length; i++) {
			if (currentObject == null)
				throw new RuntimeException("Value cannot be set because of null value in nested property!");

			if (properties[i] instanceof String) {
				properties[i] = BeanPropertyHelper.getPropertyDescriptor(currentObject.getClass(),
						(String) properties[i]);
			}

			if (properties[i] instanceof PropertyDescriptor) {
				boolean lastProperty = (i == properties.length - 1);
				if (lastProperty)
					BeanPropertyHelper.writeProperty(currentObject, (PropertyDescriptor) properties[i], value);
				else
					currentObject = BeanPropertyHelper.readProperty(currentObject, (PropertyDescriptor) properties[i]);
			} else {
				throw new RuntimeException("Invalid property: " + properties[i]);
			}

		}
	}
}
