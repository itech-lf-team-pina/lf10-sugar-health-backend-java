package com.health.sugar.lf10sugarhealth.controller;

import com.health.sugar.lf10sugarhealth.model.SugarInput;
import com.health.sugar.lf10sugarhealth.repository.SugarInputRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sugar")
public class SugarInputController {

    @Autowired
    SugarInputRepository sugarInputRepository;

    @GetMapping("/")
    public ResponseEntity<List<SugarInput>> getAllSugarInputs() {
        try {
            List<SugarInput> sugarInputList = new ArrayList<>(sugarInputRepository.findAll());

            if (sugarInputList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(sugarInputList, HttpStatus.OK);
        } catch (Exception e) {
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
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SugarInput> deleteSugarInputById(@PathVariable("id") long id) {
        try {
            sugarInputRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<SugarInput> createSugarInput(@RequestBody SugarInput sugarInputBody) {
        try {
            SugarInput sugarInput = sugarInputRepository.save(new SugarInput(sugarInputBody.getIntake()));

            return new ResponseEntity<>(sugarInput, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
