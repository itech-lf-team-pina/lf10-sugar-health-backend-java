package com.health.sugar.lf10sugarhealth.service;

import com.health.sugar.lf10sugarhealth.dto.SugarCalculationDto;

public class CustomUnitSugarCalculator implements ISugarCalculationService
{
    @Override
    public float calculateTotalSugar(SugarCalculationDto sugarCalculationDto) {
        return sugarCalculationDto.consumedUnits * sugarCalculationDto.sugarPerUnit;
    }
}
