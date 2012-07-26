package com.cityissues.models

class IssueType {
    String name
    String description
    String icon // transformar em icon
    String label
    // will be displayed in the shortcut list
    boolean showShortcut = true

    boolean isOpenData = false
    
    // users can create new issues using this category
    boolean canCreate = true

    static hasMany = [issues:Issue]
    static constraints = {
        icon nullable:false
        name blank:false, unique:true
        description nullable:true
        label nullable:false, blank:false
        showShortcut()
        canCreate()
    }
}
