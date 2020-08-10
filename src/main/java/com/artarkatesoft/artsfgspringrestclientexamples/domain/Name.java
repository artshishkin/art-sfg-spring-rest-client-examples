package com.artarkatesoft.artsfgspringrestclientexamples.domain;

import lombok.Data;

@Data
public class Name {
//    "name": {
//      "title": "Ms.",
//      "first": "Molly",
//      "last": "Robel"
//    }

    private String title;
    private String first;
    private String last;
}
