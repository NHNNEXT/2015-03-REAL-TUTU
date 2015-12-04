package org.next.lms.search.control;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.next.NextLectureManagerApplication;
import org.next.infra.repository.UserRepository;
import org.next.infra.result.Result;
import org.next.lms.content.domain.Content;
import org.next.lms.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = NextLectureManagerApplication.class)
public class searchControllerTest {

    @Autowired
    SearchController searchController;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testGet() throws Exception {
        User user = userRepository.findOne(1L);
        Result a = searchController.searchInMyLectureContents("", user);
        System.out.println();
    }
}