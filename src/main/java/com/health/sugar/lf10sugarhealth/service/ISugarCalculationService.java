package com.health.sugar.lf10sugarhealth.service;

import com.health.sugar.lf10sugarhealth.dto.SugarCalculationDto;

public interface ISugarCalculationService
{
    public float calculateTotalSugar(SugarCalculationDto sugarCalculationDto);
}
