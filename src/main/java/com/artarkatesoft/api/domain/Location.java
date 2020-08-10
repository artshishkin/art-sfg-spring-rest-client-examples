
package com.artarkatesoft.api.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Location implements Serializable
{

    private String street;
    private String city;
    private String state;
    private String postcode;
    private final static long serialVersionUID = -2256410565115705055L;
}
