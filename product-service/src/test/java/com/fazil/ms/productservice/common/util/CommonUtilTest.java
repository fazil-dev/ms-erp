package com.fazil.ms.productservice.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class CommonUtilTest {

    CommonUtil commonUtil = new CommonUtil();
    @BeforeAll
    public static void beforeAll() {
        log.info("Before All method started");
    }

    @AfterAll
    public static void afterAll() {
        log.info("After All method started");
    }

    @BeforeEach
    void setUp() {
        log.info("Before each setup method started");
    }

    @AfterEach
    void tearDown() {
        log.info("After each tearDown method started");
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class CheckLeapYear {
        @ParameterizedTest(name = "{0} Is Leap Year")
        @ValueSource(ints = {2020, 2016})
        void isLeapYear(int year) {
            log.info("testing isLeapYear method started ");
//            assertAll(
//                    () -> assertTrue(commonUtil.isLeapYear(2016)),
//                    () -> assertThrows(InvalidParameterException.class, () -> commonUtil.isLeapYear(-11))
//            );
            assertTrue(commonUtil.isLeapYear(year));
        }

        @ParameterizedTest(name = "{0} Is Input Invalid")
        @ValueSource(ints = {-10, -5})
        void isLeapYearIsInvalid(int year) {
            log.info("testing isLeapYearIsInvalid method started ");
            assertThrows(InvalidParameterException.class, () -> commonUtil.isLeapYear(year));
        }
    }

    @Test
    void getCurrentDate() {
        log.info("testing getCurrentDate  method started");

    }

    @Test
    void addDaysWithCurrentDate() {
        log.info("testing addDaysWithCurrentDate  method started");
    }

    @Test
    void getNumberOfDaysBetweenDates() {
        log.info("testing getNumberOfDaysBetweenDates  method started");
    }
}