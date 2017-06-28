package com.twealthbook.repository;


import com.twealthbook.model.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface FamilyMemebrRepository extends JpaRepository<FamilyMember, Long> {
    public FamilyMember findByClientId(Long clientId);
}
