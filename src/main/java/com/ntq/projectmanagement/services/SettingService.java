package com.ntq.projectmanagement.services;

import com.ntq.projectmanagement.settings.Setting;
import org.springframework.stereotype.Service;

public interface SettingService {
    Setting getValueFromKey(String key);
}
