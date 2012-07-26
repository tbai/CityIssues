package com.cityissues.models.sec



class OpenID {

	String url

	static belongsTo = [user: SecUser]

	static constraints = {
		url unique: true
	}
}
