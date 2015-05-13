CREATE TABLE ABC
(
    COL1                           NUMBER(10,0)              NOT NULL,
    COLS                           NUMBER(5,0)               NOT NULL,
    COLB                           NUMBER(19,0)              NOT NULL,
    COL2                           VARCHAR2(100 CHAR),
    COL2N                          NVARCHAR(100),
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

ALTER TABLE ABC ADD CONSTRAINT ABC_PK PRIMARY KEY (COL1);

CREATE INDEX ABC_FK1 ON ABC (COL1B);

ALTER TABLE ABC ADD CONSTRAINT ABC_FK1 FOREIGN KEY (COL1B) REFERENCES ANOTHER_TABLE (COL1) ON DELETE CASCADE;

