package com.health.sugar.lf10sugarhealth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class Barcode {
    @Column
    @Id
    @JsonProperty("barcode")
    private String barcode;

    @JsonProperty("name")
    private String name;

    public Barcode() {}

    public Barcode(String barcode, String name) {
        this.barcode = barcode;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
