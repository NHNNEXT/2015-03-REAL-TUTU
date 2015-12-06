package org.next.lms.content.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.next.NextLectureManagerApplication;
import org.next.lms.content.dao.ContentDao;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = NextLectureManagerApplication.class)
public class ContentDaoTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void testGetQueryingResult() {
        ContentDao dao = new ContentDao();
        dao.setKeyword("");
        System.out.println(dao.getList(entityManager));
    }
}