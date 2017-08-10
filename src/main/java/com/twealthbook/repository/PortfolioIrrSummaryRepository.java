package com.twealthbook.repository;

import com.twealthbook.model.PortfolioIrrSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioIrrSummaryRepository extends JpaRepository<PortfolioIrrSummary, PortfolioIrrSummary.PortfolioIrrSummaryKey> {
    public PortfolioIrrSummary findOneByPortfolioIrrSummaryKeyClientIdAndPortfolioIrrSummaryKeyPortfolioIdAndPortfolioIrrSummaryKeyBenchmarkId
            (Long clientId, int portfolioId, int benchmarkId);
    public List<PortfolioIrrSummary> findAllByPortfolioIrrSummaryKeyClientIdAndPortfolioIrrSummaryKeyPortfolioId(Long clientId, int portfolioId);
}
