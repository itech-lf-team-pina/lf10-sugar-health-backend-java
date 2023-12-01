package com.health.sugar.lf10sugarhealth.controller;

import com.health.sugar.lf10sugarhealth.repository.ProfileRepository;
import com.health.sugar.lf10sugarhealth.service.SugarCalculation.CustomUnitSugarCalculator;
import com.health.sugar.lf10sugarhealth.service.SugarCalculation.WeightSugarCalculator;
import com.health.sugar.lf10sugarhealth.service.SugarCalculation.ISugarCalculationService;
import com.health.sugar.lf10sugarhealth.common.enums.CalculationMode;
import com.health.sugar.lf10sugarhealth.common.enums.StatsPeriod;
import com.health.sugar.lf10sugarhealth.dto.request.CreateSugarInputRequestBody;
import com.health.sugar.lf10sugarhealth.dto.request.SugarCalculationDto;
import com.health.sugar.lf10sugarhealth.dto.request.SugarInputStat;
import com.health.sugar.lf10sugarhealth.model.SugarInput;
import com.health.sugar.lf10sugarhealth.repository.SugarInputRepository;
import com.health.sugar.lf10sugarhealth.service.SugarCalculation.ProductSugarCalculator;
import com.health.sugar.lf10sugarhealth.service.SugarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/sugar")
public class SugarInputController {

    @Autowired
    SugarInputRepository sugarInputRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    SugarService sugarService;

    Logger logger = LoggerFactory.getLogger(SugarInputController.class);


    @GetMapping("/")
    public ResponseEntity<List<SugarInput>> getAllSugarInputs() {
        try {
            List<SugarInput> sugarInputList = new ArrayList<>(sugarInputRepository.findAll());

            if (sugarInputList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(sugarInputList, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SugarInput> getSugarInputById(@PathVariable("id") long id) {
        try {
            Optional<SugarInput> sugarInputOptional = sugarInputRepository.findById(id);

            return sugarInputOptional
                    .map(sugarInput -> new ResponseEntity<>(sugarInput, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SugarInput> deleteSugarInputById(@PathVariable("id") long id) {
        try {
            sugarInputRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<SugarInput> createSugarInput(@RequestBody CreateSugarInputRequestBody sugarInputRequestBody) {
        try {
            SugarInput sugarInput = sugarService.create(sugarInputRequestBody);

            return new ResponseEntity<>(sugarInput, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{profile_id}/stats/{period}")
    public ResponseEntity<List<SugarInputStat>> getStatsByPeriod(@PathVariable("profile_id") UUID profileId, @PathVariable("period") StatsPeriod period) {
        try {
            List<SugarInputStat> sugarInputStats = new ArrayList<>();
            LocalDate dateNow = LocalDate.now();
            LocalDate endDate = dateNow;
            LocalDate startDate;

            switch (period) {
                case LAST_7_DAYS -> {
                    startDate = endDate.minusDays(6);
                }
                case LAST_30_DAYS -> {
                    startDate = endDate.minusDays(29);
                }
                case CURRENT_MONTH -> {
                    endDate = dateNow.withDayOfMonth(dateNow.lengthOfMonth());
                    startDate = endDate.withDayOfMonth(1);
                }
                default -> startDate = endDate;
            }

            List<SugarInput> sugarInputList = sugarInputRepository.findAllByProfileIdAndDateBetween(profileId, startDate, endDate);

            List<LocalDate> listOfDates = startDate.datesUntil(endDate.plusDays(1)).toList();

            listOfDates.forEach(currentDate -> {
                float totalSugarOfDate = 0f;

                for (SugarInput sugarInput : sugarInputList) {
                    if (sugarInput.getDate().isEqual(currentDate)) {
                        totalSugarOfDate += sugarInput.getIntake();
                    }
                }

                sugarInputStats.add(new SugarInputStat(totalSugarOfDate, currentDate));
            });

            if (sugarInputStats.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(sugarInputStats, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/calculateSugar")
    public float calculateSugarTotal(@RequestBody SugarCalculationDto sugarCalculationDto) {
        ISugarCalculationService calculator = getCalculationService(sugarCalculationDto.getMode());
        return calculator.calculateTotalSugar(sugarCalculationDto);
    }

    private ISugarCalculationService getCalculationService(CalculationMode mode) {
        ISugarCalculationService calculationService;
        switch (mode) {
            case ByHundredGram -> calculationService = new WeightSugarCalculator();
            case ByCustomUnit -> calculationService = new CustomUnitSugarCalculator();
            case ByProduct -> calculationService = new ProductSugarCalculator();
            default -> calculationService = null;
        }
        return calculationService;
    }

}
