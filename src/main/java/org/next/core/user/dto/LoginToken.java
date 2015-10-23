package org.next.core.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginToken {
    private String email;
    private String password;
}