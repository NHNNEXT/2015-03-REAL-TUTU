package org.next.infra.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class HanGuelUtilTest {

    @Test
    public void testJosa() throws Exception {
        System.out.println(HanGuelUtil.josa("한글", HanGuelUtil.JosaType.을를));
        System.out.println(HanGuelUtil.josa("태호", HanGuelUtil.JosaType.을를));
    }

    @Test
    public void testJosaWhenEllipsisApplied() throws Exception {

    }

    @Test
    public void testisHangle() throws Exception {
        assertTrue(HanGuelUtil.isHangle('한'));
        assertFalse(HanGuelUtil.isHangle('a'));
    }

    @Test
    public void containsJongSung() throws Exception {
        assertTrue(HanGuelUtil.containsJongSung('한'));
        assertFalse(HanGuelUtil.containsJongSung('호'));
    }

    @Test
    public void extract() throws Exception {
        char test = '같';
        System.out.println(HanGuelUtil.extractChoSung(test));
        System.out.println(HanGuelUtil.extractJungSung(test));
        System.out.println(HanGuelUtil.extractJongSung(test));
    }
}