package com.health.sugar.lf10sugarhealth.dto.response;

import com.health.sugar.lf10sugarhealth.model.Account;
import com.health.sugar.lf10sugarhealth.model.Profile;

public class AccountCreationResponseDTO {
    private Account account;

    private Profile profile;

    public AccountCreationResponseDTO(Account account, Profile profile) {
        this.account = account;
        this.profile = profile;
    }

    public Account getAccount() {
        return account;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "AccountCreationResponseDTO{" +
                "account=" + account +
                ", profile=" + profile +
                '}';
    }
}
