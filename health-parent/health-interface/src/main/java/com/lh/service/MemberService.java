package com.lh.service;

import com.lh.pojo.Member;

public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);

    int countByRegTime(String regDate);
}
