package com.poc.chatty.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jonregalado on 1/16/18.
 */

public class Result implements Serializable
{
    
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("state")
    @Expose
    private State state;
    private final static long serialVersionUID = -4208720624672601931L;
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public State getState() {
        return state;
    }
    
    public void setState(State state) {
        this.state = state;
    }
}