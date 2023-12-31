package com.health.sugar.lf10sugarhealth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Nullable
    private UUID id;

    @Column
    @JsonProperty("displayName")
    private String displayName;

    @Column(unique = true)
    @JsonProperty("login_uid")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String login_uid;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "membership_status_id", referencedColumnName = "id")
    @JsonIgnore
    private MembershipStatus membershipStatus;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "account")
    @JsonIgnore
    private List<Profile> profileList;

    public Account() {}

    public Account(String displayName) {
        this.displayName = displayName;
    }

    public Account(String displayName, MembershipStatus membershipStatus) {
        this.displayName = displayName;
        this.membershipStatus = membershipStatus;
    }

    public Account(String displayName, MembershipStatus membershipStatus, @Nullable String login_uid) {
        this.displayName = displayName;
        this.membershipStatus = membershipStatus;
        this.login_uid = login_uid;
    }

    public UUID getId() {
        return id;
    }


    public MembershipStatus getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(MembershipStatus membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    public String getLogin_uid() {
        return login_uid;
    }

    public void setLogin_uid(@Nullable String google_client_id) {
        this.login_uid = google_client_id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<Profile> getProfileList() {
        return profileList;
    }

    public void setProfileList(List<Profile> profileList) {
        this.profileList = profileList;
    }
}
