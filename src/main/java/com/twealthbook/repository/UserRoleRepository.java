package com.twealthbook.repository;

import com.twealthbook.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    public UserRole findByUserId(int userId);
}
