package com.twealthbook.repository;

import com.twealthbook.model.Portfolio;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PortfolioRepository extends JpaRepository<Portfolio, Portfolio.PortfolioKey> {
    @Cacheable("portfolios")
    public Iterable<Portfolio> findAllByPortfolioKeyClientId(Long clientId);
}
