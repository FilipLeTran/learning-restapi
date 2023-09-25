package com.infrasight.kodtest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateConvertor {

    private final Date date;
    private final GregorianCalendar cal = new GregorianCalendar();

    public DateConvertor(long dateValue) {
        //SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.cal.setTimeInMillis(dateValue * 1000);
        this.date = new Date(dateValue);

        //this.date = originalFormat.format(dateObject);
    }

    public boolean isBetween(int first, int second, int value) {
        return first <= value && value <= second;
    }

    public int getYear() {
        return this.cal.get(Calendar.YEAR);
    }

    public int getMonth() {
        return this.cal.get(Calendar.MONTH) + 1;
    }

    public int getDay() {
        return this.cal.get(Calendar.DAY_OF_MONTH);
    }

    public String getDate() {
        return "" + getYear() + "-" + getMonth() + "-" + getDay();
    }
}
