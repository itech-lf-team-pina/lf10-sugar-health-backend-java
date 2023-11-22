package com.health.sugar.lf10sugarhealth.service;

import com.health.sugar.lf10sugarhealth.common.exceptions.EmptyResponseException;
import com.health.sugar.lf10sugarhealth.model.Barcode;
import com.health.sugar.lf10sugarhealth.repository.BarcodeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class BarcodeService {
    @Autowired
    BarcodeRepository barcodeRepository;

    public List<Barcode> getBarcodes() throws EmptyResponseException {
        List<Barcode> barcodes = barcodeRepository.findAll();

        if (barcodes.isEmpty()) {
            throw new EmptyResponseException("Barcode response is empty");
        }
        return barcodes;
    }

    public Barcode getBarcodeByBarcode(String barcode) throws EmptyResponseException {
        Barcode barcodeList = barcodeRepository.findBarcodeByBarcode(barcode);

        if (barcode.isEmpty()) {
            throw new EmptyResponseException("Barcode response is empty");
        }
        return barcodeList;
    }

    public Barcode createBarcode(String barcode, String name) throws DataIntegrityViolationException {
        Barcode barcodeSaved = barcodeRepository.save(new Barcode(barcode, name));
        return barcodeSaved;
    }
}
