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

    @JsonProperty("sugar")
    private Float sugar;

    @JsonProperty("quantity")
    private Float quantity;

    @JsonProperty("sugarPerQuantity")
    private Float sugarPerQuantity;

    public Barcode() {}

    public Barcode(String barcode, String name, Float sugar, Float quantity, Float sugarPerQuantity) {
        this.barcode = barcode;
        this.name = name;
        this.sugar = sugar;
        this.quantity = quantity;
        this.sugarPerQuantity = sugarPerQuantity;
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

    public Float getSugar() {
        return sugar;
    }

    public void setSugar(Float sugar) {
        this.sugar = sugar;
    }

    public Float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getSugarPerQuantity() {
        return sugarPerQuantity;
    }

    public void setSugarPerQuantity(Float sugarPerQuantity) {
        this.sugarPerQuantity = sugarPerQuantity;
    }
}
