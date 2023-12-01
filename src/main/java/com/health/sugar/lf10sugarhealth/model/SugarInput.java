package com.health.sugar.lf10sugarhealth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sugar_input")
public class SugarInput {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "intake")
    private float intake;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "date")
    private LocalDate date;

    @Column(name= "description")
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    @JsonIgnore
    private Profile profile;

    public SugarInput() {}

    public SugarInput(Float intake, LocalDateTime timestamp) {
        this.intake = intake;
        this.timestamp = timestamp;
    }

    public SugarInput(Float intake) {
        this.intake = intake;
        this.timestamp = LocalDateTime.now();
    }

    public SugarInput(Float intake, Profile profile, String description) {
        this.intake = intake;
        this.profile = profile;
        this.description = description;
        this.date = LocalDate.now();
        this.timestamp = LocalDateTime.now();
    }

    public float getIntake() {
        return intake;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return String.format("SugarInput [id: %s | intake: %s | timestamp: %s | User: %s ]", this.id, this.intake, this.timestamp.toString(), this.getProfile().toString());
    }

    public Long getId() {
        return id;
    }
    public Profile getProfile() {
        return profile;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }
}
