package org.next.justtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.next.NextLectureManagerApplication;
import org.next.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class URIEncodeDecodeTest {

    @Test
    public void urlEncodeDecodeTest() throws Exception {
        String encoded = URLEncoder.encode("/lecture/%d?tab=request", "UTF-8");
        System.out.println(encoded);

        encoded = URLDecoder.decode("%3d", "UTF-8");
        System.out.println(encoded);

        encoded = URLDecoder.decode("%3F", "UTF-8");
        System.out.println(encoded);
    }
}