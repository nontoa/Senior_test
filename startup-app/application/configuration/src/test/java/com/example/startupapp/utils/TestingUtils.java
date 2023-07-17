package com.example.startupapp.utils;

import java.lang.reflect.Field;

/**
 * Utilities for testing.
 *
 * @author Nicolas Nontoa
 * @version 1.0.0
 */
public class TestingUtils {

	/**
	 * Returns the value of the specified inaccessible field.
	 *
	 * @param object object with the inaccessible field to be returned.
	 * @param fieldName field name to get.
	 * @throws NoSuchFieldException if a field with the specified name is not found.
	 * @throws IllegalAccessException if the specified object is not an instance of the class or interface declaring
	 * the underlying field (or a subclass or implementor thereof), or if an unwrapping conversion fails.
	 */
	public static Object getPrivateFieldValue(final Object object, final String fieldName)
			throws NoSuchFieldException, IllegalAccessException {

		return getAndMakeAccessibleField(object, fieldName).get(object);

	}

	private static Field getAndMakeAccessibleField(final Object object, final String fieldName)
			throws NoSuchFieldException {

		Field field = object.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field;

	}

}
