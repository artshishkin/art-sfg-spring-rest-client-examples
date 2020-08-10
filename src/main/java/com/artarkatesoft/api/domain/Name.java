
package com.artarkatesoft.api.domain;

import java.io.Serializable;

public class Name implements Serializable
{

    private String title;
    private String first;
    private String last;
    private final static long serialVersionUID = 819114313060912305L;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

}
