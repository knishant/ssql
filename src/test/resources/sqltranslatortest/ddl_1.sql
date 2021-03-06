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
alter table abc add CONSTRAINT abc_uk UNIQUE  (col1, cols);

--index to be used by FK
create index ABC_FK1 ON abc (colb);

create table table_234
(
    colb bigint not null
);
alter table table_234 add CONSTRAINT table_234_pk PRIMARY KEY  (colb);
--fk comment
alter TABLE abc add CONSTRAINT abc_fk1 foreign KEY (colb) REFERENCES table_234 (colb) on delete cascade;

--added in version 10
alter table abc add column col20 bigint default 0 not null;

alter table abc add constraint abc_uk2 unique (col4);
create table date_def
(
    col_pk int not null,
    col1 date DEFAULT CURRENT_DATE,
    col2 time DEFAULT CURRENT_TIME,
    col3 timestamp default current_timestamp,
    constraint date_def_pk primary key  (col_pk),
    constraint date_def_fk foreign key (col1) references ABC (col4) on delete cascade
);

create table varchar_limit
(
    col1    varchar(1),
    col10 varchar(10),
    col255 varchar(255),
    col4000 varchar(4000),
    col4100 varchar(4100),
    col8000 varchar(8000),
    col8100 varchar(8100),
    col65535 varchar(65535),
    col66000 varchar(66000)
);

---START-CUSTOM dialect=Oracle9i
create view custom_view as
select col1, col10 from varchar_limit;
---END-CUSTOM

CREATE SEQUENCE Seq_1 start with 100 minvalue 100 increment by 50 MAXVALUE 9223372036854775807 NO CYCLE no cache;

CREATE SEQUENCE Seq_2 MAXVALUE 9223372036854775807 CYCLE;

create table money_table
(
    col1m   money,
    col1n   NUMERIC(19,4),
    col2m   smallmoney,
    col2n   NUMERIC(10,4)
);
