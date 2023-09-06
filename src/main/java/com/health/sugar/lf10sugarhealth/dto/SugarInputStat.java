package com.health.sugar.lf10sugarhealth.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

public class SugarInputStat {
    private Float sugar_total;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

    SugarInputStat() {}

    public SugarInputStat(Float sugar_total, LocalDate date) {
        this.sugar_total = sugar_total;
        this.date = date;
    }
    public Float getSugar_total() {
        return sugar_total;
    }

    public void setSugar_total(Float sugar_total) {
        this.sugar_total = sugar_total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
