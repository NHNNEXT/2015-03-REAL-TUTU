package org.next.lms.tag.control;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.next.NextLectureManagerApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = NextLectureManagerApplication.class)
public class TagDaoTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void test(){
        TagDao dao = new TagDao();
        dao.setKeyword("20");
        System.out.println(dao.getList(entityManager));
    }
}