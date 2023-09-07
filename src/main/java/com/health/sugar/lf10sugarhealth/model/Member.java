package com.health.sugar.lf10sugarhealth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Nullable
    private UUID id;

    @Column
    @JsonProperty("displayName")
    private String displayName;

    @Column
    @JsonProperty("login_uid")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Nullable
    private String login_uid;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "membership_status_id", referencedColumnName = "id")
    @JsonIgnore
    private MembershipStatus membershipStatus;

    public Member() {}

    public Member(String displayName) {
        this.displayName = displayName;
    }

    public Member(String displayName, MembershipStatus membershipStatus) {
        this.displayName = displayName;
        this.membershipStatus = membershipStatus;
    }

    public Member(String displayName, MembershipStatus membershipStatus, @Nullable String login_uid) {
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

    @Nullable
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
}
