insert into authority (authority_id,	authority_type) values(1, 'ROLE_NOT_AUTHORIZED');
insert into authority (authority_id,	authority_type) values(2, 'ROLE_AUTHORIZED');
insert into authority (authority_id,	authority_type) values(3, 'ROLE_SYSTEM_MANAGER');

insert into user_info (major, user_name, phone_number, profile_url, student_id) values('웹서버1', 'hong', '010-1234-1234', NULL, '141414');
insert into user_info (major, user_name, phone_number, profile_url, student_id) values('웹서버2', 'honggildong', '010-1234-1234', NULL, '141414');
insert into user_info (major, user_name, phone_number, profile_url, student_id) values('웹서버3', 'hwangjungmin', '010-1234-1234', NULL, '141414');
insert into user_info (major, user_name, phone_number, profile_url, student_id) values('웹서버3', 'hwangjungmin', '010-1234-1234', NULL, '141414');
insert into user_info (major, user_name, phone_number, profile_url, student_id) values('웹서버3', 'hjungmin', '010-1234-1234', NULL, '141414');
insert into user_info (major, user_name, phone_number, profile_url, student_id) values('웹버3', 'hngungmin', '010-1234-1234', NULL, '141414');

insert into login_account (login_account_id, email_id,password,user_account_state,user_info_id) values(1, 'test1@test.com', '$2a$10$60D23xM6IX1LfL0rR0oVzOK0MdWPnxJjPV8UJMuAjX5e.zgpaYzPa', 'ACTIVE', 1);
insert into login_account (login_account_id, email_id,password,user_account_state,user_info_id) values(2, 'test2@test.com', '$2a$10$MXqrDkWCTHiURcE1Kj6rX.D.5s.NSzIwp8iF1EpkJ.l9hV2jhxaFC', 'ACTIVE', 2);
insert into login_account (login_account_id, email_id,password,user_account_state,user_info_id) values(3, 'test3@test.com', '$2a$10$MXqrDkWCTHiURcE1Kj6rX.D.5s.NSzIwp8iF1EpkJ.l9hV2jhxaFC', 'ACTIVE', 3);
insert into login_account (login_account_id, email_id,password,user_account_state,user_info_id) values(4, 'test5@test.com', '$2a$10$MXqrDkWCTHiURcE1Kj6rX.D.5s.NSzIwp8iF1EpkJ.l9hV2jhxaFC', 'ACTIVE', 4);
insert into login_account (login_account_id, email_id,password,user_account_state,user_info_id) values(5, 'test7@test.com', '$2a$10$MXqrDkWCTHiURcE1Kj6rX.D.5s.NSzIwp8iF1EpkJ.l9hV2jhxaFC', 'ACTIVE', 5);
insert into login_account (login_account_id, email_id,password,user_account_state,user_info_id) values(6, 'test89@test.com', '$2a$10$MXqrDkWCTHiURcE1Kj6rX.D.5s.NSzIwp8iF1EpkJ.l9hV2jhxaFC', 'ACTIVE', 6);


insert into lecture (lecture_id, date, major_type, name, host_user_id, types) values (null,'2015-11-11', 0, '자구알',  1, '[{"name":"강의자료"},{"name":"질문"},{"name":"과제","dueDate":true}]');
insert into lecture (lecture_id, date, major_type, name, host_user_id, types) values (null,'2015-11-11', 1, '네트워크',  2, '[{"name":"강의자료"},{"name":"질문"},{"name":"과제","dueDate":true}]');
insert into lecture (lecture_id, date, major_type, name, host_user_id, types) values (null,'2015-11-11', 2, '실전프',  3, '[{"name":"강의자료"},{"name":"질문"},{"name":"과제","dueDate":true}]');
insert into lecture (lecture_id, date, major_type, name, host_user_id, types) values (null,'2015-11-11', 0, 'abc',  3, '[{"name":"강의자료"},{"name":"질문"},{"name":"과제","dueDate":true}]');
insert into lecture (lecture_id, date, major_type, name, host_user_id, types) values (null,'2015-11-11', 1, '인문사회학',  1, '[{"name":"강의자료"},{"name":"질문"},{"name":"과제","dueDate":true}]');
insert into lecture (lecture_id, date, major_type, name, host_user_id, types) values (null,'2015-11-11', 2, '그래픽스',  3, '[{"name":"강의자료"},{"name":"질문"},{"name":"과제","dueDate":true}]');

insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  1);
insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  1);
insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  1);
insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  1);
insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  2);
insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  2);
insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  2);
insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  2);
insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  3);
insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  3);
insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  3);
insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  4);
insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  4);
insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  4);
insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  4);
insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  5);
insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  5);
insert into lesson (lesson_id,description,end,name,start,lecture_id) values (null, '샘플수업','2015-10-11', '샘플강의','2015-10-09',  5);

insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_info_id) values (null, 1,1);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_info_id) values (null, 2,1);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_info_id) values (null, 3,1);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_info_id) values (null, 4,1);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_info_id) values (null, 5,1);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_info_id) values (null, 1,2);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_info_id) values (null, 3,2);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_info_id) values (null, 4,2);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_info_id) values (null, 5,2);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_info_id) values (null, 4,3);
insert into user_enrolled_lecture (user_enrolled_lecture_id, lecture_id, user_info_id) values (null, 5,3);

insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, login_account_id, type) values (null,0, '테스트', '2015-11-15', '제목', '2015-10-11', 1, 1, 0);
insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, login_account_id, type) values (null,0, '테스트', '2015-11-15', '제목', '2015-10-11', 1, 1, 1);
insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, login_account_id, type) values (null,0, '테스트', '2015-11-15', '제목', '2015-10-11', 1, 1, 2);
insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, login_account_id, type) values (null,0, '테스트', '2015-11-15', '제목', '2015-10-11', 1, 2, 0);
insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, login_account_id, type) values (null,0, '테스트', '2015-11-15', '제목', '2015-10-11', 1, 2, 1);
insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, login_account_id, type) values (null,0, '테스트', '2015-11-15', '제목', '2015-10-11', 1, 2, 0);
insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, login_account_id, type) values (null,0, '테스트', '2015-11-15', '제목', '2015-10-11', 2, 1, 0);
insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, login_account_id, type) values (null,0, '테스트', '2015-11-15', '제목', '2015-10-11', 2, 1, 1);
insert into content (content_id, hits, body, due_date, title, write_date, lecture_id, login_account_id, type) values (null,0, '테스트', '2015-11-15', '제목', '2015-10-11', 2, 1, 0);