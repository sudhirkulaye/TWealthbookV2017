package com.twealthbook.repository;

import com.twealthbook.model.PortfolioHoldings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PortfolioHoldingsRepository extends JpaRepository<PortfolioHoldings, PortfolioHoldings.PortfolioHoldingsKey> {
    public Iterable<PortfolioHoldings> findAllByPortfolioHoldingsKeyClientIdAndPortfolioHoldingsKeyPortfolioId(Long clinetId, int portfolioId);
}
