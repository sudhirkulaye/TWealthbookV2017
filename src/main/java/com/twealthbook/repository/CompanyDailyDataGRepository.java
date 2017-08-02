package com.twealthbook.repository;

import com.twealthbook.model.CompanyDailyDataG;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyDailyDataGRepository extends JpaRepository<CompanyDailyDataG, CompanyDailyDataG.CompanyDailyDataGKey> {
    public List<CompanyDailyDataG> findByCompanyShortNameIgnoreCaseContainingAndCompanyDailyDataGKeyCompanyDailyDate(String companyShortName, java.sql.Date date);
    public CompanyDailyDataG findOneByCompanyDailyDataGKeyCompanyTicker(String companyTicker);
}
