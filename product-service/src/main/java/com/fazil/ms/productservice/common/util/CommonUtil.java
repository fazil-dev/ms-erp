package com.fazil.ms.productservice.common.util;

import lombok.extern.slf4j.Slf4j;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Slf4j
public class CommonUtil {
    public boolean isLeapYear(int year) {
        log.info("isLeapYear method for input {}.", year);
        if (year < 0) {
            throw new InvalidParameterException("Year should not be less than 0");
        }
        return year % 4 == 0;
    }

    public String getCurrentDate() {
        log.info("getCurrentDate method ");
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String addDaysWithCurrentDate(int numberOfDays) {
        log.info("addDaysWithCurrentDate method for input {}.", numberOfDays);
        return LocalDate.now().plusDays(numberOfDays).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public long getNumberOfDaysBetweenDates(LocalDate date1, LocalDate date2) {
        log.info("getNumberOfDaysBetweenDates method for input {} and {}.", date1.toString(), date2.toString());
        return ChronoUnit.DAYS.between(date1, date2);
    }


}
