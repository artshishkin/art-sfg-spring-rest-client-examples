
package com.artarkatesoft.api.domain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Billing implements Serializable
{

    private Card card;
    private final static long serialVersionUID = 5988914043561963447L;

}
