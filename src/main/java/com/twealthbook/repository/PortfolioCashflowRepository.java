package com.twealthbook.repository;

import com.twealthbook.model.PortfolioCashflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.sql.Date;

@RepositoryRestResource
public interface PortfolioCashflowRepository extends JpaRepository<PortfolioCashflow, Date> {
    public Iterable<PortfolioCashflow> findAllByPortfolioCashflowKeyClientIdAndPortfolioCashflowKeyPortfolioId(Long clinetId, int portfolioId);
}
