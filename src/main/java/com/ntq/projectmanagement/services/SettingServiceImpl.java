package com.ntq.projectmanagement.services;

import com.ntq.projectmanagement.repository.SettingRepository;
import com.ntq.projectmanagement.settings.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl implements SettingService{
    @Autowired
    SettingRepository settingRepository;

    @Override
    public Setting getValueFromKey(String key) {
        return settingRepository.findSettingByKeyContainingIgnoreCase(key);
    }
}
