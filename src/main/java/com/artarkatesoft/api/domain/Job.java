
package com.artarkatesoft.api.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Job implements Serializable
{

    private String title;
    private String company;
    private final static long serialVersionUID = -3389805678789129000L;

}
