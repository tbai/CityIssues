grails.project.class.dir = "./target/.classes"
grails.project.test.class.dir = "./target/.test-classes"
grails.project.test.reports.dir = "./target/test-reports"

//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        excludes 'ehcache', 'oscache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        mavenLocal()
        mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        compile "org.apache.ant:ant:1.7.1"

        runtime "org.springframework:org.springframework.test:3.0.3.RELEASE",
                'mysql:mysql-connector-java:5.1.5'

        //provided 'mysql:mysql-connector-java:5.1.5'
        //runtime 'mysql:mysql-connector-java:5.1.5'
    }
    plugins {
        compile ':mail:1.0-SNAPSHOT',
                ':asynchronous-mail:0.2.1-SNAPSHOT',
                ':quartz:0.4.2',
                ':rest:0.6.1'
    }
}
