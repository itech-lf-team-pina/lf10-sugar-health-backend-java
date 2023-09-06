package com.health.sugar.lf10sugarhealth.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity()
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    private String imageUrl;

    public Profile(String name, Member member, String imageUrl) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.member = member;
    }

    public Profile() {

    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
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
}
