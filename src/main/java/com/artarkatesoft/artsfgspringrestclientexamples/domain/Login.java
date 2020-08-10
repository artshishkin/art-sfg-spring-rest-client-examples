package com.artarkatesoft.artsfgspringrestclientexamples.domain;

import lombok.Data;

@Data
public class Login {
//      "login": {
//        "username": "devonte67",
//        "password": "><'}$pwwuv",
//        "md5": "a9b1643ad87da1cf6598a27c5bbd3ae1",
//        "sha1": "42f2cc46f189cb35c600d4a788a050d0ee27d623",
//        "sha256": "9f5a440f116de4b9e54ac378ba96b98f126cf0a3ec6c76d0e01cfefd8b75fcab"
//      },

    private String username;
    private String password;
    private String md5;
    private String sha1;
    private String sha256;
}
