package com.health.sugar.lf10sugarhealth.service;


import com.health.sugar.lf10sugarhealth.common.exceptions.EmptyResponseException;
import com.health.sugar.lf10sugarhealth.common.exceptions.MemberNotFoundException;
import com.health.sugar.lf10sugarhealth.model.Member;
import com.health.sugar.lf10sugarhealth.model.MembershipStatus;
import com.health.sugar.lf10sugarhealth.repository.MemberRepository;
import com.health.sugar.lf10sugarhealth.repository.MembershipStatusRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class MemberService {
    @Autowired
    MembershipStatusRepository membershipStatusRepository;

    @Autowired
    MemberRepository memberRepository;

    public Member getMemberById(UUID member_id) throws MemberNotFoundException {
        Optional<Member> memberOptional = memberRepository.findById(member_id);

        if (memberOptional.isPresent()) {
            return memberOptional.get();
        } else {
            throw new MemberNotFoundException("no membership for profile found");
        }
    }

    public Member getMemberByLoginId(String login_id) {
        Optional<Member> memberOptional = memberRepository.findMemberByLoginUid(login_id);

        return memberOptional.orElse(null);
    }

    public List<Member> getAllMember() throws EmptyResponseException {
        List<Member> memberList = memberRepository.findAll();

        if (memberList.isEmpty()) {
            throw new EmptyResponseException("Member response is empty");
        }
        return memberList;
    }


    public Member createMember(String displayName, String login_uid) throws DataIntegrityViolationException {
        MembershipStatus membershipStatus = membershipStatusRepository.save(new MembershipStatus());

        Member member = memberRepository.save(
                new Member(displayName, membershipStatus, login_uid));

        membershipStatus.setMember(member);

        membershipStatusRepository.save(membershipStatus);
        return member;
    }

    public void deleteMember(UUID member_id) {
        memberRepository.deleteById(member_id);
    }


}
