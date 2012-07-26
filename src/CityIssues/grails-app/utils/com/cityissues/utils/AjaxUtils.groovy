/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cityissues.utils

/**
 *
 * @author tbai
 */
class AjaxUtils {
	def static toJsonErrors(allErrors){
        def jsonErrors = [:]
        allErrors.collect{
            jsonErrors."${it.field}" = SpringUtils.getMessage(it)
        }
        jsonErrors
    }
}

