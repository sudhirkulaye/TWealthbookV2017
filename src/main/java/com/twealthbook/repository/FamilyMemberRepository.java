package com.twealthbook.repository;

import com.twealthbook.model.FamilyMember;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
    @Cacheable("families")
    public FamilyMember findOneByClientId(Long clinetId);
}
