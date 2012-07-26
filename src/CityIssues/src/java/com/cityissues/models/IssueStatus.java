package com.cityissues.models;

public enum IssueStatus {
    OPEN("OP"), RESOLVED("RE"), DELETED("DE");

    private final String id;

    IssueStatus(String id){
    	this.id = id;
    }

    public String getId() {
		return this.id;
	}
}