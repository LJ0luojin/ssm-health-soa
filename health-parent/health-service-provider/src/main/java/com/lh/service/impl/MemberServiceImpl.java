package com.lh.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.lh.dao.MemberDao;
import com.lh.pojo.Member;
import com.lh.service.MemberService;

import javax.annotation.Resource;

@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }
}
