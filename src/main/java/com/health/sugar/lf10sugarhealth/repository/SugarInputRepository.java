package com.health.sugar.lf10sugarhealth.repository;

import com.health.sugar.lf10sugarhealth.model.SugarInput;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SugarInputRepository extends JpaRepository<SugarInput, Long> {
    List<SugarInput> findAllByMemberIdAndDateBetween(UUID member_id, LocalDate startDate, LocalDate endDate);
}