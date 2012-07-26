jawr.js.mapping = "/script/"
jawr.css.mapping = "/style/"

jawr.css.bundle.names = "publicHelp,headerLinks,layoutBasicHeader,publicIndex,recoveryIndex,settingsIndex"
jawr.js.bundle.names  = "headerLinks,publicIndex,recoveryIndex,recoveryEdit,settingsIndex"

jawr.css.bundle.layoutBasicHeader.id = "/bundles/layoutBasicHeader.css"
jawr.css.bundle.layoutBasicHeader.mappings = '''
/js/yui_3.2.0/cssreset/reset-min.css,
/js/yui_3.2.0/cssbase/base-min.css,
/js/yui_3.2.0/cssfonts/fonts-min.css,
/js/yui_3.2.0/cssgrids/grids-min.css,
/js/jquery/ui/css/redmond-custom/jquery-ui-1.8.11.custom.css,
/css/layout/basicHeader.css
'''


jawr.css.bundle.headerLinks.id = "/bundles/headerLinks.css"
jawr.css.bundle.headerLinks.mappings = '''
/js/yui_3.2.0/cssreset/reset-min.css,
/js/yui_3.2.0/cssbase/base-min.css,
/js/yui_3.2.0/cssfonts/fonts-min.css,
/js/yui_3.2.0/cssgrids/grids-min.css,
/js/jquery/ui/css/redmond-custom/jquery-ui-1.8.11.custom.css,
/css/module/dialog.css,
/css/module/form.css,
/css/module/feedbackDialog.css,
/css/module/signInDialog.css,
/css/module/signUpDialog.css
'''
jawr.js.bundle.headerLinks.id = "/bundles/headerLinks.js"
jawr.js.bundle.headerLinks.mappings = '''
/js/jquery/jquery-1.4.4.js,
/js/jquery/ui/js/jquery-ui-1.8.11.custom.min.js,
/js/jquery/jquery.validate.js,
/js/jquery/jquery.cookie.js,
/js/architecture/bai.js,
/js/module/form.js,
/js/module/dialog.js,
/js/module/feedbackDialog.js,
/js/module/signInDialog.js,
/js/module/signUpDialog.js,
/js/view/headerLinks.js
'''

jawr.css.bundle.recoveryIndex.id = "/bundles/recoveryIndex.css"
jawr.css.bundle.recoveryIndex.dependencies="headerLinks"
jawr.css.bundle.recoveryIndex.mappings = '''
/css/view/recoveryIndex.css
'''
jawr.js.bundle.recoveryIndex.id = "/bundles/recoveryIndex.js"
jawr.js.bundle.recoveryIndex.dependencies="headerLinks"
jawr.js.bundle.recoveryIndex.mappings = '''
/js/view/recoveryIndex.js
'''

jawr.js.bundle.recoveryEdit.id = "/bundles/recoveryEdit.js"
jawr.js.bundle.recoveryEdit.dependencies="headerLinks"
jawr.js.bundle.recoveryEdit.mappings = '''
/js/view/recoveryEdit.js
'''

jawr.css.bundle.publicHelp.id = "/bundles/publicHelp.css"
jawr.css.bundle.publicHelp.dependencies="headerLinks"
jawr.css.bundle.publicHelp.mappings = '''
/css/view/publicHelp.css
'''

jawr.css.bundle.settingsIndex.id = "/bundles/settingsIndex.css"
jawr.css.bundle.settingsIndex.dependencies="headerLinks"
jawr.css.bundle.settingsIndex.mappings = '''
/css/view/settingsIndex.css
'''
jawr.js.bundle.settingsIndex.id = "/bundles/settingsIndex.js"
jawr.js.bundle.settingsIndex.dependencies="headerLinks"
jawr.js.bundle.settingsIndex.mappings = '''
/js/view/settingsIndex.js
'''

jawr.css.bundle.publicIndex.id = "/bundles/publicIndex.css"
jawr.css.bundle.publicIndex.dependencies="headerLinks"
jawr.css.bundle.publicIndex.mappings = '''
/js/yui_3.2.0/cssgrids/grids-min.css,
/css/module/issueListView.css,
/css/module/mapView.css,
/css/module/selectButton.css,
/css/module/infoWindow.css,
/css/module/createIssueDialog.css,
/css/module/selectAddressDialog.css,
/css/view/publicIndex.css
'''

jawr.js.bundle.publicIndex.id = "/bundles/publicIndex.js"
jawr.js.bundle.publicIndex.dependencies="headerLinks"
jawr.js.bundle.publicIndex.mappings = '''
/js/jquery/jquery.json-2.2.js,
/js/jquery/jquery.corner.js,
/js/architecture/mapUtils.js,
/js/markerclusterer/src/markerclusterer.js,
/js/module/issueListView.js,
/js/module/shortcutsView.js,
/js/module/mapView.js,
/js/module/selectButton.js,
/js/module/commentDialog.js,
/js/module/createIssueDialog.js,
/js/module/selectAddressDialog.js,
/js/view/publicIndex.js
'''
