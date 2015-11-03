package org.next;

import org.next.infra.util.CommonUtils;
import org.next.lms.lecture.domain.Lesson;

public class Test {
    @org.junit.Test
    public void testName() throws Exception {
        System.out.println(CommonUtils.parseList(Lesson.class, "[{\"start\":\"2015-11-13T00:05:00.000Z\"},{},{}]").get(0));

    }
}
