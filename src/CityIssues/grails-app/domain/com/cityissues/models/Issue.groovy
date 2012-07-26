package com.cityissues.models

import com.cityissues.models.IssueStatus

class Issue {
    String description
    Date dateResolved
    Date dateCreated

    Date dateStart
    Date dateFinish

    boolean scheduled = false

    IssueType type
    IssueStatus status = IssueStatus.OPEN
    Address address
    int totalVotes = 0
    String name

    static hasMany = [votes:IssueVote]

    static belongsTo = [user:User]
    static constraints = {
        type            nullable:true
        description     blank:false, maxSize:300
        address()
        totalVotes      nullable:false
        dateResolved    nullable:true
        dateStart       nullable:true
        dateFinish      nullable:true
        name            nullable:true
        
    }

    def beforeInsert() {
        if(!dateStart) {
            dateStart = new Date()
        }
   }
}
