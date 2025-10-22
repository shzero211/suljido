-- 이 줄을 추가!
DROP TABLE IF EXISTS article; -- article 테이블이 member를 참조하므로 먼저 삭제
DROP TABLE IF EXISTS MEMBER; -- member 테이블 삭제

create table MEMBER (
id INTEGER AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(128) NOT NULL,
email VARCHAR(256) NOT NULL unique,
age INTEGER
);

create table article(
id integer auto_increment primary key,
title varchar(256),
description varchar(4096),
created datetime,
updated datetime,
member_id integer,
foreign key(member_id) references member(id) on delete cascade
)