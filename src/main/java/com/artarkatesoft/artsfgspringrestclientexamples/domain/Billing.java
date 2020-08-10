package com.artarkatesoft.artsfgspringrestclientexamples.domain;

import lombok.Data;

@Data
public class Billing {
//      "billing": {
//        "card": {
//          "type": "MasterCard",
//          "number": "4539661724756466",
//          "expiration_date": {
//            "date": "2016-09-29 01:39:53.000000",
//            "timezone_type": 3,
//            "timezone": "UTC"
//          },
//          "iban": "NA55633746255612759286461167",
//          "swift": "RGBHHSXZ"
//        }
//      },
    private Card card;
}
