package com.cityissues.models

class IssueVote {
    boolean positive = true
    static belongsTo = [issue:Issue, user:User]
}
