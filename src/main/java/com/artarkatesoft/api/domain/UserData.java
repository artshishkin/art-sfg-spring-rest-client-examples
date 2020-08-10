package com.artarkatesoft.api.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class UserData implements Serializable {
    List<User> data;
    private final static long serialVersionUID = -8396109513325368224L;
}
