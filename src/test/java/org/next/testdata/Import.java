package org.next.testdata;

import org.junit.Test;
import org.next.dbdump.ImportCSV;

public class Import {
    @Test
    public void importTest() throws Exception {
        ImportCSV importCSV = new ImportCSV(true);
        importCSV.exe();
    }
}
