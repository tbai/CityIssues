package com.cityissues.models

class Address{
    static belongsTo = [Issue, Broadcast]
    String formatted
    String city
    String neighborhood
    String state
    String country
    String number
    String cep
    String type
    double lat = 0
    double lng = 0

    static constraints = {
        formatted nullable:false, blank:false
        lat nullable:false
        lng nullable:false
        city nullable:true
        cep nullable:true
        number nullable:true
        neighborhood nullable:true
        state nullable:true
        country nullable:true
        type nullable:true
    }
}
