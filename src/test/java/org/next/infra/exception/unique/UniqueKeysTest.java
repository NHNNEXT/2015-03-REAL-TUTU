package org.next.infra.exception.unique;

import org.junit.Test;

import static org.junit.Assert.*;

public class UniqueKeysTest {

    @Test
    public void testExtract() throws Exception {
        assertEquals(UniqueKeys.getErrorMessage("Duplicate entry '실전 프로젝트' for key 'LECTURE_NAME_ALREADY_EXIST'"), "이미 존재하는 강의명입니다.");
    }
}