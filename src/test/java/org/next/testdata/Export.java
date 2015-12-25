package org.next.testdata;

import org.junit.Test;
import org.next.dbdump.ExportCSV;

public class Export {
    @Test
    public void exportTest() throws Exception {
        ExportCSV exportCSV = new ExportCSV();
        exportCSV.exe();
    }
}
