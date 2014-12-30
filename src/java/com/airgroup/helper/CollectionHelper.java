package com.airgroup.helper;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.Properties;

public class CollectionHelper {
	public static Properties getProperties(String string) {
		Properties properties = new Properties();
		try {
			properties.load(new StringReader(string));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return properties;
	}

	public static boolean isPresent(Collection<? extends Object> objects) {
		return ((objects != null) && (objects.size() > 0));
	}

	public static boolean isEmpty(Collection<? extends Object> objects) {
		return ((objects == null) || (objects.size() == 0));
	}
}
