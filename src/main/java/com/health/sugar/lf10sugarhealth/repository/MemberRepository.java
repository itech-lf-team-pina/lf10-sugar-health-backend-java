package com.health.sugar.lf10sugarhealth.repository;

import com.health.sugar.lf10sugarhealth.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
}
