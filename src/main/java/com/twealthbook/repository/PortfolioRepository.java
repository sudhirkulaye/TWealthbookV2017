package com.twealthbook.repository;

import com.twealthbook.model.Portfolio;
import com.twealthbook.model.PortfolioKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PortfolioRepository extends JpaRepository<Portfolio, PortfolioKey> {
    public Portfolio findByPortfolioKey(PortfolioKey portfolioKey);
    public Iterable<Portfolio> findAllByPortfolioKeyClientId(Long clientId);
}
