package com.twealthbook.repository;

import com.twealthbook.model.PortfolioHistoricalHoldings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PortfolioHistoricalHoldingsRepository extends JpaRepository<PortfolioHistoricalHoldings, PortfolioHistoricalHoldings.PortfolioHistoricalHoldingsKey> {
    public Iterable<PortfolioHistoricalHoldings> findAllByPortfolioHistoricalHoldingsKeyClientIdAndPortfolioHistoricalHoldingsKeyPortfolioId(Long clinetId, int portfolioId);
}
