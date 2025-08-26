package com.kawasaki.service.provider_service.service.impl;

import com.kawasaki.service.common.exception.BizException;
import com.kawasaki.service.common.exception.BizExceptionCodeEnum;
import com.kawasaki.service.provider_service.mapper.ProviderMapper;
import com.kawasaki.service.provider_service.model.Provider;
import com.kawasaki.service.provider_service.model.ProviderExample;
import com.kawasaki.service.provider_service.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    private ProviderMapper providerMapper;

    @Override
    public Provider findByEmail(String email) {
        ProviderExample example = new ProviderExample();
        ProviderExample.Criteria criteria = example.createCriteria();

        criteria.andEmailEqualTo(email);

        List<Provider> result = providerMapper.selectByExample(example);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public Provider createProvider(String name, String email, String phone, String password_hash, String bio) {
        // check if email exists
        Provider existingProvider = this.findByEmail(email);
        if (!Objects.isNull(existingProvider)) {
            throw new BizException(BizExceptionCodeEnum.EMAIL_EXISTS);
        }

        // instantiate model and insert
        Provider provider = new Provider();
        provider.setName(name);
        provider.setEmail(email);
        provider.setPhone(phone);
        provider.setPasswordHash(password_hash);
        provider.setBio(bio);
        provider.setCreatedAt(new Date(System.currentTimeMillis()));
        provider.setUpdatedAt(new Date(System.currentTimeMillis()));

        int rows = providerMapper.insert(provider);
        if (rows != 1) {
            throw new BizException(BizExceptionCodeEnum.DB_INSERT_FAILED);
        }

        return provider;
    }
}
