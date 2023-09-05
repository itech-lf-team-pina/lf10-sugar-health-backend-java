package com.health.sugar.lf10sugarhealth.controller;

import com.health.sugar.lf10sugarhealth.common.enums.StatsPeriod;
import com.health.sugar.lf10sugarhealth.dto.CreateSugarInputRequestBody;
import com.health.sugar.lf10sugarhealth.dto.SugarInputStat;
import com.health.sugar.lf10sugarhealth.model.Member;
import com.health.sugar.lf10sugarhealth.model.SugarInput;
import com.health.sugar.lf10sugarhealth.repository.MemberRepository;
import com.health.sugar.lf10sugarhealth.repository.SugarInputRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    MemberRepository memberRepository;

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
            Optional<Member> member = memberRepository.findById(sugarInputRequestBody.getMemberID());

            if (member.isPresent()) {
                SugarInput sugarInput = sugarInputRepository.save(
                        new SugarInput(sugarInputRequestBody.getIntake(), member.get()));

                return new ResponseEntity<>(sugarInput, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/stats/{period}")
    public ResponseEntity<List<SugarInput>> getStatsByPeriod(@PathVariable("id") UUID memberId, @PathVariable("period") StatsPeriod period) {
        try {
            switch (period) {
                case DAILY -> {
                    logger.info("Called daily stats for member: " + memberId);
                    LocalDateTime endDate = LocalDate.now().atTime(23,59,59);
                    LocalDateTime startDate = LocalDate.now().minusDays(7).atStartOfDay();

                    List<SugarInputStat> sugarInputStats = sugarInputRepository.getSumOfSugarInputsForMemberAndTimestampDateBetween(memberId, startDate, endDate);

                }
                case MONTHLY -> {
                    logger.info("Called monthly stats for member: " + memberId);
                }
                default ->  {
                    logger.error("Called stats for member: " + memberId + " without valid period..");
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }

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

}
