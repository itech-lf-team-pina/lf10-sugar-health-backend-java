package com.health.sugar.lf10sugarhealth.repository;

import com.health.sugar.lf10sugarhealth.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    int countProfileByMemberId(UUID member_id);

    List<Profile> findAllByMemberId(UUID member_id);
}
