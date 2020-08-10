package com.artarkatesoft.artsfgspringrestclientexamples.domain;

import lombok.Data;

@Data
public class ExpirationDate {
//          "expiration_date": {
//            "date": "2016-09-29 01:39:53.000000",
//            "timezone_type": 3,
//            "timezone": "UTC"
//          },

    private String date;
    private Integer timezone_type;
    private String timezone;
}
