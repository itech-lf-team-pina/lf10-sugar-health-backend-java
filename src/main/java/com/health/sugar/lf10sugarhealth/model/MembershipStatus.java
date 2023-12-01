package com.health.sugar.lf10sugarhealth.model;

import com.health.sugar.lf10sugarhealth.common.enums.BillingPeriod;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "membership_status")
public class MembershipStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private Boolean isActive;

    @Column
    private LocalDateTime startDate;

    @Column
    private BillingPeriod billingPeriod;

    public MembershipStatus() {
        this.isActive = false;
    }

    MembershipStatus(BillingPeriod period, LocalDateTime startDate, Boolean isActive, Account account) {
        this.billingPeriod = period;
        this.startDate = startDate;
        this.isActive = isActive;
    }

    MembershipStatus(Account account) {
        this.billingPeriod = null;
        this.startDate = null;
        this.isActive = false;
    }

    public UUID getId() {
        return id;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public BillingPeriod getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(BillingPeriod billingPeriod) {
        this.billingPeriod = billingPeriod;
    }
}
