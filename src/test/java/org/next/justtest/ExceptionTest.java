package org.next.justtest;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ExceptionTest {

    @Test
    public void 인자가_InvalidDataAccessApiUsageException_발생2() throws Exception {
        Calendar c = new GregorianCalendar();
        c.set(Calendar.DATE, c.get(Calendar.DATE) - 6);
        c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date d1 = c.getTime();
        System.out.println(d1);
    }

}