package com.linn.web;

import javax.persistence.Column;

public class ImageBase64Model {
    @Column
    private String filename;
    @Column
    private String image64;

    public ImageBase64Model() {
    }

    public ImageBase64Model(String filename, String image64) {
        this.filename = filename;
        this.image64 = image64;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getImage64() {
        return image64;
    }

    public void setImage64(String image64) {
        this.image64 = image64;
    }
}
