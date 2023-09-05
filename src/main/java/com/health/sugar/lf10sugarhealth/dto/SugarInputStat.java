package com.health.sugar.lf10sugarhealth.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

public class SugarInputStat {
    private UUID member_id;
    private Float sum;
    private LocalDateTime date;

    SugarInputStat() {}

    SugarInputStat(UUID member_id, Float sum, LocalDateTime date) {
        this.member_id = member_id;
        this.sum = sum;
        this.date = date;
    }

    SugarInputStat(UUID member_id, Float sum, LocalDate date) {
        this.member_id = member_id;
        this.sum = sum;
        this.date = date.atStartOfDay();
    }

    SugarInputStat(UUID member_id, Float sum, Date date) {
        this.member_id = member_id;
        this.sum = sum;
        this.date = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    SugarInputStat(UUID member_id, Float sum) {
        this.member_id = member_id;
        this.sum = sum;
    }

    public UUID getMember_id() {
        return member_id;
    }

    public void setMember_id(UUID member_id) {
        this.member_id = member_id;
    }

    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
