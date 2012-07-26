package com.cityissues.services
import javax.servlet.http.HttpSession
import org.springframework.web.context.request.RequestContextHolder
import com.cityissues.models.*
import com.cityissues.models.IssueStatus

class IssueService {

    static transactional = true

    def springSecurityService
    
    /**
     * @param dateStart List only issues younger then the provided date
     */
    def list(firstResultVal, maxResultsVal, filterType, filterDate, sortListBy, filterLocationType = null, filterLocationValue = null, dateStart = null){
        def now = new Date()
        def filterDateStart = null
        if(filterDate){
            use( [groovy.time.TimeCategory] ){
                switch(filterDate){
                    case "day":
                        filterDateStart = now -1.day
                        break;
                    case "month":
                        filterDateStart = now -30.days
                        break;
                    case "year":
                        filterDateStart = now -1.year
                        break;
                }
            }
        }
        
        try{
            dateStart = new Date(dateStart.toLong())
        } catch(Exception e){
            dateStart = null
        }
        def issues = Issue.withCriteria{
            if(firstResultVal >= 0 && maxResultsVal > 0){
                firstResult(firstResultVal)
                maxResults(maxResultsVal)
            }
            eq("status", IssueStatus.OPEN)
            if(filterLocationType && filterLocationValue){
                address {
                    eq(filterLocationType, filterLocationValue)
                }
            }
            type {
                if(filterType){
                    if( filterType.toLowerCase().equals("all") ){
                        eq("isOpenData", false)
                    } else {
                        inList("name", filterType.split(","))        
                    }
                }
            }
            and{
                or {
                    eq("scheduled", false)
                    and {
                        eq("scheduled", true)
                        le("dateStart", now)
                        ge("dateFinish", now)
                    }
                }
                if(filterDateStart){
                    ge("dateStart", filterDateStart)
                }
                if(dateStart){
                    gt("dateStart", dateStart)
                }
            }
            if(sortListBy?.equals("score")){
                order "totalVotes", "desc"
            } else {
                order "dateStart", "desc"
            }
        }
        
        issues
    }

    def createIssue(type, description, address, schedule = null, ownerUser = null){
        def result = [success:false]
        if(!springSecurityService.isLoggedIn() && ownerUser==null)
            return result
            
        def user = ownerUser ?: User.findByUsername(springSecurityService.principal?.username)

        if(description?.size() > 300){ // max char size
            description = description.substring(0,299);
        }
        
        def addressObj = address
        if( address instanceof Map ){
            addressObj = new Address(address)
        }

        def issue = new Issue(
            user:user,
            address: addressObj,
            description:description,
            type:IssueType.findByName(type)
        )

        if(schedule){
            issue.scheduled = true
            issue.dateStart = schedule.dateStart
            issue.dateFinish = schedule.dateFinish
        }

        if(!issue.save()){
            log.error "error saving issue: ${issue.errors.allErrors}"
            result.errors = issue.errors.allErrors
        } else {
            result.success = true
            result.issue = issue 
        }
        result
    }
    
    def deleteIssue(issueId){
        def result = [success:false]
        def user = User.findByUsername(springSecurityService.principal?.username)
        def issue = Issue.get(issueId)
        if(issue?.user?.id == user?.id){
            issue.status = IssueStatus.DELETED
            issue.dateFinish = new Date()
        
            if(!issue.save()){
                log.error "error saving issue: ${issue.errors.allErrors}"
                result.errors = issue.errors.allErrors
            } else {
                result.success = true
                result.issue = issue 
            }
        } else {
            log.warn "User is not the owner of the issue"
        }
        
        result
    }
    
    def toJson(issue) {
        def desc =  issue.description.size() <= 50 ? issue.description: issue.description.substring(0, 50) + "..."
        [
            id:issue.id
            //totalVotes:issue.totalVotes,
            , addr: [ lat: issue.address.lat, lng:issue.address.lng ]
            , desc: desc
            , type: (issue.type?.name) ?: "info"
            //,dateCreated:issue.dateCreated
        ]
    }

    def vote(issueId, boolean positive){
        def result = [success:true]

        def user = User.findByUsername(springSecurityService.principal.username)
        def issue = Issue.get(issueId)
        def vote = getVote(issue, user)

        if(!vote){
            vote = new IssueVote(user:user, issue:issue, positive:positive)
        } else {
            vote.positive = positive
        }

        if(!vote.save()){
            result.success = false
            result.errors = vote.errors.allErrors
        } else {
            result.issue = issue
        }

        // count number of votes for this issue
        def totalVotes = IssueVote.withCriteria{
            eq "issue", issue
            projections {
                groupProperty("positive")
                count("positive")
            }
        }?.collect {
            it[0] ? it[1] : -it[1]
        }?.sum()
        issue.totalVotes = totalVotes != null ? totalVotes: 0

        issue.save()

        result
    }

    /**
     * @return IssueVote
     */
    def getVote(issue, user=null){
        if(!springSecurityService.isLoggedIn() && user==null)
            return null

        if(!user)
            user = User.findByUsername(springSecurityService.principal.username)
        def vote = IssueVote.findByUserAndIssue(user, issue)
        vote
    }




    /* vote using session
    def vote(issueId, boolean positive){
        def result = [success:true]

        // create the session object
        def session = SpringUtils.getSession()
        if(!session.issueVotes){
            session.issueVotes = [:]
        }

        // get the issue
        def issue = Issue.get(issueId)
        result.issue = issue
        def voteVal = positive ? +1 : -1

        // if there is no vote or if there is a vote different from the previous one
        if(!session.issueVotes[issue.id] || session.issueVotes[issue.id].val != voteVal){
            session.issueVotes[issue.id] = [val:voteVal]
            issue.totalVotes += voteVal

            // save the vote info if logged in
            if (springSecurityService.isLoggedIn()) {
                def user = User.findByUsername(springSecurityService.principal.username)
                def vote = new IssueVote(user:user, issue:issue, positive:positive)
                vote.positive = positive
            }

            if(!issue.save()){
                result.success = false
                result.errors = vote.errors.allErrors
            }
        }

        result
    }

    def getVote(issue, user=null){
        def session = SpringUtils.getSession()
        if(session?.issueVotes)
            session?.issueVotes[issue.id]?.val
        else null
    }
    */
}
