--table level comment
CREATE TABLE ABC
(
    --col 1 comment
    COL1                           INT                       NOT NULL,
    COLS                           SMALLINT                  DEFAULT -1 NOT NULL,
    COLB                           BIGINT                    DEFAULT 123 NOT NULL,
    COL2                           VARCHAR(100)              DEFAULT 'empty',
    /*
    not a very useful default value
    testing multi-line comment
     */
    COL2N                          NVARCHAR(100)             DEFAULT NULL,
    --col 3 comment
    COL3                           CHAR(1)                   DEFAULT 'T',
    COL3N                          NCHAR(1),
    COL4                           DATE                      NOT NULL,
    COL5                           TIMESTAMP,
    COL6                           NUMERIC(10,2),
    COL7                           TIME                      DEFAULT CURRENT_TIME,
    COL8                           CLOB,
    COL9                           BLOB,
    COL10                          FLOAT,
    COL11                          DOUBLE PRECISION          DEFAULT 2.3,
    COL12                          REAL,
    COL13                          BOOLEAN
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
    COLB                           BIGINT                    NOT NULL
);

ALTER TABLE ABC ADD CONSTRAINT TABLE_234_PK PRIMARY KEY (COLB);

--fk comment
ALTER TABLE ABC ADD CONSTRAINT ABC_FK1 FOREIGN KEY (COLB) REFERENCES TABLE_234 (COLB) ON DELETE CASCADE;

--added in version 10
ALTER TABLE ABC ADD COLUMN COL20 BIGINT  DEFAULT 0 NOT NULL;

CREATE TABLE DATE_DEF
(
    COL1                           DATE                      DEFAULT CURRENT_DATE,
    COL2                           TIME                      DEFAULT CURRENT_TIME,
    COL3                           TIMESTAMP                 DEFAULT CURRENT_TIMESTAMP
);

