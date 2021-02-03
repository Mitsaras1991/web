package com.linn.web;

import com.sun.istack.NotNull;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ImageUploadModel implements Serializable {
    @NotNull
    @NotBlank(message = "Heading can't be blank")
    private String heading;
    private MultipartFile file;

    public ImageUploadModel() {
    }

    public ImageUploadModel(String heading, MultipartFile file) {
        this.heading = heading;
        this.file = file;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
