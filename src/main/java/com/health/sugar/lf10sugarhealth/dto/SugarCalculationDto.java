package com.health.sugar.lf10sugarhealth.dto;

import com.health.sugar.lf10sugarhealth.common.enums.CalculationMode;

import java.util.UUID;

public class SugarCalculationDto {
    public float sugarPerUnit;
    public float consumedUnits;
    public long productId;
    private CalculationMode calculationMode;
    public CalculationMode getMode()
    {
        return calculationMode;
    }
}
