package com.health.sugar.lf10sugarhealth.repository;


import com.health.sugar.lf10sugarhealth.dto.SugarInputStat;
import com.health.sugar.lf10sugarhealth.model.SugarInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SugarInputRepository extends JpaRepository<SugarInput, Long> {

    @Query(value = "SELECT " +
            "new com.health.sugar.lf10sugarhealth.dto.SugarInputStat(" +
            "s.member.id, SUM(s.intake), Date(s.timestamp)) " +
            "FROM SugarInput s " +
            "WHERE " +
            "s.member.id = :memberId " +
            "AND timestamp BETWEEN :startDate AND :endDate " +
            "GROUP BY Date(s.timestamp), s.member.id"
    )
    List<SugarInputStat> getSumOfSugarInputsForMemberAndTimestampDateBetween(@Param("memberId") UUID memberId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}