package com.health.sugar.lf10sugarhealth.model;

import jakarta.persistence.*;

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

    public SugarInput() {}

    public SugarInput(Float intake, LocalDateTime timestamp) {
        this.intake = intake;
        this.timestamp = timestamp;
    }

    public SugarInput(Float intake) {
        this.intake = intake;
        this.timestamp = LocalDateTime.now();
    }

    public float getIntake() {
        return intake;
    }

    public void setIntake(float intake) {
        this.intake = intake;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("SugarInput [id: %s | intake: %s | timestamp: %s ]", this.id, this.intake, this.timestamp.toString());
    }

    public Long getId() {
        return id;
    }
}
