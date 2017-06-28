package com.twealthbook.repository;

import com.twealthbook.model.Portfolio;
import com.twealthbook.model.PortfolioKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, PortfolioKey> {
    public Portfolio findByPortfolioKey(PortfolioKey portfolioKey);
    public Iterable<Portfolio> findAllByPortfolioKeyClientId(Long clientId);
}
