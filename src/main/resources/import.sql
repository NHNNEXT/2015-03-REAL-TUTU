insert into user (major, name, phone_number, profile_url, student_id, email,password) values('webserver1', 'hong', '010-1234-1234', NULL, '141414','test1@test.com', '$2a$10$60D23xM6IX1LfL0rR0oVzOK0MdWPnxJjPV8UJMuAjX5e.zgpaYzPa');
insert into user (major, name, phone_number, profile_url, student_id, email,password) values('webserver2', 'honggildong', '010-1234-1234', NULL, '141414','test2@test.com', '$2a$10$60D23xM6IX1LfL0rR0oVzOK0MdWPnxJjPV8UJMuAjX5e.zgpaYzPa');
insert into user (major, name, phone_number, profile_url, student_id, email,password) values('webserver3', 'hwangjungmin', '010-1234-1234', NULL, '141414','test3@test.com', '$2a$10$60D23xM6IX1LfL0rR0oVzOK0MdWPnxJjPV8UJMuAjX5e.zgpaYzPa');
insert into user (major, name, phone_number, profile_url, student_id, email,password) values('webserver4', 'hwangjungmin', '010-1234-1234', NULL, '141414','test4@test.com', '$2a$10$60D23xM6IX1LfL0rR0oVzOK0MdWPnxJjPV8UJMuAjX5e.zgpaYzPa');
insert into user (major, name, phone_number, profile_url, student_id, email,password) values('webserver5', 'hjungmin', '010-1234-1234', NULL, '141414','test5@test.com', '$2a$10$60D23xM6IX1LfL0rR0oVzOK0MdWPnxJjPV8UJMuAjX5e.zgpaYzPa');
insert into user (major, name, phone_number, profile_url, student_id, email,password) values('webserver6', 'hngungmin', '010-1234-1234', NULL, '141414','test6@test.com', '$2a$10$60D23xM6IX1LfL0rR0oVzOK0MdWPnxJjPV8UJMuAjX5e.zgpaYzPa');

insert into lecture (lecture_id,  major_type, name, host_user_id, register_policy) values (null, 0, 'jagual',  1, 1);
insert into lecture (lecture_id,  major_type, name, host_user_id, register_policy) values (null, 1, 'network',  2, 0);
insert into lecture (lecture_id,  major_type, name, host_user_id, register_policy) values (null, 2, 'real',  3, 1);
insert into lecture (lecture_id,  major_type, name, host_user_id, register_policy) values (null, 0, 'abcd',  3, 0);

insert into user_group (DEFAULT_GROUP, NAME, 	LECTURE_ID) values (true,'sub', 1);
insert into user_group (DEFAULT_GROUP, NAME, 	LECTURE_ID) values (false,'stu', 1);
insert into user_group (DEFAULT_GROUP, NAME, 	LECTURE_ID) values (true,'sub', 2);
insert into user_group (DEFAULT_GROUP, NAME, 	LECTURE_ID) values (false,'stu', 2);
insert into user_group (DEFAULT_GROUP, NAME, 	LECTURE_ID) values (true,'sub', 3);
insert into user_group (DEFAULT_GROUP, NAME, 	LECTURE_ID) values (false,'stu', 3);
insert into user_group (DEFAULT_GROUP, NAME, 	LECTURE_ID) values (true,'sub', 4);
insert into user_group (DEFAULT_GROUP, NAME, 	LECTURE_ID) values (false,'stu', 4);

insert into content_type (END_TIME,  	EXTEND_WRITE,  	NAME,  	ONLY_WRITER,  	START_TIME,  	STATISTIC,  	LECTURE_ID) values (true, true, '수업', false, true, false, 1);
insert into content_type (END_TIME,  	EXTEND_WRITE,  	NAME,  	ONLY_WRITER,  	START_TIME,  	STATISTIC,  	LECTURE_ID) values (false, false, '강의자료', false, false, false, 1);
insert into content_type (END_TIME,  	EXTEND_WRITE,  	NAME,  	ONLY_WRITER,  	START_TIME,  	STATISTIC,  	LECTURE_ID) values (true, false, '과제', false, false, false, 1);
insert into content_type (END_TIME,  	EXTEND_WRITE,  	NAME,  	ONLY_WRITER,  	START_TIME,  	STATISTIC,  	LECTURE_ID) values (true, true, '수업', false, true, false, 2);
insert into content_type (END_TIME,  	EXTEND_WRITE,  	NAME,  	ONLY_WRITER,  	START_TIME,  	STATISTIC,  	LECTURE_ID) values (false, false, '강의자료', false, false, false, 2);
insert into content_type (END_TIME,  	EXTEND_WRITE,  	NAME,  	ONLY_WRITER,  	START_TIME,  	STATISTIC,  	LECTURE_ID) values (true, false, '과제', false, false, false, 2);
insert into content_type (END_TIME,  	EXTEND_WRITE,  	NAME,  	ONLY_WRITER,  	START_TIME,  	STATISTIC,  	LECTURE_ID) values (true, true, '수업', false, true, false, 3);
insert into content_type (END_TIME,  	EXTEND_WRITE,  	NAME,  	ONLY_WRITER,  	START_TIME,  	STATISTIC,  	LECTURE_ID) values (false, false, '강의자료', false, false, false, 3);
insert into content_type (END_TIME,  	EXTEND_WRITE,  	NAME,  	ONLY_WRITER,  	START_TIME,  	STATISTIC,  	LECTURE_ID) values (true, false, '과제', false, false, false, 3);
insert into content_type (END_TIME,  	EXTEND_WRITE,  	NAME,  	ONLY_WRITER,  	START_TIME,  	STATISTIC,  	LECTURE_ID) values (true, true, '수업', false, true, false, 4);
insert into content_type (END_TIME,  	EXTEND_WRITE,  	NAME,  	ONLY_WRITER,  	START_TIME,  	STATISTIC,  	LECTURE_ID) values (false, false, '강의자료', false, false, false, 4);
insert into content_type (END_TIME,  	EXTEND_WRITE,  	NAME,  	ONLY_WRITER,  	START_TIME,  	STATISTIC,  	LECTURE_ID) values (true, false, '과제', false, false, false, 4);

insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id, approval_state) values (null, 1,1, 0);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id, approval_state) values (null, 2,1, 1);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id, approval_state) values (null, 3,1, 0);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id, approval_state) values (null, 4,1, 1);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id, approval_state) values (null, 1,2, 0);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id, approval_state) values (null, 3,2, 1);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id, approval_state) values (null, 4,2, 0);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id, approval_state) values (null, 4,3, 1);

insert into content (content_id, hits, body, end_time, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 1, 1, 0);
insert into content (content_id, hits, body, end_time, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 1, 1, 1);
insert into content (content_id, hits, body, end_time, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 1, 1, 2);
insert into content (content_id, hits, body, end_time, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 1, 2, 0);
insert into content (content_id, hits, body, end_time, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 1, 2, 1);
insert into content (content_id, hits, body, end_time, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 1, 2, 0);
insert into content (content_id, hits, body, end_time, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 2, 1, 0);
insert into content (content_id, hits, body, end_time, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 2, 1, 1);
insert into content (content_id, hits, body, end_time, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 2, 1, 0);

insert into message (message, type, user_id, url, read) values('test', 0,1, '/content/1', 'true');
insert into message (message, type, user_id, url, read) values('test', 0,1, '/content/1', 'true');
insert into message (message, type, user_id, url, read) values('test', 0,1, '/content/1', 'true');
insert into message (message, type, user_id, url, read) values('test', 0,1, '/content/1', 'false');