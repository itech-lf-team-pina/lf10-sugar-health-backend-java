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
    @JsonProperty("first")
    private String firstname;

    @Column
    @JsonProperty("last")
    private String lastname;

    @Column
    @JsonProperty("google_client_id")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Nullable
    private String google_client_id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "membership_status_id", referencedColumnName = "id")
    @JsonIgnore
    private MembershipStatus membershipStatus;

    public Member() {}

    public Member(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Member(String first, String last, MembershipStatus membershipStatus) {
        this.firstname = first;
        this.lastname = last;
        this.membershipStatus = membershipStatus;
    }

    public Member(String first, String last, MembershipStatus membershipStatus, @Nullable String google_client_id) {
        this.firstname = first;
        this.lastname = last;
        this.membershipStatus = membershipStatus;
        this.google_client_id = google_client_id;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public MembershipStatus getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(MembershipStatus membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    @Nullable
    public String getGoogle_client_id() {
        return google_client_id;
    }

    public void setGoogle_client_id(@Nullable String google_client_id) {
        this.google_client_id = google_client_id;
    }
}
