package com.cityissues.controllers

import com.cityissues.models.*

class AddressController {

    static scaffold = Address
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def save = {
        def addressInstance = new Address(params)
        if (addressInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'address.label', default: 'Address'), addressInstance.id])}"
            redirect(action: "show", id: addressInstance.id)
        }
        else {
            render(view: "create", model: [addressInstance: addressInstance])
        }
    }

    def update = {
        def addressInstance = Address.get(params.id)
        if (addressInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (addressInstance.version > version) {
                    
                    addressInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'address.label', default: 'Address')] as Object[], "Another user has updated this Address while you were editing")
                    render(view: "edit", model: [addressInstance: addressInstance])
                    return
                }
            }
            addressInstance.properties = params
            if (!addressInstance.hasErrors() && addressInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'address.label', default: 'Address'), addressInstance.id])}"
                redirect(action: "show", id: addressInstance.id)
            }
            else {
                render(view: "edit", model: [addressInstance: addressInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'address.label', default: 'Address'), params.id])}"
            redirect(action: "list")
        }
    }
}
