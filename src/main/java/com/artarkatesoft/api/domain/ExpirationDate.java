
package com.artarkatesoft.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExpirationDate implements Serializable
{

    private String date;
    @JsonProperty("timezone_type")
    private Integer timezoneType;
    private String timezone;
    private final static long serialVersionUID = 2549450060707957210L;

}
