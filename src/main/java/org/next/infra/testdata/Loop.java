package org.next.infra.testdata;

import org.next.infra.exception.WrongAccessException;

import java.util.Iterator;
import java.util.List;

public class Loop<T> {
    private List<T> list;
    private Iterator<T> iterator;

    public Loop(List<T> list) {
        if (list.size() == 0)
            throw new WrongAccessException();
        this.list = list;
        iterator = this.list.iterator();
    }

    public <T> T next() {
        if (iterator.hasNext())
            return (T) iterator.next();
        iterator = this.list.iterator();
        return next();
    }
}
