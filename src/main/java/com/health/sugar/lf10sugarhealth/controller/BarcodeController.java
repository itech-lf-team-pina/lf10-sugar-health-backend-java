package com.health.sugar.lf10sugarhealth.controller;

import com.health.sugar.lf10sugarhealth.common.exceptions.EmptyResponseException;
import com.health.sugar.lf10sugarhealth.dto.CreateBarCodeDTO;
import com.health.sugar.lf10sugarhealth.model.Barcode;
import com.health.sugar.lf10sugarhealth.service.BarcodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barcode")
public class BarcodeController {

    @Autowired
    BarcodeService barcodeService;

    Logger logger = LoggerFactory.getLogger(BarcodeController.class);

    @GetMapping("/")
    public ResponseEntity<List<Barcode>> getAllBarcodes(
            @RequestParam(value = "page", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "size", defaultValue = "25", required = false) int pageSize
    ) {
        try {
            List<Barcode> barcodes = barcodeService.getBarcodes();

            return new ResponseEntity<>(barcodes, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{barcode}")
    public ResponseEntity<Barcode> getBarcodeByBarcode(
            @PathVariable("barcode") String barcode) {
        try {
            Barcode barcodeByBarcode = barcodeService.getBarcodeByBarcode(barcode);

            return new ResponseEntity<>(barcodeByBarcode, HttpStatus.OK);

        } catch (EmptyResponseException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Barcode> deleteMemberById(@PathVariable("barcode") String barcode) {
//        try {
//            barcodeService.delete(barcode);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/")
    public ResponseEntity<Barcode> createMember(@RequestBody CreateBarCodeDTO barCodeDTO) throws EmptyResponseException {
        try {
            Barcode barcode = barcodeService.createBarcode(barCodeDTO.getBarcode(), barCodeDTO.getName());

            return new ResponseEntity<>(barcode, HttpStatus.CREATED);

        } catch (DataIntegrityViolationException dve) {
            logger.info("Barcode is already in database, returning existing barcode");
            Barcode barcode = barcodeService.getBarcodeByBarcode(barCodeDTO.getBarcode());
            return new ResponseEntity<>(barcode, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
