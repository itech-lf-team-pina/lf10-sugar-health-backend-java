package com.health.sugar.lf10sugarhealth.repository;

import com.health.sugar.lf10sugarhealth.model.SugarInput;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SugarInputRepository extends JpaRepository<SugarInput, Long> {
    List<SugarInput> findAllByMemberIdAndDateBetween(UUID member_id, LocalDate startDate, LocalDate endDate);

    default List<SugarInput> findAllByMemberIdAndDateBetweenOrderByTimestampTimestampDesc(UUID member_id) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusHours(24);
        return findAllByMemberIdAndTimestampBetweenOrderByTimestampDesc(member_id, startDate, endDate);
    }

    List<SugarInput> findAllByMemberIdAndTimestampBetweenOrderByTimestampDesc(UUID member_id, LocalDateTime startDate, LocalDateTime endDate);
}