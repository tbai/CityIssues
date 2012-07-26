package com.cityissues.models
import com.cityissues.models.sec.SecUser

class User extends SecUser{
    String email
    String name
    String activationToken
    String recoverToken

    static hasMany = [issues:Issue]
    static constraints = {
        name nullable:true, blank:false
        email email:true, unique:true, blank:false
        activationToken  nullable:true, blank:false
        recoverToken  nullable:true, blank:false
    }
}
