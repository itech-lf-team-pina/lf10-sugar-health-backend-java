package com.health.sugar.lf10sugarhealth.dto;

public class CreateBarCodeDTO {
    private String name;

    private String barcode;

    private Float sugar;

    private Float quantity;

    private Float sugarPerQuantity;

    public CreateBarCodeDTO() {}

    public CreateBarCodeDTO(String name, String barcode) {
        this.name = name;
        this.barcode = barcode;
        this.sugar = sugar;
        this.quantity = quantity;
        this.sugarPerQuantity = sugarPerQuantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CreateProfileRequestBody{" +
                "name='" + name + '\'' +
                ", barcode='" + barcode + '\'' +
                '}';
    }
}
