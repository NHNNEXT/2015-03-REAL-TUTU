insert into user (major, name, phone_number, profile_url, student_id, email,password) values('webserver1', 'hong', '010-1234-1234', NULL, '141414','test1@test.com', '$2a$10$60D23xM6IX1LfL0rR0oVzOK0MdWPnxJjPV8UJMuAjX5e.zgpaYzPa');
insert into user (major, name, phone_number, profile_url, student_id, email,password) values('webserver2', 'honggildong', '010-1234-1234', NULL, '141414','test2@test.com', '$2a$10$60D23xM6IX1LfL0rR0oVzOK0MdWPnxJjPV8UJMuAjX5e.zgpaYzPa');
insert into user (major, name, phone_number, profile_url, student_id, email,password) values('webserver3', 'hwangjungmin', '010-1234-1234', NULL, '141414','test3@test.com', '$2a$10$60D23xM6IX1LfL0rR0oVzOK0MdWPnxJjPV8UJMuAjX5e.zgpaYzPa');
insert into user (major, name, phone_number, profile_url, student_id, email,password) values('webserver4', 'hwangjungmin', '010-1234-1234', NULL, '141414','test4@test.com', '$2a$10$60D23xM6IX1LfL0rR0oVzOK0MdWPnxJjPV8UJMuAjX5e.zgpaYzPa');
insert into user (major, name, phone_number, profile_url, student_id, email,password) values('webserver5', 'hjungmin', '010-1234-1234', NULL, '141414','test5@test.com', '$2a$10$60D23xM6IX1LfL0rR0oVzOK0MdWPnxJjPV8UJMuAjX5e.zgpaYzPa');
insert into user (major, name, phone_number, profile_url, student_id, email,password) values('webserver6', 'hngungmin', '010-1234-1234', NULL, '141414','test6@test.com', '$2a$10$60D23xM6IX1LfL0rR0oVzOK0MdWPnxJjPV8UJMuAjX5e.zgpaYzPa');

insert into lecture (lecture_id,  major_type, name, host_user_id) values (null, 0, 'jagual',  1);
insert into lecture (lecture_id,  major_type, name, host_user_id) values (null, 1, 'network',  2);
insert into lecture (lecture_id,  major_type, name, host_user_id) values (null, 2, 'real',  3);
insert into lecture (lecture_id,  major_type, name, host_user_id) values (null, 0, 'abcd',  3);
insert into lecture (lecture_id,  major_type, name, host_user_id) values (null, 1, 'korean',  1);
insert into lecture (lecture_id,  major_type, name, host_user_id) values (null, 2, 'graphics',  3);


insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id) values (null, 1,1);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id) values (null, 2,1);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id) values (null, 3,1);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id) values (null, 4,1);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id) values (null, 5,1);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id) values (null, 1,2);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id) values (null, 3,2);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id) values (null, 4,2);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id) values (null, 5,2);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id) values (null, 4,3);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_id) values (null, 5,3);

insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 1, 1, 0);
insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 1, 1, 1);
insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 1, 1, 2);
insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 1, 2, 0);
insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 1, 2, 1);
insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 1, 2, 0);
insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 2, 1, 0);
insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 2, 1, 1);
insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, user_id, type) values (null,0, 'test', '2015-11-15', 'title', '2015-10-11', 2, 1, 0);

insert into message (message, type, user_id, url, read) values('test', 0,1, '/content/1', 'true');
insert into message (message, type, user_id, url, read) values('test', 0,1, '/content/1', 'true');
insert into message (message, type, user_id, url, read) values('test', 0,1, '/content/1', 'true');
insert into message (message, type, user_id, url, read) values('test', 0,1, '/content/1', 'false');