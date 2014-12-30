dataSource {
	pooled = true
	driverClassName = "com.mysql.jdbc.Driver"
	dialect = "com.airgroup.util.MySQLUTF8InnoDBDialect"
	username = "root"
	password = "123456"
}
hibernate {
	cache.use_second_level_cache = true
	cache.use_query_cache = true
	cache.provider_class = "net.sf.ehcache.hibernate.EhCacheProvider"
}

// environment specific settings
environments {
	development {
		dataSource {
			username = "root"
			password = "123456"
			dbCreate = "update" // one of 'create', 'create-drop','update'
			url = "jdbc:mysql://localhost:3306/airgroup?useUnicode=yes&characterEncoding=UTF-8"
		}
	}
	test {
		dataSource {
			dbCreate = "update"
			url = "jdbc:mysql://localhost:3306/airgroup_test?useUnicode=yes&characterEncoding=UTF-8"
		}
	}
	production {
		dataSource {
			dbCreate = "update"
			url = "jdbc:mysql://localhost:3306/airgroup?useUnicode=yes&characterEncoding=UTF-8"
			pooled = true
			username = "root"
			password = "airgroup"
		}
	}
}