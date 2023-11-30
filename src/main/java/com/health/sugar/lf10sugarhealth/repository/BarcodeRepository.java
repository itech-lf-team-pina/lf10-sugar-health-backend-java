package com.health.sugar.lf10sugarhealth.repository;

import com.health.sugar.lf10sugarhealth.model.Barcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarcodeRepository extends JpaRepository<Barcode, Long> {
    Barcode findBarcodeByBarcode(String barcode);
}
