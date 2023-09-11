package com.health.sugar.lf10sugarhealth.service.SugarCalculation;

import com.health.sugar.lf10sugarhealth.dto.SugarCalculationDto;

public class WeightSugarCalculator implements ISugarCalculationService
{

    @Override
    public float calculateTotalSugar(SugarCalculationDto sugarCalculationDto)
    {
        float sugarTotal = 0f;
        switch (sugarCalculationDto.getMode()){
            case ByHundredGram -> sugarTotal = sugarCalculationDto.sugarPerUnit * sugarCalculationDto.consumedUnits/100;
            default -> sugarTotal = 0;
        }
        return sugarTotal;
    }
}
