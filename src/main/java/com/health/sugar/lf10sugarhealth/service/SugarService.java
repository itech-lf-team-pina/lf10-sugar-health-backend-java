package com.health.sugar.lf10sugarhealth.service;

import com.health.sugar.lf10sugarhealth.common.exceptions.EmptyResponseException;
import com.health.sugar.lf10sugarhealth.model.SugarInput;
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

    public List<SugarInput> getSugarByMember(UUID member_id) throws EmptyResponseException {
        List<SugarInput> sugarInputList = sugarInputRepository.findAllByMemberIdAndDateBetweenOrderByTimestampTimestampDesc(member_id);

        if (sugarInputList.isEmpty()) {
            throw new EmptyResponseException("getSugarByMember response is empty");
        }
        return sugarInputList;
    }
}
