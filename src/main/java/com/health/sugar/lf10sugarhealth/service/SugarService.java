package com.health.sugar.lf10sugarhealth.service;

import com.health.sugar.lf10sugarhealth.common.exceptions.EmptyResponseException;
import com.health.sugar.lf10sugarhealth.dto.request.CreateSugarInputRequestBody;
import com.health.sugar.lf10sugarhealth.model.Profile;
import com.health.sugar.lf10sugarhealth.model.SugarInput;
import com.health.sugar.lf10sugarhealth.repository.ProfileRepository;
import com.health.sugar.lf10sugarhealth.repository.SugarInputRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SugarService {

    @Autowired
    SugarInputRepository sugarInputRepository;

    @Autowired
    ProfileRepository profileRepository;

    public List<SugarInput> getSugarByAccount(UUID account_id) throws EmptyResponseException {
        List<SugarInput> sugarInputList = sugarInputRepository.findAllByAccountIdAndDateBetweenOrderByTimestampTimestampDesc(account_id);

        if (sugarInputList.isEmpty()) {
            throw new EmptyResponseException("getSugarByAccount response is empty");
        }
        return sugarInputList;
    }

    public SugarInput create(CreateSugarInputRequestBody sugarInputRequestBody) {
        Profile profile = profileRepository.getReferenceById(sugarInputRequestBody.getProfileId());

        return sugarInputRepository.save(
                new SugarInput(
                        sugarInputRequestBody.getIntake(),
                        profile,
                        sugarInputRequestBody.getDescription()
                )
        );
    }
}
