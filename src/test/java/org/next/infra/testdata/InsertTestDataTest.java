package org.next.infra.testdata;

import org.apache.commons.io.IOUtils;
import org.h2.tools.DeleteDbFiles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.io.IOUtil;
import org.next.NextLectureManagerApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;

import static org.junit.Assert.*;


public class InsertTestDataTest {

    @Test
    public void contextLoads() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/NEXT-LectureManager", "sa", "");
        Statement stat = conn.createStatement();

        stat.execute("create table test(id int primary key, name varchar(255))");
        stat.execute("insert into test values(1, 'Hello')");
        ResultSet rs;
        rs = stat.executeQuery("select * from test");
        while (rs.next()) {
            System.out.println(rs.getString("name"));
        }
        stat.close();
        conn.close();
    }
}