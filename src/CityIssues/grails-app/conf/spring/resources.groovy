import grails.util.Environment
import org.codehaus.groovy.grails.commons.ConfigurationHolder

// Place your Spring DSL code here
beans = {
    localeResolver(org.springframework.web.servlet.i18n.SessionLocaleResolver) {
		defaultLocale = new Locale("pt", "BR")
		java.util.Locale.setDefault(defaultLocale)
	}
}
