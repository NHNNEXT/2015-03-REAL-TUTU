package org.next.infra.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginToken {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private String email;
    private String password;

    public void encryptPassword() {
        if(this.password == null) return;

        this.password = passwordEncoder.encode(this.password);
    }

    public boolean isPasswordCorrect(String dbPassword) {
        return this.password != null && passwordEncoder.matches(this.password, dbPassword);
    }
}