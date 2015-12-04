package org.next.lms.submit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.next.infra.auth.AuthCheck;
import org.next.infra.auth.CRUDBasicAuthCheck;
import org.next.lms.user.domain.User;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Getter
@Setter
@Component
public class SubmitAuth extends CRUDBasicAuthCheck {
}
