package com.health.sugar.lf10sugarhealth.service;

import com.health.sugar.lf10sugarhealth.common.exceptions.EmptyResponseException;
import com.health.sugar.lf10sugarhealth.model.Profile;
import com.health.sugar.lf10sugarhealth.repository.ProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;

    public List<Profile> getProfilesByMemberId(UUID member_id) throws EmptyResponseException {
        List<Profile> profiles = profileRepository.findAllByMemberId(member_id);

        if (profiles.isEmpty()) {
            throw new EmptyResponseException("Profile response is empty");
        }
        return profiles;
    }
}
