package com.health.sugar.lf10sugarhealth.service.SugarCalculation;

import com.health.sugar.lf10sugarhealth.dto.request.SugarCalculationDto;

public class WeightSugarCalculator implements ISugarCalculationService
{

    @Override
    public float calculateTotalSugar(SugarCalculationDto sugarCalculationDto)
    {
        float sugarTotal;
        switch (sugarCalculationDto.getMode()){
            case ByHundredGram -> sugarTotal = sugarCalculationDto.sugarPerUnit * sugarCalculationDto.consumedUnits/100;
            default -> sugarTotal = 0;
        }
        return sugarTotal;
    }
}
