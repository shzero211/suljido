insert into member(name,email,age) values('김상훈','shzero211@naver.com',10);
insert into member(name,email,age) values('기문수','gimunsu@naver.com',43);

insert into article(title,description,created,updated,member_id)
values('첫번쨰 제목','첫번쨰 본문',current_timestamp,current_timestamp,1 );
insert into article(title,description,created,updated,member_id)
values('두번쨰 제목','두번쨰 본문',current_timestamp,current_timestamp,2);
