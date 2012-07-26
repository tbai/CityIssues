dataSource {
    pooled = true
    driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            pooled = true
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/suricato"
            driverClassName = "com.mysql.jdbc.Driver"
            username = "suricato"
            password = "senha"
        }
        /*
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            url = "jdbc:hsqldb:mem:devDB"
        }*/

    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:mem:testDb"
        }
    }
    production {
        dataSource {
            pooled = true
            dbCreate = "update"
            url = "jdbc:mysql://localhost:3306/suricato?autoReconnect=true"
            //url = "jdbc:mysql://ec2-67-202-5-236.compute-1.amazonaws.com/:3306/suricato"
            driverClassName = "com.mysql.jdbc.Driver"
            username = "suricato"
            password = "senha"
            // prevent broken pipe:
            // Error 500: Executing action [index] of controller [com.cityissues.controllers.PublicController] caused exception: Could not open Hibernate Session for transaction; nested exception is org.hibernate.TransactionException: JDBC begin failed:
            // referência: http://grails.1312388.n4.nabble.com/MySQL-and-Grails-Broken-pipe-after-4-hours-td3012633.html
            properties {
                validationQuery="select 1"
                testWhileIdle=true
                timeBetweenEvictionRunsMillis=60000
            }
        }
    }
    /*
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:file:prodDb;shutdown=true"
        }
    }

   production {
        dataSource {
            pooled = true
            driverClassName = "com.mysql.jdbc.Driver"
            dbCreate = "update" // one of 'create', 'create-drop','update'
            username = 'root'
            password = 'senha'
            url = 'jdbc:mysql://suricato.cltl2vyt8xkr.us-east-1.rds.amazonaws.com:3306/suricato'
            dialect = org.hibernate.dialect.MySQL5InnoDBDialect
            properties {
                validationQuery = "SELECT 1"
                testOnBorrow = true
                testOnReturn = true
                testWhileIdle = true
                timeBetweenEvictionRunsMillis = 1000 * 60 * 30
                numTestsPerEvictionRun = 3
                minEvictableIdleTimeMillis = 1000 * 60 * 30
            }
        }
    }*/

}
