package com.cityissues.controllers

import com.cityissues.models.*

class IssueTypeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [issueTypeInstanceList: IssueType.list(params), issueTypeInstanceTotal: IssueType.count()]
    }

    def create = {
        def issueTypeInstance = new IssueType()
        issueTypeInstance.properties = params
        return [issueTypeInstance: issueTypeInstance]
    }

    def save = {
        def issueTypeInstance = new IssueType(params)
        if (issueTypeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'issueType.label', default: 'IssueType'), issueTypeInstance.id])}"
            redirect(action: "show", id: issueTypeInstance.id)
        }
        else {
            render(view: "create", model: [issueTypeInstance: issueTypeInstance])
        }
    }

    def show = {
        def issueTypeInstance = IssueType.get(params.id)
        if (!issueTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'issueType.label', default: 'IssueType'), params.id])}"
            redirect(action: "list")
        }
        else {
            [issueTypeInstance: issueTypeInstance]
        }
    }

    def edit = {
        def issueTypeInstance = IssueType.get(params.id)
        if (!issueTypeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'issueType.label', default: 'IssueType'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [issueTypeInstance: issueTypeInstance]
        }
    }

    def update = {
        def issueTypeInstance = IssueType.get(params.id)
        if (issueTypeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (issueTypeInstance.version > version) {
                    
                    issueTypeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'issueType.label', default: 'IssueType')] as Object[], "Another user has updated this IssueType while you were editing")
                    render(view: "edit", model: [issueTypeInstance: issueTypeInstance])
                    return
                }
            }
            issueTypeInstance.properties = params
            if (!issueTypeInstance.hasErrors() && issueTypeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'issueType.label', default: 'IssueType'), issueTypeInstance.id])}"
                redirect(action: "show", id: issueTypeInstance.id)
            }
            else {
                render(view: "edit", model: [issueTypeInstance: issueTypeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'issueType.label', default: 'IssueType'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def issueTypeInstance = IssueType.get(params.id)
        if (issueTypeInstance) {
            try {
                issueTypeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'issueType.label', default: 'IssueType'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'issueType.label', default: 'IssueType'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'issueType.label', default: 'IssueType'), params.id])}"
            redirect(action: "list")
        }
    }
}
