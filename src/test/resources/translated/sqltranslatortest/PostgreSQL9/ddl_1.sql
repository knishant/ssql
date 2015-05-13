--table level comment
CREATE TABLE ABC
(
    --col 1 comment
    COL1                           INTEGER                   NOT NULL,
    COLS                           SMALLINT                  NOT NULL,
    COLB                           BIGINT                    NOT NULL,
    COL2                           VARCHAR(100),
    COL2N                          VARCHAR(100),
    COL3                           CHAR(1)                   DEFAULT 'T',
    COL3N                          CHAR(1),
    --col 3 comment
    COL4                           DATE                      NOT NULL,
    COL5                           TIMESTAMP,
    COL6                           NUMERIC(10,2),
    COL7                           TIME                      DEFAULT CURRENT_TIME,
    COL8                           TEXT,
    COL9                           OID,
    COL10                          REAL,
    COL11                          DOUBLE PRECISION,
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

