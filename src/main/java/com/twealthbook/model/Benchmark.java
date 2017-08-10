package com.twealthbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "benchmark")
public class Benchmark {
    private  int benchmarkId;
    private String benchmarkName;
    private String benchmarkDescription;

    @Id
    @Column(name = "benchmark_id")
    public int getBenchmarkId() {
        return benchmarkId;
    }
    public void setBenchmarkId(int benchmarkId) {
        this.benchmarkId = benchmarkId;
    }

    @Column(name = "benchmark_name")
    public String getBenchmarkName() {
        return benchmarkName;
    }
    public void setBenchmarkName(String benchmarkName) {
        this.benchmarkName = benchmarkName;
    }

    @Column(name = "benchmark_description")
    public String getBenchmarkDescription() {
        return benchmarkDescription;
    }
    public void setBenchmarkDescription(String benchmarkDescription) {
        this.benchmarkDescription = benchmarkDescription;
    }
}
