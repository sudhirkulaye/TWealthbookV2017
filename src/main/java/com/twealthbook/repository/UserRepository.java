package com.twealthbook.repository;

import com.twealthbook.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    @Cacheable("users")
    public Optional<User> findByUserLoginId(String userLoginId);
}
