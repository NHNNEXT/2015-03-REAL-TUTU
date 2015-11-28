package org.next.infra.repository;

import org.next.lms.content.control.auth.UserGroupCanReadContent;
import org.next.lms.content.control.auth.UserGroupCanReadTodo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupCanReadTodoRepository extends JpaRepository<UserGroupCanReadTodo, Long> {
    void deleteByUserGroupId(Long id);

    void deleteByContentTypeId(Long id);
}
