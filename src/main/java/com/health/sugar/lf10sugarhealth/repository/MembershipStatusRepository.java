package com.health.sugar.lf10sugarhealth.repository;

import com.health.sugar.lf10sugarhealth.model.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MembershipStatusRepository extends JpaRepository<MembershipStatus, UUID> {
    Optional<MembershipStatus> findByMemberId(UUID memberId);
}
