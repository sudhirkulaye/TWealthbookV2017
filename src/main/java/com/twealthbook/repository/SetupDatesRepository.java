package com.twealthbook.repository;

import com.twealthbook.model.SetupDates;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface SetupDatesRepository  extends JpaRepository<SetupDates, Date> {

    @Cacheable("setupdates")
    public List<SetupDates> findAll();

}
