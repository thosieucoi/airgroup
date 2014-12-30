package com.airgroup.util;

import org.hibernate.dialect.MySQLInnoDBDialect;

public class MySQLUTF8InnoDBDialect extends MySQLInnoDBDialect {
	@Override
	public String getTableTypeString() {
		return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
	}
}