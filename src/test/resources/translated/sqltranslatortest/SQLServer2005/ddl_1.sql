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
    COL4                           DATETIME                  NOT NULL,
    COL5                           DATETIME,
    COL6                           NUMERIC(10,2),
    COL7                           DATETIME                  DEFAULT GETDATE(),
    COL8                           VARCHAR(MAX),
    COL9                           VARBINARY(MAX),
    COL10                          FLOAT,
    COL11                          DOUBLE PRECISION          DEFAULT 2.3,
    COL12                          REAL,
    COL13                          BIT
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
    COL1                           DATETIME                  DEFAULT GETDATE(),
    COL2                           DATETIME                  DEFAULT GETDATE(),
    COL3                           DATETIME                  DEFAULT GETDATE()
);

