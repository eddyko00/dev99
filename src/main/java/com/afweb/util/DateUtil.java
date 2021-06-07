/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afweb.util;

import com.afweb.service.ServiceAFweb;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;

/**
 *
 * @author eddyko
 */
public class DateUtil {

    // Monday = 1
    // Sunday = 7
    public static int getDateOfWeekNow() {
//    LocalDate date = LocalDate.of(2017, Month.JANUARY, 31);  
        LocalDate date = LocalDate.now();
        DayOfWeek day = DayOfWeek.from(date);
//        System.out.println(day.getValue());
        return day.getValue();
    }

    // 24 hr
    // 9:00 pm = 21
    public static int getHourNow() {
        LocalTime now = LocalTime.now();
//        System.out.println(now.getHour());
        return now.getHour();
    }
 
    public static boolean isMarketOpen() {
        int hr = getHourNow();
        int dayOfW = getDateOfWeekNow();
        boolean mkopen = false;
        if (hr < 21) { // < 9:00 pm) {
            if (hr > 8) {  // > 8:00 pm
                if (dayOfW <= 5) {
                    mkopen = true;
                }
            }
        }
        if (ServiceAFweb.forceMarketOpen == true) {
            mkopen = true;
        }
        return mkopen;
    }
///////////////

    public static long getFirstDayCurrentYear() {

        LocalDate Dateloc = LocalDate.now();
        LocalDate firstDayOfYear = Dateloc.with(TemporalAdjusters.firstDayOfYear());
        Date retDate = Date.valueOf(firstDayOfYear);
        return retDate.getTime();
    }

}
