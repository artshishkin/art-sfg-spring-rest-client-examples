
package com.artarkatesoft.api.domain;

import java.io.Serializable;

public class Location implements Serializable
{

    private String street;
    private String city;
    private String state;
    private String postcode;
    private final static long serialVersionUID = -2256410565115705055L;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

}
