package com.cityissues.controllers

import com.cityissues.models.*

class BroadcastController {

    static scaffold = Broadcast
    
    def addressService
    def broadcastService
    
    def save = {
        def model = params
        if(model.address){
            model.address = addressService.create(params.address)
        }
        def broadcastInstance = new Broadcast(params)
        if (broadcastInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'broadcast.label', default: 'Broadcast'), broadcastInstance.id])}"
            redirect(action: "show", id: broadcastInstance.id)
        }
        else {
            render(view: "create", model: [broadcastInstance: broadcastInstance])
        }
    }
    
    def test = {
        def list = []
        def json = broadcastService.twitterRequest(params.query, null, 50)
        
        def broadcast = [addressRegex:params.addressRegex]

        json?.results.each{ twit->
            list.push ([text:twit.text, addressList:broadcastService.findAddress(twit.text, broadcast)])
        }
        
        [results:list]
    }
    
    def deleteIssues = {
        def broadcastInstance = Broadcast.get(params.id)
        if (broadcastInstance) {
            broadcastInstance.issues.collect{ issue -> {-> 
                broadcastInstance.removeFromIssues(issue)
                issue.delete()
            }}.each{ it() }
            flash.message = "${message(code: 'default.deleteIssues.message', args: [broadcastInstance.id])}"
            redirect(action: "show", id: broadcastInstance.id)
        } else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'broadcast.label', default: 'Broadcast'), params.id])}"
            redirect(action: "list")
        }
    }
    
    def update = {

        def broadcastInstance = Broadcast.get(params.id)
        if (broadcastInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (broadcastInstance.version > version) {
                    
                    broadcastInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'broadcast.label', default: 'Broadcast')] as Object[], "Another user has updated this Broadcast while you were editing")
                    render(view: "edit", model: [broadcastInstance: broadcastInstance])
                    return
                }
            }
            def model = params
            
            // create a new address if the current one is different
            def currAddr = broadcastInstance.address
            if(!model.address || model.address.trim().size() == 0){
                model.address = null
            } else if( !currAddr || !model.address.equalsIgnoreCase(currAddr.formatted)){
                model.address = addressService.create(params.address)
            } else {
                model.address = currAddr
            }
            
            broadcastInstance.properties = model
            if (!broadcastInstance.hasErrors() && broadcastInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'broadcast.label', default: 'Broadcast'), broadcastInstance.id])}"
                redirect(action: "show", id: broadcastInstance.id)
            }
            else {
                render(view: "edit", model: [broadcastInstance: broadcastInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'broadcast.label', default: 'Broadcast'), params.id])}"
            redirect(action: "list")
        }
    }
}
