package com.arka.user_service.domain.service;

import com.arka.user_service.domain.util.RegexPatterns;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class EmailValidationService {
    private static final Pattern CODE_PATTERN = Pattern.compile(RegexPatterns.EMAIL_CODE_PATTERN);

    public boolean isValid(String code) {
        return code != null && CODE_PATTERN.matcher(code).matches();
    }
}
