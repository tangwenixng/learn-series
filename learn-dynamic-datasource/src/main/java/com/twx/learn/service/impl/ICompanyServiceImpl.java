package com.twx.learn.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.twx.learn.entity.Company;
import com.twx.learn.mapper.CompanyMapper;
import com.twx.learn.service.ICompanyService;
import org.springframework.stereotype.Service;

@Service
@DS("slave_1")
public class ICompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {

}
