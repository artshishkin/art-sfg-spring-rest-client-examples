
package com.artarkatesoft.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class Card implements Serializable
{

    private String type;
    private String number;
    @JsonProperty("expiration_date")
    private ExpirationDate expirationDate;
    private String iban;
    private String swift;
    private final static long serialVersionUID = -4704514103222500532L;

}
