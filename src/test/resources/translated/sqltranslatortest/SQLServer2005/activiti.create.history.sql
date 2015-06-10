CREATE TABLE ACT_HI_PROCINST
(
    ID_                            NVARCHAR(64)              NOT NULL,
    PROC_INST_ID_                  NVARCHAR(64)              NOT NULL,
    BUSINESS_KEY_                  NVARCHAR(255),
    PROC_DEF_ID_                   NVARCHAR(64)              NOT NULL,
    START_TIME_                    DATETIME                  NOT NULL,
    END_TIME_                      DATETIME,
    DURATION_                      BIGINT,
    START_USER_ID_                 NVARCHAR(255),
    START_ACT_ID_                  NVARCHAR(255),
    END_ACT_ID_                    NVARCHAR(255),
    SUPER_PROCESS_INSTANCE_ID_     NVARCHAR(64),
    DELETE_REASON_                 NVARCHAR(4000),
    TENANT_ID_                     NVARCHAR(255)             DEFAULT '',
    NAME_                          NVARCHAR(255),
    CONSTRAINT ACT_HI_PROCINST_PK PRIMARY KEY (ID_),
    CONSTRAINT ACT_HI_PROCINST_UN UNIQUE (PROC_INST_ID_)
);

CREATE TABLE ACT_HI_ACTINST
(
    ID_                            NVARCHAR(64)              NOT NULL,
    PROC_DEF_ID_                   NVARCHAR(64)              NOT NULL,
    PROC_INST_ID_                  NVARCHAR(64)              NOT NULL,
    EXECUTION_ID_                  NVARCHAR(64)              NOT NULL,
    ACT_ID_                        NVARCHAR(255)             NOT NULL,
    TASK_ID_                       NVARCHAR(64),
    CALL_PROC_INST_ID_             NVARCHAR(64),
    ACT_NAME_                      NVARCHAR(255),
    ACT_TYPE_                      NVARCHAR(255)             NOT NULL,
    ASSIGNEE_                      NVARCHAR(255),
    START_TIME_                    DATETIME                  NOT NULL,
    END_TIME_                      DATETIME,
    DURATION_                      BIGINT,
    TENANT_ID_                     NVARCHAR(255)             DEFAULT '',
    CONSTRAINT ACT_HI_ACTINST_PK PRIMARY KEY (ID_)
);

CREATE TABLE ACT_HI_TASKINST
(
    ID_                            NVARCHAR(64)              NOT NULL,
    PROC_DEF_ID_                   NVARCHAR(64),
    TASK_DEF_KEY_                  NVARCHAR(255),
    PROC_INST_ID_                  NVARCHAR(64),
    EXECUTION_ID_                  NVARCHAR(64),
    NAME_                          NVARCHAR(255),
    PARENT_TASK_ID_                NVARCHAR(64),
    DESCRIPTION_                   NVARCHAR(4000),
    OWNER_                         NVARCHAR(255),
    ASSIGNEE_                      NVARCHAR(255),
    START_TIME_                    DATETIME                  NOT NULL,
    CLAIM_TIME_                    DATETIME,
    END_TIME_                      DATETIME,
    DURATION_                      BIGINT,
    DELETE_REASON_                 NVARCHAR(4000),
    PRIORITY_                      INT,
    DUE_DATE_                      DATETIME,
    FORM_KEY_                      NVARCHAR(255),
    CATEGORY_                      NVARCHAR(255),
    TENANT_ID_                     NVARCHAR(255)             DEFAULT '',
    CONSTRAINT ACT_HI_TASKINST_PK PRIMARY KEY (ID_)
);

CREATE TABLE ACT_HI_VARINST
(
    ID_                            NVARCHAR(64)              NOT NULL,
    PROC_INST_ID_                  NVARCHAR(64),
    EXECUTION_ID_                  NVARCHAR(64),
    TASK_ID_                       NVARCHAR(64),
    NAME_                          NVARCHAR(255)             NOT NULL,
    VAR_TYPE_                      NVARCHAR(100),
    REV_                           INT,
    BYTEARRAY_ID_                  NVARCHAR(64),
    DOUBLE_                        DOUBLE PRECISION,
    LONG_                          BIGINT,
    TEXT_                          NVARCHAR(4000),
    TEXT2_                         NVARCHAR(4000),
    CREATE_TIME_                   DATETIME,
    LAST_UPDATED_TIME_             DATETIME,
    CONSTRAINT ACT_HI_VARINST_PK PRIMARY KEY (ID_)
);

CREATE TABLE ACT_HI_DETAIL
(
    ID_                            NVARCHAR(64)              NOT NULL,
    TYPE_                          NVARCHAR(255)             NOT NULL,
    PROC_INST_ID_                  NVARCHAR(64),
    EXECUTION_ID_                  NVARCHAR(64),
    TASK_ID_                       NVARCHAR(64),
    ACT_INST_ID_                   NVARCHAR(64),
    NAME_                          NVARCHAR(255)             NOT NULL,
    VAR_TYPE_                      NVARCHAR(64),
    REV_                           INT,
    TIME_                          DATETIME                  NOT NULL,
    BYTEARRAY_ID_                  NVARCHAR(64),
    DOUBLE_                        DOUBLE PRECISION,
    LONG_                          BIGINT,
    TEXT_                          NVARCHAR(4000),
    TEXT2_                         NVARCHAR(4000),
    CONSTRAINT ACT_HI_DETAIL_PK PRIMARY KEY (ID_)
);

CREATE TABLE ACT_HI_COMMENT
(
    ID_                            NVARCHAR(64)              NOT NULL,
    TYPE_                          NVARCHAR(255),
    TIME_                          DATETIME                  NOT NULL,
    USER_ID_                       NVARCHAR(255),
    TASK_ID_                       NVARCHAR(64),
    PROC_INST_ID_                  NVARCHAR(64),
    ACTION_                        NVARCHAR(255),
    MESSAGE_                       NVARCHAR(4000),
    FULL_MSG_                      VARBINARY(MAX),
    CONSTRAINT ACT_HI_COMMENT_PK PRIMARY KEY (ID_)
);

CREATE TABLE ACT_HI_ATTACHMENT
(
    ID_                            NVARCHAR(64)              NOT NULL,
    REV_                           INT,
    USER_ID_                       NVARCHAR(255),
    NAME_                          NVARCHAR(255),
    DESCRIPTION_                   NVARCHAR(4000),
    TYPE_                          NVARCHAR(255),
    TASK_ID_                       NVARCHAR(64),
    PROC_INST_ID_                  NVARCHAR(64),
    URL_                           NVARCHAR(4000),
    CONTENT_ID_                    NVARCHAR(64),
    TIME_                          DATETIME,
    CONSTRAINT ACT_HI_ATTACHMENT_PK PRIMARY KEY (ID_)
);

CREATE TABLE ACT_HI_IDENTITYLINK
(
    ID_                            NVARCHAR(64),
    GROUP_ID_                      NVARCHAR(255),
    TYPE_                          NVARCHAR(255),
    USER_ID_                       NVARCHAR(255),
    TASK_ID_                       NVARCHAR(64),
    PROC_INST_ID_                  NVARCHAR(64),
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

