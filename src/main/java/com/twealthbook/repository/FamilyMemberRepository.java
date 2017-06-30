package com.twealthbook.repository;

import com.twealthbook.model.FamilyMember;
import com.twealthbook.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
    public FamilyMember findOneByClientId(Long clinetId);
}
