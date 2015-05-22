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
ALTER TABLE ABC ADD CONSTRAINT ABC_UK UNIQUE (COL1, COLS);

--index to be used by FK
CREATE INDEX ABC_FK1 ON ABC (COLB);

CREATE TABLE TABLE_234
(
    COLB                           BIGINT                    NOT NULL
);

ALTER TABLE TABLE_234 ADD CONSTRAINT TABLE_234_PK PRIMARY KEY (COLB);

--fk comment
ALTER TABLE ABC ADD CONSTRAINT ABC_FK1 FOREIGN KEY (COLB) REFERENCES TABLE_234 (COLB) ON DELETE CASCADE;

--added in version 10
ALTER TABLE ABC ADD COLUMN COL20 BIGINT  DEFAULT 0 NOT NULL;

ALTER TABLE ABC ADD CONSTRAINT ABC_UK2 UNIQUE (COL4);

CREATE TABLE DATE_DEF
(
    COL_PK                         INT                       NOT NULL,
    COL1                           DATETIME                  DEFAULT GETDATE(),
    COL2                           DATETIME                  DEFAULT GETDATE(),
    COL3                           DATETIME                  DEFAULT GETDATE(),
    CONSTRAINT DATE_DEF_PK PRIMARY KEY (COL_PK),
    CONSTRAINT DATE_DEF_FK FOREIGN KEY (COL1) REFERENCES ABC (COL4) ON DELETE CASCADE
);

CREATE TABLE VARCHAR_LIMIT
(
    COL1                           VARCHAR(1),
    COL10                          VARCHAR(10),
    COL255                         VARCHAR(255),
    COL4000                        VARCHAR(4000),
    COL4100                        VARCHAR(4100),
    COL8000                        VARCHAR(8000),
    COL8100                        VARCHAR(MAX),
    COL65535                       VARCHAR(MAX),
    COL66000                       VARCHAR(MAX)
);

