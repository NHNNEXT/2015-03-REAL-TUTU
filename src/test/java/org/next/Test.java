package org.next;

import java.util.ArrayList;
import java.util.List;

public class Test {
    @org.junit.Test
    public void testName() throws Exception {
        List<Integer> a = new ArrayList<>();
        a.add(1);
        //a.stream().filter(value->value.equals(1)).findFirst().get().equals(1);
        System.out.println(a.stream().filter(value -> value.equals(1)).findAny().get());

    }
}
