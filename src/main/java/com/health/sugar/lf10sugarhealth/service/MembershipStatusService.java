package com.health.sugar.lf10sugarhealth.service;

import com.health.sugar.lf10sugarhealth.common.exceptions.MembershipNotFoundException;
import com.health.sugar.lf10sugarhealth.model.MembershipStatus;
import com.health.sugar.lf10sugarhealth.repository.MembershipStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class MembershipStatusService {
    @Autowired
    MembershipStatusRepository membershipStatusRepository;

    public MembershipStatus getMembershipStatusById(UUID member_id) throws MembershipNotFoundException {
        Optional<MembershipStatus> membershipStatusOptional = membershipStatusRepository.findByMemberId(member_id);

        if (membershipStatusOptional.isPresent()) {
            return membershipStatusOptional.get();
        } else {
            throw new MembershipNotFoundException("no membership for profile found");
        }
    }
}
