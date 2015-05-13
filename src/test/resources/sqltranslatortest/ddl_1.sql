CREATE TABLE ABC
(
    col1 int not null,
    cols smallint not null,
    colb bigint not null,
    col2 VARCHAR(100),
    col2n nVARCHAR(100),
    col3 char(1) default 'T',
    col3n nchar(1),
    col4 date not null,
    col5 TIMESTAMP,
    col6 NUMERIC(10,2),
    col7 TIME default CURRENT_TIME,
    col8 clob,
    col9 blob,
    col10 float,
    col11 DOUBLE PRECISION,
    col12 real,
    col13 boolean
);
alter table abc add CONSTRAINT abc_pk PRIMARY KEY  (col1);

create index ABC_FK1 ON abc (col1b);

alter TABLE abc add CONSTRAINT abc_fk1 foreign KEY (col1b) REFERENCES ANOTHER_TABLE (col1) on delete cascade;

alter table abc add column col20 bigint default 0 not null;
