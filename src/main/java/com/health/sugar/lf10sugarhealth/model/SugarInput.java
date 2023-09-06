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

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    public SugarInput() {}

    public SugarInput(Float intake, LocalDateTime timestamp) {
        this.intake = intake;
        this.timestamp = timestamp;
    }

    public SugarInput(Float intake) {
        this.intake = intake;
        this.timestamp = LocalDateTime.now();
    }

    public SugarInput(Float intake, Member member) {
        this.intake = intake;
        this.member = member;
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
        return String.format("SugarInput [id: %s | intake: %s | timestamp: %s | User: %s ]", this.id, this.intake, this.timestamp.toString(), this.getUser().toString());
    }

    public Long getId() {
        return id;
    }

    public Member getUser() {
        return member;
    }

    public void setUser(Member member) {
        this.member = member;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
