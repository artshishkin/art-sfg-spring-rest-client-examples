
package com.artarkatesoft.api.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Login implements Serializable
{

    private String username;
    private String password;
    private String md5;
    private String sha1;
    private String sha256;
    private final static long serialVersionUID = 5096575104467681874L;

}
