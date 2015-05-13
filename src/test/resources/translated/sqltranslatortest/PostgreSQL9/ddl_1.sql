CREATE TABLE ABC
(
    COL1                           INTEGER                   NOT NULL,
    COLS                           SMALLINT                  NOT NULL,
    COLB                           BIGINT                    NOT NULL,
    COL2                           VARCHAR(100),
    COL2N                          VARCHAR(100),
    COL3                           CHAR(1)                   DEFAULT 'T',
    COL3N                          CHAR(1),
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

ALTER TABLE ABC ADD CONSTRAINT ABC_PK PRIMARY KEY (COL1);

CREATE INDEX ABC_FK1 ON ABC (COL1B);

ALTER TABLE ABC ADD CONSTRAINT ABC_FK1 FOREIGN KEY (COL1B) REFERENCES ANOTHER_TABLE (COL1) ON DELETE CASCADE;

