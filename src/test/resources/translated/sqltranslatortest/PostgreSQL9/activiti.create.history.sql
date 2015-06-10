CREATE TABLE ACT_HI_PROCINST
(
    ID_                            VARCHAR(64)               NOT NULL,
    PROC_INST_ID_                  VARCHAR(64)               NOT NULL,
    BUSINESS_KEY_                  VARCHAR(255),
    PROC_DEF_ID_                   VARCHAR(64)               NOT NULL,
    START_TIME_                    TIMESTAMP                 NOT NULL,
    END_TIME_                      TIMESTAMP,
    DURATION_                      BIGINT,
    START_USER_ID_                 VARCHAR(255),
    START_ACT_ID_                  VARCHAR(255),
    END_ACT_ID_                    VARCHAR(255),
    SUPER_PROCESS_INSTANCE_ID_     VARCHAR(64),
    DELETE_REASON_                 VARCHAR(4000),
    TENANT_ID_                     VARCHAR(255)              DEFAULT '',
    NAME_                          VARCHAR(255),
    CONSTRAINT ACT_HI_PROCINST_PK PRIMARY KEY (ID_),
    CONSTRAINT ACT_HI_PROCINST_UN UNIQUE (PROC_INST_ID_)
);

CREATE TABLE ACT_HI_ACTINST
(
    ID_                            VARCHAR(64)               NOT NULL,
    PROC_DEF_ID_                   VARCHAR(64)               NOT NULL,
    PROC_INST_ID_                  VARCHAR(64)               NOT NULL,
    EXECUTION_ID_                  VARCHAR(64)               NOT NULL,
    ACT_ID_                        VARCHAR(255)              NOT NULL,
    TASK_ID_                       VARCHAR(64),
    CALL_PROC_INST_ID_             VARCHAR(64),
    ACT_NAME_                      VARCHAR(255),
    ACT_TYPE_                      VARCHAR(255)              NOT NULL,
    ASSIGNEE_                      VARCHAR(255),
    START_TIME_                    TIMESTAMP                 NOT NULL,
    END_TIME_                      TIMESTAMP,
    DURATION_                      BIGINT,
    TENANT_ID_                     VARCHAR(255)              DEFAULT '',
    CONSTRAINT ACT_HI_ACTINST_PK PRIMARY KEY (ID_)
);

CREATE TABLE ACT_HI_TASKINST
(
    ID_                            VARCHAR(64)               NOT NULL,
    PROC_DEF_ID_                   VARCHAR(64),
    TASK_DEF_KEY_                  VARCHAR(255),
    PROC_INST_ID_                  VARCHAR(64),
    EXECUTION_ID_                  VARCHAR(64),
    NAME_                          VARCHAR(255),
    PARENT_TASK_ID_                VARCHAR(64),
    DESCRIPTION_                   VARCHAR(4000),
    OWNER_                         VARCHAR(255),
    ASSIGNEE_                      VARCHAR(255),
    START_TIME_                    TIMESTAMP                 NOT NULL,
    CLAIM_TIME_                    TIMESTAMP,
    END_TIME_                      TIMESTAMP,
    DURATION_                      BIGINT,
    DELETE_REASON_                 VARCHAR(4000),
    PRIORITY_                      INTEGER,
    DUE_DATE_                      TIMESTAMP,
    FORM_KEY_                      VARCHAR(255),
    CATEGORY_                      VARCHAR(255),
    TENANT_ID_                     VARCHAR(255)              DEFAULT '',
    CONSTRAINT ACT_HI_TASKINST_PK PRIMARY KEY (ID_)
);

CREATE TABLE ACT_HI_VARINST
(
    ID_                            VARCHAR(64)               NOT NULL,
    PROC_INST_ID_                  VARCHAR(64),
    EXECUTION_ID_                  VARCHAR(64),
    TASK_ID_                       VARCHAR(64),
    NAME_                          VARCHAR(255)              NOT NULL,
    VAR_TYPE_                      VARCHAR(100),
    REV_                           INTEGER,
    BYTEARRAY_ID_                  VARCHAR(64),
    DOUBLE_                        DOUBLE PRECISION,
    LONG_                          BIGINT,
    TEXT_                          VARCHAR(4000),
    TEXT2_                         VARCHAR(4000),
    CREATE_TIME_                   TIMESTAMP,
    LAST_UPDATED_TIME_             TIMESTAMP,
    CONSTRAINT ACT_HI_VARINST_PK PRIMARY KEY (ID_)
);

CREATE TABLE ACT_HI_DETAIL
(
    ID_                            VARCHAR(64)               NOT NULL,
    TYPE_                          VARCHAR(255)              NOT NULL,
    PROC_INST_ID_                  VARCHAR(64),
    EXECUTION_ID_                  VARCHAR(64),
    TASK_ID_                       VARCHAR(64),
    ACT_INST_ID_                   VARCHAR(64),
    NAME_                          VARCHAR(255)              NOT NULL,
    VAR_TYPE_                      VARCHAR(64),
    REV_                           INTEGER,
    TIME_                          TIMESTAMP                 NOT NULL,
    BYTEARRAY_ID_                  VARCHAR(64),
    DOUBLE_                        DOUBLE PRECISION,
    LONG_                          BIGINT,
    TEXT_                          VARCHAR(4000),
    TEXT2_                         VARCHAR(4000),
    CONSTRAINT ACT_HI_DETAIL_PK PRIMARY KEY (ID_)
);

CREATE TABLE ACT_HI_COMMENT
(
    ID_                            VARCHAR(64)               NOT NULL,
    TYPE_                          VARCHAR(255),
    TIME_                          TIMESTAMP                 NOT NULL,
    USER_ID_                       VARCHAR(255),
    TASK_ID_                       VARCHAR(64),
    PROC_INST_ID_                  VARCHAR(64),
    ACTION_                        VARCHAR(255),
    MESSAGE_                       VARCHAR(4000),
    FULL_MSG_                      OID,
    CONSTRAINT ACT_HI_COMMENT_PK PRIMARY KEY (ID_)
);

CREATE TABLE ACT_HI_ATTACHMENT
(
    ID_                            VARCHAR(64)               NOT NULL,
    REV_                           INTEGER,
    USER_ID_                       VARCHAR(255),
    NAME_                          VARCHAR(255),
    DESCRIPTION_                   VARCHAR(4000),
    TYPE_                          VARCHAR(255),
    TASK_ID_                       VARCHAR(64),
    PROC_INST_ID_                  VARCHAR(64),
    URL_                           VARCHAR(4000),
    CONTENT_ID_                    VARCHAR(64),
    TIME_                          TIMESTAMP,
    CONSTRAINT ACT_HI_ATTACHMENT_PK PRIMARY KEY (ID_)
);

CREATE TABLE ACT_HI_IDENTITYLINK
(
    ID_                            VARCHAR(64),
    GROUP_ID_                      VARCHAR(255),
    TYPE_                          VARCHAR(255),
    USER_ID_                       VARCHAR(255),
    TASK_ID_                       VARCHAR(64),
    PROC_INST_ID_                  VARCHAR(64),
    CONSTRAINT ACT_HI_IDENTITYLINK_PK PRIMARY KEY (ID_)
);

CREATE INDEX ACT_IDX_HI_PRO_INST_END ON ACT_HI_PROCINST (END_TIME_);

CREATE INDEX ACT_IDX_HI_PRO_I_BUSKEY ON ACT_HI_PROCINST (BUSINESS_KEY_);

CREATE INDEX ACT_IDX_HI_ACT_INST_START ON ACT_HI_ACTINST (START_TIME_);

CREATE INDEX ACT_IDX_HI_ACT_INST_END ON ACT_HI_ACTINST (END_TIME_);

CREATE INDEX ACT_IDX_HI_DETAIL_PROC_INST ON ACT_HI_DETAIL (PROC_INST_ID_);

CREATE INDEX ACT_IDX_HI_DETAIL_ACT_INST ON ACT_HI_DETAIL (ACT_INST_ID_);

CREATE INDEX ACT_IDX_HI_DETAIL_TIME ON ACT_HI_DETAIL (TIME_);

CREATE INDEX ACT_IDX_HI_DETAIL_NAME ON ACT_HI_DETAIL (NAME_);

CREATE INDEX ACT_IDX_HI_DETAIL_TASK_ID ON ACT_HI_DETAIL (TASK_ID_);

CREATE INDEX ACT_IDX_HI_PROCVAR_PROC_INST ON ACT_HI_VARINST (PROC_INST_ID_);

CREATE INDEX ACT_IDX_HI_PROCVAR_NAME_TYPE ON ACT_HI_VARINST (NAME_, VAR_TYPE_);

CREATE INDEX ACT_IDX_HI_PROCVAR_TASK_ID ON ACT_HI_VARINST (TASK_ID_);

CREATE INDEX ACT_IDX_HI_ACT_INST_PROCINST ON ACT_HI_ACTINST (PROC_INST_ID_, ACT_ID_);

CREATE INDEX ACT_IDX_HI_ACT_INST_EXEC ON ACT_HI_ACTINST (EXECUTION_ID_, ACT_ID_);

CREATE INDEX ACT_IDX_HI_IDENT_LNK_USER ON ACT_HI_IDENTITYLINK (USER_ID_);

CREATE INDEX ACT_IDX_HI_IDENT_LNK_TASK ON ACT_HI_IDENTITYLINK (TASK_ID_);

CREATE INDEX ACT_IDX_HI_IDENT_LNK_PROCINST ON ACT_HI_IDENTITYLINK (PROC_INST_ID_);

CREATE INDEX ACT_IDX_HI_TASK_INST_PROCINST ON ACT_HI_TASKINST (PROC_INST_ID_);

