package com.arka.user_service.domain.service;

import com.arka.user_service.domain.util.RegexPatterns;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class FullNameValidationService {
    private static final Pattern PATTERN = Pattern.compile(RegexPatterns.FULLNAME_PATTERN);

    public boolean isValid(String fullName) {
        return fullName != null && PATTERN.matcher(fullName).matches();
    }
}

