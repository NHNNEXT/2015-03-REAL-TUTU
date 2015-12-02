package org.next.lms.tag.control;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.next.NextLectureManagerApplication;
import org.next.lms.tag.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = NextLectureManagerApplication.class)
public class TagDaoTest {

    @Autowired
    TagDao tagDao;

    @Test
    public void testGetTags() throws Exception {
        List<String> tags = tagDao.getTags("2015");
        assertEquals(tags.size(), 1);
    }
}