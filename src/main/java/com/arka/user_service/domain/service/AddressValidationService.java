package com.arka.user_service.domain.service;

import com.arka.user_service.domain.util.RegexPatterns;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class AddressValidationService {
    private static final Pattern PATTERN = Pattern.compile(RegexPatterns.ADDRESS_PATTERN);

    public boolean isValid(String address) {
        return address != null && PATTERN.matcher(address).matches();
    }
}

