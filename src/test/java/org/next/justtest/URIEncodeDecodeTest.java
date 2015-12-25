package org.next.justtest;

import org.junit.Test;

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