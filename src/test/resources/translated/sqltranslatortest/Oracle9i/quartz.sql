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
    SCHED_NAME                     VARCHAR2(120 CHAR)        NOT NULL,
    JOB_NAME                       VARCHAR2(200 CHAR)        NOT NULL,
    JOB_GROUP                      VARCHAR2(200 CHAR)        NOT NULL,
    DESCRIPTION                    VARCHAR2(250 CHAR),
    JOB_CLASS_NAME                 VARCHAR2(250 CHAR)        NOT NULL,
    IS_DURABLE                     NUMBER(1,0)               NOT NULL,
    IS_NONCONCURRENT               NUMBER(1,0)               NOT NULL,
    IS_UPDATE_DATA                 NUMBER(1,0)               NOT NULL,
    REQUESTS_RECOVERY              NUMBER(1,0)               NOT NULL,
    JOB_DATA                       BLOB
);

ALTER TABLE QRTZ_JOB_DETAILS ADD CONSTRAINT QRTZ_JOB_DETAILS_PK PRIMARY KEY (SCHED_NAME, JOB_NAME, JOB_GROUP);

CREATE TABLE QRTZ_TRIGGERS
(
    SCHED_NAME                     VARCHAR2(120 CHAR)        NOT NULL,
    TRIGGER_NAME                   VARCHAR2(200 CHAR)        NOT NULL,
    TRIGGER_GROUP                  VARCHAR2(200 CHAR)        NOT NULL,
    JOB_NAME                       VARCHAR2(200 CHAR)        NOT NULL,
    JOB_GROUP                      VARCHAR2(200 CHAR)        NOT NULL,
    DESCRIPTION                    VARCHAR2(250 CHAR),
    NEXT_FIRE_TIME                 NUMBER(19,0),
    PREV_FIRE_TIME                 NUMBER(19,0),
    PRIORITY                       NUMBER(10,0),
    TRIGGER_STATE                  VARCHAR2(16 CHAR)         NOT NULL,
    TRIGGER_TYPE                   VARCHAR2(8 CHAR)          NOT NULL,
    START_TIME                     NUMBER(19,0)              NOT NULL,
    END_TIME                       NUMBER(19,0),
    CALENDAR_NAME                  VARCHAR2(200 CHAR),
    MISFIRE_INSTR                  NUMBER(5,0),
    JOB_DATA                       BLOB
);

ALTER TABLE QRTZ_TRIGGERS ADD CONSTRAINT QRTZ_TRIGGERS_PK PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

ALTER TABLE QRTZ_TRIGGERS ADD CONSTRAINT QRTZ_TRIGGERS_FK FOREIGN KEY (SCHED_NAME, JOB_NAME, JOB_GROUP) REFERENCES QRTZ_JOB_DETAILS (SCHED_NAME, JOB_NAME, JOB_GROUP);

CREATE TABLE QRTZ_SIMPLE_TRIGGERS
(
    SCHED_NAME                     VARCHAR2(120 CHAR)        NOT NULL,
    TRIGGER_NAME                   VARCHAR2(200 CHAR)        NOT NULL,
    TRIGGER_GROUP                  VARCHAR2(200 CHAR)        NOT NULL,
    REPEAT_COUNT                   NUMBER(19,0)              NOT NULL,
    REPEAT_INTERVAL                NUMBER(19,0)              NOT NULL,
    TIMES_TRIGGERED                NUMBER(19,0)              NOT NULL
);

ALTER TABLE QRTZ_SIMPLE_TRIGGERS ADD CONSTRAINT QRTZ_SIMPLE_TRIGGERS_PK PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

ALTER TABLE QRTZ_SIMPLE_TRIGGERS ADD CONSTRAINT QRTZ_SIMPLE_TRIGGERS_FK FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

CREATE TABLE QRTZ_CRON_TRIGGERS
(
    SCHED_NAME                     VARCHAR2(120 CHAR)        NOT NULL,
    TRIGGER_NAME                   VARCHAR2(200 CHAR)        NOT NULL,
    TRIGGER_GROUP                  VARCHAR2(200 CHAR)        NOT NULL,
    CRON_EXPRESSION                VARCHAR2(120 CHAR)        NOT NULL,
    TIME_ZONE_ID                   VARCHAR2(80 CHAR)
);

ALTER TABLE QRTZ_CRON_TRIGGERS ADD CONSTRAINT QRTZ_CRON_TRIGGERS_PK PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

ALTER TABLE QRTZ_CRON_TRIGGERS ADD CONSTRAINT QRTZ_CRON_TRIGGERS_FK FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

CREATE TABLE QRTZ_SIMPROP_TRIGGERS
(
    SCHED_NAME                     VARCHAR2(120 CHAR)        NOT NULL,
    TRIGGER_NAME                   VARCHAR2(200 CHAR)        NOT NULL,
    TRIGGER_GROUP                  VARCHAR2(200 CHAR)        NOT NULL,
    STR_PROP_1                     VARCHAR2(512 CHAR),
    STR_PROP_2                     VARCHAR2(512 CHAR),
    STR_PROP_3                     VARCHAR2(512 CHAR),
    INT_PROP_1                     NUMBER(10,0),
    INT_PROP_2                     NUMBER(10,0),
    LONG_PROP_1                    NUMBER(19,0),
    LONG_PROP_2                    NUMBER(19,0),
    DEC_PROP_1                     NUMBER(13,4),
    DEC_PROP_2                     NUMBER(13,4),
    BOOL_PROP_1                    NUMBER(1,0),
    BOOL_PROP_2                    NUMBER(1,0)
);

ALTER TABLE QRTZ_SIMPROP_TRIGGERS ADD CONSTRAINT QRTZ_SIMPROP_TRIGGERS_PK PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

ALTER TABLE QRTZ_SIMPROP_TRIGGERS ADD CONSTRAINT QRTZ_SIMPROP_TRIGGERS_FK FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

CREATE TABLE QRTZ_BLOB_TRIGGERS
(
    SCHED_NAME                     VARCHAR2(120 CHAR)        NOT NULL,
    TRIGGER_NAME                   VARCHAR2(200 CHAR)        NOT NULL,
    TRIGGER_GROUP                  VARCHAR2(200 CHAR)        NOT NULL,
    BLOB_DATA                      BLOB
);

ALTER TABLE QRTZ_BLOB_TRIGGERS ADD CONSTRAINT QRTZ_BLOB_TRIGGERS_PK PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

ALTER TABLE QRTZ_BLOB_TRIGGERS ADD CONSTRAINT QRTZ_BLOB_TRIGGERS_FK FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

CREATE TABLE QRTZ_CALENDARS
(
    SCHED_NAME                     VARCHAR2(120 CHAR)        NOT NULL,
    CALENDAR_NAME                  VARCHAR2(200 CHAR)        NOT NULL,
    CALENDAR                       BLOB                      NOT NULL
);

ALTER TABLE QRTZ_CALENDARS ADD CONSTRAINT QRTZ_CALENDARS_PK PRIMARY KEY (SCHED_NAME, CALENDAR_NAME);

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS
(
    SCHED_NAME                     VARCHAR2(120 CHAR)        NOT NULL,
    TRIGGER_GROUP                  VARCHAR2(200 CHAR)        NOT NULL
);

ALTER TABLE QRTZ_PAUSED_TRIGGER_GRPS ADD CONSTRAINT QRTZ_PAUSED_TRIGGER_GRPS_PK PRIMARY KEY (SCHED_NAME, TRIGGER_GROUP);

CREATE TABLE QRTZ_FIRED_TRIGGERS
(
    SCHED_NAME                     VARCHAR2(120 CHAR)        NOT NULL,
    ENTRY_ID                       VARCHAR2(95 CHAR)         NOT NULL,
    TRIGGER_NAME                   VARCHAR2(200 CHAR)        NOT NULL,
    TRIGGER_GROUP                  VARCHAR2(200 CHAR)        NOT NULL,
    INSTANCE_NAME                  VARCHAR2(200 CHAR)        NOT NULL,
    FIRED_TIME                     NUMBER(19,0)              NOT NULL,
    SCHED_TIME                     NUMBER(19,0)              NOT NULL,
    PRIORITY                       NUMBER(10,0)              NOT NULL,
    STATE                          VARCHAR2(16 CHAR)         NOT NULL,
    JOB_NAME                       VARCHAR2(200 CHAR),
    JOB_GROUP                      VARCHAR2(200 CHAR),
    IS_NONCONCURRENT               NUMBER(1,0),
    REQUESTS_RECOVERY              NUMBER(1,0)
);

ALTER TABLE QRTZ_FIRED_TRIGGERS ADD CONSTRAINT QRTZ_FIRED_TRIGGERS_PK PRIMARY KEY (SCHED_NAME, ENTRY_ID);

CREATE TABLE QRTZ_SCHEDULER_STATE
(
    SCHED_NAME                     VARCHAR2(120 CHAR)        NOT NULL,
    INSTANCE_NAME                  VARCHAR2(200 CHAR)        NOT NULL,
    LAST_CHECKIN_TIME              NUMBER(19,0)              NOT NULL,
    CHECKIN_INTERVAL               NUMBER(19,0)              NOT NULL
);

ALTER TABLE QRTZ_SCHEDULER_STATE ADD CONSTRAINT QRTZ_SCHEDULER_STATE_PK PRIMARY KEY (SCHED_NAME, INSTANCE_NAME);

CREATE TABLE QRTZ_LOCKS
(
    SCHED_NAME                     VARCHAR2(120 CHAR)        NOT NULL,
    LOCK_NAME                      VARCHAR2(40 CHAR)         NOT NULL
);

ALTER TABLE QRTZ_LOCKS ADD CONSTRAINT QRTZ_LOCKS_PK PRIMARY KEY (SCHED_NAME, LOCK_NAME);

CREATE INDEX IDX_QRTZ_J_REQ_RECOVERY ON QRTZ_JOB_DETAILS (SCHED_NAME, REQUESTS_RECOVERY);

CREATE INDEX IDX_QRTZ_J_GRP ON QRTZ_JOB_DETAILS (SCHED_NAME, JOB_GROUP);

CREATE INDEX IDX_QRTZ_T_J ON QRTZ_TRIGGERS (SCHED_NAME, JOB_NAME, JOB_GROUP);

CREATE INDEX IDX_QRTZ_T_JG ON QRTZ_TRIGGERS (SCHED_NAME, JOB_GROUP);

CREATE INDEX IDX_QRTZ_T_C ON QRTZ_TRIGGERS (SCHED_NAME, CALENDAR_NAME);

CREATE INDEX IDX_QRTZ_T_G ON QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_GROUP);

CREATE INDEX IDX_QRTZ_T_STATE ON QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_T_N_STATE ON QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, TRIGGER_STATE);

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

CREATE INDEX IDX_QRTZ_FT_T_G ON QRTZ_FIRED_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

CREATE INDEX IDX_QRTZ_FT_TG ON QRTZ_FIRED_TRIGGERS (SCHED_NAME, TRIGGER_GROUP);
