package com.health.sugar.lf10sugarhealth.repository;

import com.health.sugar.lf10sugarhealth.model.SugarInput;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SugarInputRepository extends JpaRepository<SugarInput, Long> {
    List<SugarInput> findAllByProfileIdAndDateBetween(UUID account_id, LocalDate startDate, LocalDate endDate);

    default List<SugarInput> findAllByAccountIdAndDateBetweenOrderByTimestampTimestampDesc(UUID account_id) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusHours(24);
        return findAllByProfileIdAndTimestampBetweenOrderByTimestampDesc(account_id, startDate, endDate);
    }

    List<SugarInput> findAllByProfileIdAndTimestampBetweenOrderByTimestampDesc(UUID account_id, LocalDateTime startDate, LocalDateTime endDate);

    void deleteAllByProfileId(UUID uid);
}