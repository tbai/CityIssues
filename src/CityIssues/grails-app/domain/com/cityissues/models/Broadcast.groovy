package com.cityissues.models

import com.cityissues.models.*

class Broadcast {
    static hasMany = [issues:Issue]
    
    String name
    String type = "twitter"
    String query
    String sinceId
    String issueTypeName

    int maxResults = 20
    
    BroadcastMethod method = BroadcastMethod.ONE_FOR_FIRST_ADDRESS
    
    // Address info
    Address address
    
    Date lastUpdated
    
    String updatePeriod
    
    String lifeTime = "1.day"
    
    String addressRegex = /(((?i)(bairro|avenida|av\.?|rua|r\.?|estr\.?|estrada|(?<=na))(\.| )(?-i)((Dr|Cd|Visc|Eng)\. )?(d(a|o|e)s? )?))\p{Lu}\p{Ll}+( ((do|da|de) )?\p{Lu}\p{Ll}+)?( ((do|da|de) )?\p{Lu}\p{Ll}+)?|(?<=((esq|prox|próx)\.?( a| da| à)?|(esquina|com|com (a|à))) )((?i)(avenida|av|av\.|rua|r\.|estr\.|estrada)(\.| )((de|da|do)s? )?)?(?-i)\p{Lu}\p{Ll}+( ((do|da|de) )?\p{Lu}\p{Ll}+)?( ((do|da|de) )?\p{Lu}\p{Ll}+)?/
    
    def beforeUpdate() {
        lastUpdated = new Date()
    }

    static constraints = {
        name    nullable:false, blank:false, unique:true
        type    nullable:false, blank:false, inList:["twitter"]
        issueTypeName nullable:false, blank:false
        query   nullable:false, blanl:false
        
	maxResults nullable:false, min:1, max:100

        lastUpdated()
        
        method()
        
        sinceId nullable:true,  blank:false
        
        address nullable:true
        
        lifeTime nullable:false, blank:false, matches:/\d+\.(day|hour|minute|month|week|year|second)(s)?/
        
        updatePeriod nullable:true, blank:false
        
        addressRegex nullable:true, blank:false, maxSize:1000
    }
}
