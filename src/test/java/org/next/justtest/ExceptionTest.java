package org.next.justtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.next.NextLectureManagerApplication;
import org.next.infra.repository.UserRepository;
import org.next.lms.tag.domain.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = NextLectureManagerApplication.class)
public class ExceptionTest {

    @Autowired
    private UserRepository userRepository;

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void 인자가_널이면_InvalidDataAccessApiUsageException_발생1() throws Exception {
        userRepository.getOne(null);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void 인자가_널이면_InvalidDataAccessApiUsageException_발생2() throws Exception {
        userRepository.findOne(null);
    }

}