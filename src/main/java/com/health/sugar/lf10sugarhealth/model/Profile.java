package com.health.sugar.lf10sugarhealth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;

@Entity()
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String imageUrl;

    private Boolean primaryProfile;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonIgnore
    private Account account;

    public Profile(String name, Account account, String imageUrl, Boolean primaryProfile) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.account = account;
        this.primaryProfile = primaryProfile;
    }

    public Profile() {

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public Boolean getPrimaryProfile() {
        return primaryProfile;
    }
}
