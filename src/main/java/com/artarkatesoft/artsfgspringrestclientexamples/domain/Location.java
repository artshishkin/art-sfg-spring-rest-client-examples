package com.artarkatesoft.artsfgspringrestclientexamples.domain;

import lombok.Data;

@Data
public class Location {
//      "location": {
//        "street": "24674 Cyrus Key Apt. 291",
//        "city": "Emilieberg",
//        "state": "Texas",
//        "postcode": "47890-3822"
//      },

    private String street;
    private String city;
    private String state;
    private String postcode;
}
