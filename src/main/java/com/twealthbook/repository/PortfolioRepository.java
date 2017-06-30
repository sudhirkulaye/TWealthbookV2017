package com.twealthbook.repository;

import com.twealthbook.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PortfolioRepository extends JpaRepository<Portfolio, Portfolio.PortfolioKey> {
    public Iterable<Portfolio> findAllByPortfolioKeyClientId(Long clientId);
}
