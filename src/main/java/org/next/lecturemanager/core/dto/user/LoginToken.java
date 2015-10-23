package org.next.lecturemanager.core.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginToken {
    private String email;
    private String password;
}