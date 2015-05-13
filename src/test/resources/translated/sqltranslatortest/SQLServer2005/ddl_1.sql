CREATE TABLE ABC
(
    COL1                           INT                       NOT NULL,
    COLS                           SMALLINT                  NOT NULL,
    COLB                           BIGINT                    NOT NULL,
    COL2                           VARCHAR(100),
    COL2N                          NVARCHAR(100),
    COL3                           CHAR(1)                   DEFAULT 'T',
    COL3N                          NCHAR(1),
    COL4                           DATETIME                  NOT NULL,
    COL5                           DATETIME,
    COL6                           NUMERIC(10,2),
    COL7                           DATETIME                  DEFAULT GETDATE(),
    COL8                           VARCHAR(MAX),
    COL9                           VARBINARY(MAX),
    COL10                          FLOAT,
    COL11                          DOUBLE PRECISION,
    COL12                          REAL,
    COL13                          BIT
);

ALTER TABLE ABC ADD CONSTRAINT ABC_PK PRIMARY KEY (COL1);

ALTER TABLE ABC ADD CONSTRAINT ABC_FK1 FOREIGN KEY (COL1B) REFERENCES ANOTHER_TABLE (COL1) ON DELETE CASCADE;

