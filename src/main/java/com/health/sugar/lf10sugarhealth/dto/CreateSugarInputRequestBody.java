package com.health.sugar.lf10sugarhealth.dto;

import java.util.UUID;

public class CreateSugarInputRequestBody {
    private Float intake;

    private String description;
    private UUID memberID;

    public UUID getMemberID() {
        return memberID;
    }

    public void setMemberID(UUID memberID) {
        this.memberID = memberID;
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
