
package com.artarkatesoft.api.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExpirationDate implements Serializable
{

    private String date;
    private Integer timezoneType;
    private String timezone;
    private final static long serialVersionUID = 2549450060707957210L;

}
