package com.health.sugar.lf10sugarhealth.service.SugarCalculation;

import com.health.sugar.lf10sugarhealth.dto.request.SugarCalculationDto;

public interface ISugarCalculationService
{
    public float calculateTotalSugar(SugarCalculationDto sugarCalculationDto);
}
