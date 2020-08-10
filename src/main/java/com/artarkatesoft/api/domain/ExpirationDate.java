
package com.artarkatesoft.api.domain;

import java.io.Serializable;

public class ExpirationDate implements Serializable
{

    private String date;
    private Integer timezoneType;
    private String timezone;
    private final static long serialVersionUID = 2549450060707957210L;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTimezoneType() {
        return timezoneType;
    }

    public void setTimezoneType(Integer timezoneType) {
        this.timezoneType = timezoneType;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

}
