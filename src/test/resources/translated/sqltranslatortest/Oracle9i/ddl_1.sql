--table level comment
CREATE TABLE ABC
(
    --col 1 comment
    COL1                           NUMBER(10,0)              NOT NULL,
    COLS                           NUMBER(5,0)               DEFAULT -1 NOT NULL,
    COLB                           NUMBER(19,0)              DEFAULT 123 NOT NULL,
    COL2                           VARCHAR2(100 CHAR)        DEFAULT 'empty',
    /*
    not a very useful default value
    testing multi-line comment
     */
    COL2N                          NVARCHAR2(100)            DEFAULT NULL,
    --col 3 comment
    COL3                           CHAR(1 CHAR)              DEFAULT 'T',
    COL3N                          NCHAR(1),
    COL4                           DATE                      NOT NULL,
    COL5                           TIMESTAMP,
    COL6                           NUMBER(10,2),
    COL7                           DATE                      DEFAULT CURRENT_TIMESTAMP,
    COL8                           CLOB,
    COL9                           BLOB,
    COL10                          FLOAT,
    COL11                          DOUBLE PRECISION          DEFAULT 2.3,
    COL12                          REAL,
    COL13                          NUMBER(1,0)
);

--pk comment
ALTER TABLE ABC ADD CONSTRAINT ABC_PK PRIMARY KEY (COL1);

--uk comment
--uk, though not really useful
ALTER TABLE ABC ADD CONSTRAINT ABC_UK UNIQUE (COL1, COLS);

--index to be used by FK
CREATE INDEX ABC_FK1 ON ABC (COLB);

CREATE TABLE TABLE_234
(
    COLB                           NUMBER(19,0)              NOT NULL
);

ALTER TABLE TABLE_234 ADD CONSTRAINT TABLE_234_PK PRIMARY KEY (COLB);

--fk comment
ALTER TABLE ABC ADD CONSTRAINT ABC_FK1 FOREIGN KEY (COLB) REFERENCES TABLE_234 (COLB) ON DELETE CASCADE;

--added in version 10
ALTER TABLE ABC ADD COL20 NUMBER(19,0)  DEFAULT 0 NOT NULL;

ALTER TABLE ABC ADD CONSTRAINT ABC_UK2 UNIQUE (COL4);

CREATE TABLE DATE_DEF
(
    COL_PK                         NUMBER(10,0)              NOT NULL,
    COL1                           DATE                      DEFAULT CURRENT_DATE,
    COL2                           DATE                      DEFAULT CURRENT_TIMESTAMP,
    COL3                           TIMESTAMP                 DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT DATE_DEF_PK PRIMARY KEY (COL_PK),
    CONSTRAINT DATE_DEF_FK FOREIGN KEY (COL1) REFERENCES ABC (COL4) ON DELETE CASCADE
);

CREATE TABLE VARCHAR_LIMIT
(
    COL1                           VARCHAR2(1 CHAR),
    COL10                          VARCHAR2(10 CHAR),
    COL255                         VARCHAR2(255 CHAR),
    COL4000                        VARCHAR2(4000 CHAR),
    COL4100                        CLOB,
    COL8000                        CLOB,
    COL8100                        CLOB,
    COL65535                       CLOB,
    COL66000                       CLOB
);


create view custom_view as
select col1, col10 from varchar_limit;

CREATE SEQUENCE SEQ_1 START WITH 100 INCREMENT BY 50 MINVALUE 100 MAXVALUE 9223372036854775807 NOCYCLE NOCACHE;

CREATE SEQUENCE SEQ_2 MAXVALUE 9223372036854775807 CYCLE;

CREATE TABLE MONEY_TABLE
(
    COL1M                          NUMBER(19,4),
    COL1N                          NUMBER(19,4),
    COL2M                          NUMBER(10,4),
    COL2N                          NUMBER(10,4)
);

