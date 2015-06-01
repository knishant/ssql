DROP TABLE QRTZ_FIRED_TRIGGERS;

DROP TABLE QRTZ_PAUSED_TRIGGER_GRPS;

DROP TABLE QRTZ_SCHEDULER_STATE;

DROP TABLE QRTZ_LOCKS;

DROP TABLE QRTZ_SIMPLE_TRIGGERS;

DROP TABLE QRTZ_CRON_TRIGGERS;

DROP TABLE QRTZ_SIMPROP_TRIGGERS;

DROP TABLE QRTZ_BLOB_TRIGGERS;

DROP TABLE QRTZ_TRIGGERS;

DROP TABLE QRTZ_JOB_DETAILS;

DROP TABLE QRTZ_CALENDARS;

CREATE TABLE QRTZ_JOB_DETAILS
(
    SCHED_NAME                     VARCHAR(120)              NOT NULL,
    JOB_NAME                       VARCHAR(200)              NOT NULL,
    JOB_GROUP                      VARCHAR(200)              NOT NULL,
    DESCRIPTION                    VARCHAR(250),
    JOB_CLASS_NAME                 VARCHAR(250)              NOT NULL,
    IS_DURABLE                     BOOLEAN                   NOT NULL,
    IS_NONCONCURRENT               BOOLEAN                   NOT NULL,
    IS_UPDATE_DATA                 BOOLEAN                   NOT NULL,
    REQUESTS_RECOVERY              BOOLEAN                   NOT NULL,
    JOB_DATA                       BLOB
);

ALTER TABLE QRTZ_JOB_DETAILS ADD CONSTRAINT QRTZ_JOB_DETAILS_PK PRIMARY KEY (SCHED_NAME, JOB_NAME, JOB_GROUP);

CREATE TABLE QRTZ_TRIGGERS
(
    SCHED_NAME                     VARCHAR(120)              NOT NULL,
    "TRIGGER_NAME"                 VARCHAR(200)              NOT NULL,
    TRIGGER_GROUP                  VARCHAR(200)              NOT NULL,
    JOB_NAME                       VARCHAR(200)              NOT NULL,
    JOB_GROUP                      VARCHAR(200)              NOT NULL,
    DESCRIPTION                    VARCHAR(250),
    NEXT_FIRE_TIME                 BIGINT,
    PREV_FIRE_TIME                 BIGINT,
    PRIORITY                       INT,
    TRIGGER_STATE                  VARCHAR(16)               NOT NULL,
    TRIGGER_TYPE                   VARCHAR(8)                NOT NULL,
    START_TIME                     BIGINT                    NOT NULL,
    END_TIME                       BIGINT,
    CALENDAR_NAME                  VARCHAR(200),
    MISFIRE_INSTR                  SMALLINT,
    JOB_DATA                       BLOB
);

ALTER TABLE QRTZ_TRIGGERS ADD CONSTRAINT QRTZ_TRIGGERS_PK PRIMARY KEY (SCHED_NAME, "TRIGGER_NAME", TRIGGER_GROUP);

ALTER TABLE QRTZ_TRIGGERS ADD CONSTRAINT QRTZ_TRIGGERS_FK FOREIGN KEY (SCHED_NAME, JOB_NAME, JOB_GROUP) REFERENCES QRTZ_JOB_DETAILS (SCHED_NAME, JOB_NAME, JOB_GROUP);

CREATE TABLE QRTZ_SIMPLE_TRIGGERS
(
    SCHED_NAME                     VARCHAR(120)              NOT NULL,
    "TRIGGER_NAME"                 VARCHAR(200)              NOT NULL,
    TRIGGER_GROUP                  VARCHAR(200)              NOT NULL,
    REPEAT_COUNT                   BIGINT                    NOT NULL,
    REPEAT_INTERVAL                BIGINT                    NOT NULL,
    TIMES_TRIGGERED                BIGINT                    NOT NULL
);

ALTER TABLE QRTZ_SIMPLE_TRIGGERS ADD CONSTRAINT QRTZ_SIMPLE_TRIGGERS_PK PRIMARY KEY (SCHED_NAME, "TRIGGER_NAME", TRIGGER_GROUP);

ALTER TABLE QRTZ_SIMPLE_TRIGGERS ADD CONSTRAINT QRTZ_SIMPLE_TRIGGERS_FK FOREIGN KEY (SCHED_NAME, "TRIGGER_NAME", TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS (SCHED_NAME, "TRIGGER_NAME", TRIGGER_GROUP);

CREATE TABLE QRTZ_CRON_TRIGGERS
(
    SCHED_NAME                     VARCHAR(120)              NOT NULL,
    "TRIGGER_NAME"                 VARCHAR(200)              NOT NULL,
    TRIGGER_GROUP                  VARCHAR(200)              NOT NULL,
    CRON_EXPRESSION                VARCHAR(120)              NOT NULL,
    TIME_ZONE_ID                   VARCHAR(80)
);

ALTER TABLE QRTZ_CRON_TRIGGERS ADD CONSTRAINT QRTZ_CRON_TRIGGERS_PK PRIMARY KEY (SCHED_NAME, "TRIGGER_NAME", TRIGGER_GROUP);

ALTER TABLE QRTZ_CRON_TRIGGERS ADD CONSTRAINT QRTZ_CRON_TRIGGERS_FK FOREIGN KEY (SCHED_NAME, "TRIGGER_NAME", TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS (SCHED_NAME, "TRIGGER_NAME", TRIGGER_GROUP);

CREATE TABLE QRTZ_SIMPROP_TRIGGERS
(
    SCHED_NAME                     VARCHAR(120)              NOT NULL,
    "TRIGGER_NAME"                 VARCHAR(200)              NOT NULL,
    TRIGGER_GROUP                  VARCHAR(200)              NOT NULL,
    STR_PROP_1                     VARCHAR(512),
    STR_PROP_2                     VARCHAR(512),
    STR_PROP_3                     VARCHAR(512),
    INT_PROP_1                     INT,
    INT_PROP_2                     INT,
    LONG_PROP_1                    BIGINT,
    LONG_PROP_2                    BIGINT,
    DEC_PROP_1                     NUMERIC(13,4),
    DEC_PROP_2                     NUMERIC(13,4),
    BOOL_PROP_1                    BOOLEAN,
    BOOL_PROP_2                    BOOLEAN
);

ALTER TABLE QRTZ_SIMPROP_TRIGGERS ADD CONSTRAINT QRTZ_SIMPROP_TRIGGERS_PK PRIMARY KEY (SCHED_NAME, "TRIGGER_NAME", TRIGGER_GROUP);

ALTER TABLE QRTZ_SIMPROP_TRIGGERS ADD CONSTRAINT QRTZ_SIMPROP_TRIGGERS_FK FOREIGN KEY (SCHED_NAME, "TRIGGER_NAME", TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS (SCHED_NAME, "TRIGGER_NAME", TRIGGER_GROUP);

CREATE TABLE QRTZ_BLOB_TRIGGERS
(
    SCHED_NAME                     VARCHAR(120)              NOT NULL,
    "TRIGGER_NAME"                 VARCHAR(200)              NOT NULL,
    TRIGGER_GROUP                  VARCHAR(200)              NOT NULL,
    BLOB_DATA                      BLOB
);

ALTER TABLE QRTZ_BLOB_TRIGGERS ADD CONSTRAINT QRTZ_BLOB_TRIGGERS_PK PRIMARY KEY (SCHED_NAME, "TRIGGER_NAME", TRIGGER_GROUP);

ALTER TABLE QRTZ_BLOB_TRIGGERS ADD CONSTRAINT QRTZ_BLOB_TRIGGERS_FK FOREIGN KEY (SCHED_NAME, "TRIGGER_NAME", TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS (SCHED_NAME, "TRIGGER_NAME", TRIGGER_GROUP);

CREATE TABLE QRTZ_CALENDARS
(
    SCHED_NAME                     VARCHAR(120)              NOT NULL,
    CALENDAR_NAME                  VARCHAR(200)              NOT NULL,
    CALENDAR                       BLOB                      NOT NULL
);

ALTER TABLE QRTZ_CALENDARS ADD CONSTRAINT QRTZ_CALENDARS_PK PRIMARY KEY (SCHED_NAME, CALENDAR_NAME);

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS
(
    SCHED_NAME                     VARCHAR(120)              NOT NULL,
    TRIGGER_GROUP                  VARCHAR(200)              NOT NULL
);

ALTER TABLE QRTZ_PAUSED_TRIGGER_GRPS ADD CONSTRAINT QRTZ_PAUSED_TRIGGER_GRPS_PK PRIMARY KEY (SCHED_NAME, TRIGGER_GROUP);

CREATE TABLE QRTZ_FIRED_TRIGGERS
(
    SCHED_NAME                     VARCHAR(120)              NOT NULL,
    ENTRY_ID                       VARCHAR(95)               NOT NULL,
    "TRIGGER_NAME"                 VARCHAR(200)              NOT NULL,
    TRIGGER_GROUP                  VARCHAR(200)              NOT NULL,
    INSTANCE_NAME                  VARCHAR(200)              NOT NULL,
    FIRED_TIME                     BIGINT                    NOT NULL,
    SCHED_TIME                     BIGINT                    NOT NULL,
    PRIORITY                       INT                       NOT NULL,
    "STATE"                        VARCHAR(16)               NOT NULL,
    JOB_NAME                       VARCHAR(200),
    JOB_GROUP                      VARCHAR(200),
    IS_NONCONCURRENT               BOOLEAN,
    REQUESTS_RECOVERY              BOOLEAN
);

ALTER TABLE QRTZ_FIRED_TRIGGERS ADD CONSTRAINT QRTZ_FIRED_TRIGGERS_PK PRIMARY KEY (SCHED_NAME, ENTRY_ID);

CREATE TABLE QRTZ_SCHEDULER_STATE
(
    SCHED_NAME                     VARCHAR(120)              NOT NULL,
    INSTANCE_NAME                  VARCHAR(200)              NOT NULL,
    LAST_CHECKIN_TIME              BIGINT                    NOT NULL,
    CHECKIN_INTERVAL               BIGINT                    NOT NULL
);

ALTER TABLE QRTZ_SCHEDULER_STATE ADD CONSTRAINT QRTZ_SCHEDULER_STATE_PK PRIMARY KEY (SCHED_NAME, INSTANCE_NAME);

CREATE TABLE QRTZ_LOCKS
(
    SCHED_NAME                     VARCHAR(120)              NOT NULL,
    LOCK_NAME                      VARCHAR(40)               NOT NULL
);

ALTER TABLE QRTZ_LOCKS ADD CONSTRAINT QRTZ_LOCKS_PK PRIMARY KEY (SCHED_NAME, LOCK_NAME);

CREATE INDEX IDX_QRTZ_J_REQ_RECOVERY ON QRTZ_JOB_DETAILS (SCHED_NAME, REQUESTS_RECOVERY);

CREATE INDEX IDX_QRTZ_J_GRP ON QRTZ_JOB_DETAILS (SCHED_NAME, JOB_GROUP);

CREATE INDEX IDX_QRTZ_T_J ON QRTZ_TRIGGERS (SCHED_NAME, JOB_NAME, JOB_GROUP);

CREATE INDEX IDX_QRTZ_T_JG ON QRTZ_TRIGGERS (SCHED_NAME, JOB_GROUP);

CREATE INDEX IDX_QRTZ_T_C ON QRTZ_TRIGGERS (SCHED_NAME, CALENDAR_NAME);

CREATE INDEX IDX_QRTZ_T_G ON QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_GROUP);

CREATE INDEX IDX_QRTZ_T_STATE ON QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_T_N_STATE ON QRTZ_TRIGGERS (SCHED_NAME, "TRIGGER_NAME", TRIGGER_GROUP, TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_T_N_G_STATE ON QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_GROUP, TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_T_NEXT_FIRE_TIME ON QRTZ_TRIGGERS (SCHED_NAME, NEXT_FIRE_TIME);

CREATE INDEX IDX_QRTZ_T_NFT_ST ON QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_STATE, NEXT_FIRE_TIME);

CREATE INDEX IDX_QRTZ_T_NFT_MISFIRE ON QRTZ_TRIGGERS (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME);

CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE ON QRTZ_TRIGGERS (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME, TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE_GRP ON QRTZ_TRIGGERS (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME, TRIGGER_GROUP, TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_FT_TRIG_INST_NAME ON QRTZ_FIRED_TRIGGERS (SCHED_NAME, INSTANCE_NAME);

CREATE INDEX IDX_QRTZ_FT_INST_JOB_REQ_RCVRY ON QRTZ_FIRED_TRIGGERS (SCHED_NAME, INSTANCE_NAME, REQUESTS_RECOVERY);

CREATE INDEX IDX_QRTZ_FT_J_G ON QRTZ_FIRED_TRIGGERS (SCHED_NAME, JOB_NAME, JOB_GROUP);

CREATE INDEX IDX_QRTZ_FT_JG ON QRTZ_FIRED_TRIGGERS (SCHED_NAME, JOB_GROUP);

CREATE INDEX IDX_QRTZ_FT_T_G ON QRTZ_FIRED_TRIGGERS (SCHED_NAME, "TRIGGER_NAME", TRIGGER_GROUP);

CREATE INDEX IDX_QRTZ_FT_TG ON QRTZ_FIRED_TRIGGERS (SCHED_NAME, TRIGGER_GROUP);

