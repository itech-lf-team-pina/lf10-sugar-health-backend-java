package com.health.sugar.lf10sugarhealth.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonProperty("category")
    private String category;

    @JsonProperty("category_german")
    private String categoryGerman;

    @JsonProperty("description")
    private String description;

    @JsonProperty("description_german")
    private String descriptionGerman;

    @JsonProperty("sugar")
    private Float sugar;
}
