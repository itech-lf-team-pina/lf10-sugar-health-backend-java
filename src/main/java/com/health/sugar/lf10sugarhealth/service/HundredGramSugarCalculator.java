package com.health.sugar.lf10sugarhealth.service;

import com.health.sugar.lf10sugarhealth.dto.SugarCalculationDto;

public class HundredGramSugarCalculator implements ISugarCalculationService
{

    @Override
    public float calculateTotalSugar(SugarCalculationDto sugarCalculationDto)
    {
        // Einheit entspricht hier 100g, Angabe in Gramm, deshalb durch 100 teilen
        return sugarCalculationDto.sugarPerUnit * sugarCalculationDto.consumedUnits/100;
    }
}
