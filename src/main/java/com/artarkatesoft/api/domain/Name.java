
package com.artarkatesoft.api.domain;

import lombok.Data;

import java.io.Serializable;
@Data
public class Name implements Serializable
{

    private String title;
    private String first;
    private String last;
    private final static long serialVersionUID = 819114313060912305L;
}
