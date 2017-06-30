package com.twealthbook.repository;

import com.twealthbook.model.PortfolioCashflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PortfolioCashflowRepository extends JpaRepository<PortfolioCashflow, PortfolioCashflow.PortfolioCashflowKey> {
    public Iterable<PortfolioCashflow> findAllByportfolioCashflowKeyClientIdAndPortfolioCashflowKeyPortfolioId(Long clinetId, int portfolioId);
}
