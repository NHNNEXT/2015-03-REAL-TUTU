package org.next.justtest;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCompare {

    @Test
    public void dateCompareTest() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date day1 = null;
        Date day2 = null;
        try {
            day1 = format.parse("2012-12-16");
            day2 = format.parse("2012-12-17");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int compare = day1.compareTo(day2);

        if (compare > 0) {
            System.out.println("day1 > day2");
        } else if (compare < 0) {
            System.out.println("day1 < day2");
        } else {
            System.out.println("day1 = day2");
        }
    }
}
