eventCompileEnd = {
    ant.copy(file: "${basedir}/grails-app/conf/Jawr.groovy", todir: classesDirPath)
    ant.copy(file: "${basedir}/grails-app/i18n/messages_pt_BR.properties", todir: classesDirPath+"/grails-app/i18n/")
}
