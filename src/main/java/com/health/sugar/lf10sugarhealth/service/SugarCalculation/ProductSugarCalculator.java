package com.health.sugar.lf10sugarhealth.service.SugarCalculation;

import com.health.sugar.lf10sugarhealth.dto.request.SugarCalculationDto;
import com.health.sugar.lf10sugarhealth.model.Product;
import com.health.sugar.lf10sugarhealth.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ProductSugarCalculator implements ISugarCalculationService
{
    @Autowired
    ProductRepository productRepository;

    @Override
    public float calculateTotalSugar(SugarCalculationDto sugarCalculationDto) {
        Product product = productRepository.getReferenceById(sugarCalculationDto.productId);
        return sugarCalculationDto.consumedUnits * product.getSugar();
    }
}
