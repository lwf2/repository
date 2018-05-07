create table PERSON
(
  id      NUMBER not null,
  name    VARCHAR2(20),
  age     NUMBER,
  nation  VARCHAR2(20),
  address VARCHAR2(20)
)

create sequence HIBERNATE_SEQUENCE
minvalue 0
maxvalue 9223372036854775807
start with 21
increment by 1
cache 20;
