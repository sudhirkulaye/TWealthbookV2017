package com.twealthbook.repository;

import com.twealthbook.model.SetupDates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;

public interface SetupDatesRepository  extends JpaRepository<SetupDates, Date> {

}
