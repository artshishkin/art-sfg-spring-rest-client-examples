
package com.artarkatesoft.api.domain;

import java.io.Serializable;

public class Billing implements Serializable
{

    private Card card;
    private final static long serialVersionUID = 5988914043561963447L;

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

}
