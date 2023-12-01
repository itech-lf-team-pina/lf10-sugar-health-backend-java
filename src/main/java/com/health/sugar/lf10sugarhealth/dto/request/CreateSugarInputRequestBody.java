package com.health.sugar.lf10sugarhealth.dto.request;

import java.util.UUID;

public class CreateSugarInputRequestBody {
    private Float intake;

    private String description;
    private UUID profileId;

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    public Float getIntake() {
        return intake;
    }

    public void setIntake(Float intake) {
        this.intake = intake;
    }

    public String getDescription() {
        return description;
    }
}
