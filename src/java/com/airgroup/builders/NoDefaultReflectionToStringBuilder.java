package com.airgroup.builders;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class NoDefaultReflectionToStringBuilder extends
		ReflectionToStringBuilder {
	public NoDefaultReflectionToStringBuilder(Object object) {
		super(object);
	}

	protected void appendFieldsIn(Class<?> clazz) {
		if (clazz.isArray()) {
			reflectionAppendArray(getObject());
			return;
		}
		Field[] fields = clazz.getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);
		for (Field field : fields) {
			String fieldName = field.getName();
			if (!(accept(field))) {
				continue;
			}

			try {
				Object fieldValue = getValue(field);

				if (fieldValue != null) {
					if ((!(fieldValue instanceof Number))
							|| (((Number) fieldValue).intValue() != 0)) {
						if ((!(fieldValue instanceof Boolean))
								|| (((Boolean) fieldValue).booleanValue())) {
							append(fieldName, fieldValue);
						}
					}
				}
			} catch (IllegalAccessException ex) {
				throw new InternalError("Unexpected IllegalAccessException: "
						+ ex.getMessage());
			}
		}
	}
}
