package com.twealthbook.repository;

import com.twealthbook.model.Benchmark;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BenchmarkRepository extends JpaRepository<Benchmark, Integer> {
    @Cacheable("benchmark")
    public  Benchmark findOneByBenchmarkId(int benchmarkId);
}
