package com.health.sugar.lf10sugarhealth.dto.request;


public class CreateAccountDTO {
    private String name;
    private String uid;
    private String image;

    public CreateAccountDTO(String name, String uid, String image) {
        this.name = name;
        this.uid = uid;
        this.image = image;
    }

    public CreateAccountDTO() {
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getImageUrl() {
        return image;
    }

    @Override
    public String toString() {
        return "CreateAccountDTO{" +
                "name='" + name + '\'' +
                ", uid='" + uid + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
