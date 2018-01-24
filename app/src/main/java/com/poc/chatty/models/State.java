package com.poc.chatty.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jonregalado on 1/16/18.
 */

public class State implements Serializable
{
    
    @SerializedName("registrant")
    @Expose
    private Registrant registrant;
    private final static long serialVersionUID = -273326912553289342L;
    
    public Registrant getRegistrant() {
        return registrant;
    }
    
    public void setRegistrant(Registrant registrant) {
        this.registrant = registrant;
    }
}
