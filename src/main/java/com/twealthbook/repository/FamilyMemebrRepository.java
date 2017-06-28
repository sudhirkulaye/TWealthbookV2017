package com.twealthbook.repository;


import com.twealthbook.model.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyMemebrRepository extends JpaRepository<FamilyMember, Long> {
    public FamilyMember findByClientId(Long clientId);
}
