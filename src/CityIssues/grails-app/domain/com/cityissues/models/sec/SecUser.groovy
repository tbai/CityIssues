package com.cityissues.models.sec

class SecUser {

	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

    static hasMany = [openIds: OpenID]
        
	static constraints = {
		username blank: false, unique: true
		password blank: false, nullable:false
	}

	static mapping = {
		password column: '`password`'
	}

	Set<SecRole> getAuthorities() {
		SecUserSecRole.findAllBySecUser(this).collect { it.secRole } as Set
	}
}
