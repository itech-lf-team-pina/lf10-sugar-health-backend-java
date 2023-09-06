package com.health.sugar.lf10sugarhealth.dto;

import java.util.UUID;

public class CreateProfileRequestBody {
    private String name;
    private UUID member_id;

    private String imageUrl;

    public UUID getMemberID() {
        return member_id;
    }

    public void setMemberID(UUID memberId) {
        this.member_id = memberId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CreateProfileRequestBody{" +
                "name='" + name + '\'' +
                ", memberId=" + member_id +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
