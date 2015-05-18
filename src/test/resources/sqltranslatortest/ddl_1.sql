--table level comment
CREATE TABLE ABC
(
    --col 1 comment
    col1 int not null,
    cols smallint default -1 not null,
    colb bigint default 123 not null,
    col2 VARCHAR(100) default 'empty',
    /*
    not a very useful default value
    testing multi-line comment
     */
    col2n nVARCHAR(100) DEFAULT null,
    --col 3 comment
    col3 char(1) default 'T',
    col3n nchar(1),
    col4 date not null,
    col5 TIMESTAMP,
    col6 NUMERIC(10,2),
    col7 TIME default CURRENT_TIME,
    col8 clob,
    col9 blob,
    col10 float,
    col11 DOUBLE PRECISION default 2.3,
    col12 real,
    col13 boolean
);
--pk comment

alter table abc add CONSTRAINT abc_pk PRIMARY KEY  (col1);
--uk comment
--uk, though not really useful
alter table abc add CONSTRAINT abc_pk UNIQUE  (col1, cols);

--index to be used by FK
create index ABC_FK1 ON abc (col1b);

create table table_234
(
    colb bigint not null
);
alter table abc add CONSTRAINT table_234_pk PRIMARY KEY  (colb);
--fk comment
alter TABLE abc add CONSTRAINT abc_fk1 foreign KEY (colb) REFERENCES table_234 (colb) on delete cascade;

--added in version 10
alter table abc add column col20 bigint default 0 not null;

create table date_def
(
    col1 date DEFAULT CURRENT_DATE,
    col2 time DEFAULT CURRENT_TIME,
    col3 timestamp default current_timestamp
);