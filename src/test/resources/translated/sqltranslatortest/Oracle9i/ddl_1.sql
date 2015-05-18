--table level comment
CREATE TABLE ABC
(
    --col 1 comment
    COL1                           NUMBER(10,0)              NOT NULL,
    COLS                           NUMBER(5,0)               NOT NULL,
    COLB                           NUMBER(19,0)              NOT NULL,
    COL2                           VARCHAR2(100 CHAR),
    COL2N                          NVARCHAR(100),
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
    COL11                          DOUBLE PRECISION,
    COL12                          REAL,
    COL13                          NUMBER(1,0)
);

--pk comment
ALTER TABLE ABC ADD CONSTRAINT ABC_PK PRIMARY KEY (COL1);

--uk comment
--uk, though not really useful
ALTER TABLE ABC ADD CONSTRAINT ABC_PK UNIQUE (COL1, COLS);

--index to be used by FK
CREATE INDEX ABC_FK1 ON ABC (COL1B);

CREATE TABLE TABLE_234
(
    COLB                           NUMBER(19,0)              NOT NULL
);

ALTER TABLE ABC ADD CONSTRAINT TABLE_234_PK PRIMARY KEY (COLB);

--fk comment
ALTER TABLE ABC ADD CONSTRAINT ABC_FK1 FOREIGN KEY (COLB) REFERENCES TABLE_234 (COLB) ON DELETE CASCADE;

--added in version 10
ALTER TABLE ABC ADD COL20 NUMBER(19,0)  DEFAULT 0 NOT NULL;

CREATE TABLE DATE_DEF
(
    COL1                           DATE                      DEFAULT CURRENT_DATE,
    COL2                           DATE                      DEFAULT CURRENT_TIMESTAMP,
    COL3                           TIMESTAMP                 DEFAULT CURRENT_TIMESTAMP
);

