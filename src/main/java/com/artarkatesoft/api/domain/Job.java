
package com.artarkatesoft.api.domain;

import java.io.Serializable;

public class Job implements Serializable
{

    private String title;
    private String company;
    private final static long serialVersionUID = -3389805678789129000L;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

}
