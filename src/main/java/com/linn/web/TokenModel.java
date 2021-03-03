package com.linn.web;

import javax.persistence.Column;
import java.io.Serializable;

public class TokenModel {
    @Column
    private String IdToken;

    public TokenModel() {
    }

    public TokenModel(String IdToken) {
        this.IdToken = IdToken;
    }

    public String getIdToken() {
        return this.IdToken;
    }

    public void setIdToken(String IdToken) {
        this.IdToken = IdToken;
    }
}
