package com.cityissues.models;

public enum BroadcastMethod {
    ONE_FOR_FIRST_ADDRESS("ONE_FOR_FIRST_ADDRESS"), 
    ONE_FOR_EACH_ADDRESS("ONE_FOR_EACH_ADDRESS"), 
    USE_ADDRESS("USE_ADDRESS");

    private final String id;

    BroadcastMethod(String id){
    	this.id = id;
    }

    public String getId() {
		return this.id;
	}
}
