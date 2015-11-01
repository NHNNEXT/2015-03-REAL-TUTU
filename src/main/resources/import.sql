INSERT INTO AUTHORITY VALUES(1, 'ROLE_NOT_AUTHORIZED');
INSERT INTO AUTHORITY VALUES(2, 'ROLE_AUTHORIZED');
INSERT INTO AUTHORITY VALUES(3, 'ROLE_SYSTEM_MANAGER');

INSERT INTO USER_INFO VALUES(1, '웹서버1', '홍길동', '010-1234-1234', NULL, '141414');
INSERT INTO USER_INFO VALUES(2, '웹서버2', '홍길동', '010-1234-1234', NULL, '141414');
INSERT INTO USER_INFO VALUES(3, '웹서버3', '홍길동', '010-1234-1234', NULL, '141414');

INSERT INTO LOGIN_ACCOUNT VALUES(1, 'test1@test.com', '$2a$10$MXqrDkWCTHiURcE1Kj6rX.D.5s.NSzIwp8iF1EpkJ.l9hV2jhxaFC', 'ACTIVE', 1);
INSERT INTO LOGIN_ACCOUNT VALUES(2, 'test2@test.com', '$2a$10$MXqrDkWCTHiURcE1Kj6rX.D.5s.NSzIwp8iF1EpkJ.l9hV2jhxaFC', 'ACTIVE', 2);
INSERT INTO LOGIN_ACCOUNT VALUES(3, 'test3@test.com', '$2a$10$MXqrDkWCTHiURcE1Kj6rX.D.5s.NSzIwp8iF1EpkJ.l9hV2jhxaFC', 'ACTIVE', 3);

INSERT INTO TERM VALUES(1, '2015-10-15', '2015-1학기', '2016-01-01')
INSERT INTO TERM VALUES(2, '2015-10-15', '2015-2학기', '2016-01-01')
INSERT INTO TERM VALUES(3, '2015-10-15', '2015-3학기', '2016-01-01')

insert into lecture (lecture_id, host_user_id, type, name, play_time, term_id) values (null, 1, 0, '자구알', '[]', 1);
insert into lecture (lecture_id, host_user_id, type, name, play_time, term_id) values (null, 2, 0, '자구알2', '[]', 1);
insert into lecture (lecture_id, host_user_id, type, name, play_time, term_id) values (null, 3, 0, '자구알3', '[]', 1);
