package com.twealthbook.repository;

import com.twealthbook.model.MutualFund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MutualFundRepository extends JpaRepository<MutualFund, MutualFund.MutualFundKey> {
    public MutualFund findOneByMutualFundKeySchemeCode(Integer schemeCode);
    public List<MutualFund> findBySchemeNameIgnoreCaseContaining(String schemeName);
}
