package org.next.lecturemanager.core.repository.user;

import org.next.lecturemanager.core.domain.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
}
