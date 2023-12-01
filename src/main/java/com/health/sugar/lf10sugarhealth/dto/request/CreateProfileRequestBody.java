package com.health.sugar.lf10sugarhealth.dto.request;

import java.util.UUID;

public class CreateProfileRequestBody {
    private String name;
    private UUID account_id;

    private String imageUrl;

    public UUID getAccountID() {
        return account_id;
    }

    public void setAccountID(UUID accountId) {
        this.account_id = accountId;
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
                ", accountId=" + account_id +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
