insert into user(user_id, email, introduce, major, name, password, profile_url, state) values (1, 'test1@test.com', null, null, stringdecode('\uad501'), '$2a$10$9cbnbv55q2xuwkjrjkj/q.ovkslkceyz9.w.wjx42xo/e42ncoic.', null, null), (2, 'test2@test.com', null, null, stringdecode('\ud5592'), '$2a$10$goiascg/lgg.f48syxmogugdcyydym1zqdndi3pktc7jk.i8llgse', null, null);

insert into lecture(lecture_id, major_type, name, register_policy, host_user_id) values (1, 1, stringdecode('\uc790\ub8cc\uad6c\uc870'), 1, 1);

insert into user_enrolled_lecture(user_enrolled_lecture_id, approval_state, side_menu, lecture_id, user_id, user_group_id) values (1, null, true, 1, 1, null), (2, 0, true, 1, 2, null);

insert into user_group(id, default_group, name, lecture_id) values(1, false, stringdecode('\uc870\uad50'), 1),(2, true, stringdecode('\uc218\uac15\uc0dd'), 1);

insert into content_type(id, end_time, extend_write, name, only_writer, start_time, statistic, lecture_id) values(1, true, true, stringdecode('\uc218\uc5c5'), null, true, null, 1),(2, null, null, stringdecode('\uac15\uc758\uc790\ub8cc'), null, null, null, 1),(3, null, null, stringdecode('\uc9c8\ubb38'), null, null, null, 1),(4, true, null, stringdecode('\uacfc\uc81c'), true, null, true, 1);

insert into user_group_can_read_content(id, content_type_id, user_group_id) values(1, 1, 1),(2, 2, 1),(3, 3, 1),(4, 4, 1),(5, 1, 2),(6, 2, 2),(7, 3, 2),(8, 4, 2);

insert into user_group_can_write_content(id, content_type_id, user_group_id) values(1, 1, 1),(2, 2, 1),(3, 3, 1),(4, 4, 1),(5, 3, 2),(6, 4, 2);

insert into message(message_id, date, message, read, type, type_id, url, user_id) values(1, null, null, null, null, null, null, 1),(2, null, null, null, null, null, null, null),(3, null, null, null, null, null, null, null);

insert into content(content_id, body, end_time, hits, start_time, title, write_date, lecture_id, content_type, user_id) values (1, stringdecode('<p>\ub0b4\uc6a9\uc774\uc788\uc5b4\uc57c\uc81c</p>'), timestamp '2015-11-11 14:23:14.0', 5, timestamp '2015-11-01 15:24:14.0', stringdecode('\uc54c\uace0\ub9ac\uc9982'), timestamp '2015-11-19 15:24:34.601', 1, 1, 1),(2, null, timestamp '2015-11-04 16:24:58.867', null, timestamp '2015-11-04 14:24:58.867', stringdecode('\uac00\ub098\ub2e4'), timestamp '2015-11-19 15:25:20.642', 1, 1, 1),(3, null, timestamp '2015-11-11 16:24:58.867', null, timestamp '2015-11-11 14:24:58.867', stringdecode('\uac00\ub098\ub2e4'), timestamp '2015-11-19 15:25:20.647', 1, 1, 1),(4, null, timestamp '2015-11-18 16:24:58.867', null, timestamp '2015-11-18 14:24:58.867', stringdecode('\uac00\ub098\ub2e4'), timestamp '2015-11-19 15:25:20.654', 1, 1, 1),(5, null, timestamp '2015-11-25 16:24:58.867', null, timestamp '2015-11-25 14:24:58.867', stringdecode('\uac00\ub098\ub2e4'), timestamp '2015-11-19 15:25:20.661', 1, 1, 1);

insert into reply(reply_id, body, write_date, content_id, writer_id) values(1, stringdecode('\ud6c4\ud6d7'), timestamp '2015-11-19 15:27:06.497', 5, 1);

insert into user_likes_reply(id, reply_id, user_id) values(1, 1, 1);