package org.next.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String encode(String originalPassword) {
        return encoder.encode(originalPassword);
    }

    public boolean same(String originalPassword, String encodedPassword) {
        return encoder.matches(originalPassword, encodedPassword);
    }
}
