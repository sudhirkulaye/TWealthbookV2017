package com.twealthbook.repository;

import com.twealthbook.model.PortfolioTwrrSummary;
import com.twealthbook.model.PortfolioTwrrSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioTwrrSummaryRepository extends JpaRepository<PortfolioTwrrSummary, PortfolioTwrrSummary.PortfolioTwrrSummaryKey> {
    public PortfolioTwrrSummary findOneByPortfolioTwrrSummaryKeyClientIdAndPortfolioTwrrSummaryKeyPortfolioIdAndPortfolioTwrrSummaryKeyBenchmarkId
            (Long clientId, int portfolioId, int benchmarkId);
    public List<PortfolioTwrrSummary> findAllByPortfolioTwrrSummaryKeyClientIdAndPortfolioTwrrSummaryKeyPortfolioId(Long clientId, int portfolioId);
}
