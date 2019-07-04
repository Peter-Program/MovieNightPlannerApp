package com.example.movienightplanner;

import android.util.Log;

public class EventDateTime {
    int day;
    int month;
    int year;
    int hour;
    int minute;

    public EventDateTime(int day, int month, int year, int hour, int minute) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
    }

    public EventDateTime() {

    }

    private String hourToString() {
        String hr;
        if (hour < 10)
            hr = "0" + hour;
        else
            hr = "" + hour;

        return hr;
    }

    private String minuteToString() {
        String min;
        if (minute < 10)
            min = "0" + minute;
        else
            min = "" + minute;
        return min;
    }

    private String monthToString() {
        String mon;
        int trueMonth = month + 1;
        if (trueMonth < 10)
            mon = "0" + trueMonth;
        else
            mon = "" + trueMonth;
        return mon;
    }

    public String toStringDate() {
        String s = " " + day + "-" + monthToString() + "-" + year;
        return s;
    }

    public String toStringTime() {
        // adjust for if hour is < 10 and same for minute
        String s = " " + hourToString() + ":" + minuteToString();
        return s;
    }

    public boolean laterThen(EventDateTime e) {
        Long endTime = getEntireDateTime();
        Long startTime = e.getEntireDateTime();
        if ((endTime == Long.valueOf(0)) || (startTime == Long.valueOf(0))
                ||getEntireDateTime() <= e.getEntireDateTime())
            return false;
        else
            return true;
    }

    public Long getEntireDateTime() {
        String s = "";
        s = s.concat("" + year);
        s = s.concat("" + day);
        s = s.concat(monthToString());
        s = s.concat(hourToString());
        s = s.concat(minuteToString());

        if (year == 0)
            // No date has Been Selected
            return Long.valueOf(0);

        Long entireDateTime = Long.parseLong(s);

        Log.i("EventDateTime" , "DateTime: " + entireDateTime);
        return entireDateTime;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
