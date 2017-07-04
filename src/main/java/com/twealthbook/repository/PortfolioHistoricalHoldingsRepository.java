package com.twealthbook.repository;

import com.twealthbook.model.PortfolioHistoricalHoldings;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PortfolioHistoricalHoldingsRepository extends JpaRepository<PortfolioHistoricalHoldings, PortfolioHistoricalHoldings.PortfolioHistoricalHoldingsKey> {
    @Cacheable("historicalholdings")
    public Iterable<PortfolioHistoricalHoldings> findAllByPortfolioHistoricalHoldingsKeyClientIdAndPortfolioHistoricalHoldingsKeyPortfolioId(Long clinetId, int portfolioId);
}
