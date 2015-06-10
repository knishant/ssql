create table ACT_GE_PROPERTY (
    NAME_ nvarchar(64),
    VALUE_ nvarchar(300),
    REV_ int,
    constraint ACT_GE_PROPERTY_PK primary key (NAME_)
);

create table ACT_GE_BYTEARRAY (
    ID_ nvarchar(64),
    REV_ int,
    NAME_ nvarchar(255),
    DEPLOYMENT_ID_ nvarchar(64),
    BYTES_ blob,
    GENERATED_ boolean,
    constraint ACT_GE_BYTEARRAY_PK primary key (ID_)
);

create table ACT_RE_DEPLOYMENT (
    ID_ nvarchar(64),
    NAME_ nvarchar(255),
    CATEGORY_ nvarchar(255),
    TENANT_ID_ nvarchar(255) default '',
    DEPLOY_TIME_ timestamp,
    CONSTRAINT ACT_RE_DEPLOYMENT_PK primary key (ID_)
);

create table ACT_RE_MODEL (
    ID_ nvarchar(64) not null,
    REV_ int,
    NAME_ nvarchar(255),
    KEY_ nvarchar(255),
    CATEGORY_ nvarchar(255),
    CREATE_TIME_ timestamp,
    LAST_UPDATE_TIME_ timestamp,
    VERSION_ int,
    META_INFO_ nvarchar(4000),
    DEPLOYMENT_ID_ nvarchar(64),
    EDITOR_SOURCE_VALUE_ID_ nvarchar(64),
    EDITOR_SOURCE_EXTRA_VALUE_ID_ nvarchar(64),
    TENANT_ID_ nvarchar(255) default '',
    constraint ACT_RE_MODEL_PK primary key (ID_)
);

create table ACT_RU_EXECUTION (
    ID_ nvarchar(64),
    REV_ int,
    PROC_INST_ID_ nvarchar(64),
    BUSINESS_KEY_ nvarchar(255),
    PARENT_ID_ nvarchar(64),
    PROC_DEF_ID_ nvarchar(64),
    SUPER_EXEC_ nvarchar(64),
    ACT_ID_ nvarchar(255),
    IS_ACTIVE_ boolean,
    IS_CONCURRENT_ boolean,
    IS_SCOPE_ boolean,
    IS_EVENT_SCOPE_ boolean,
    SUSPENSION_STATE_ int,
    CACHED_ENT_STATE_ int,
    TENANT_ID_ nvarchar(255) default '',
    NAME_ nvarchar(255),
    LOCK_TIME_ timestamp,
    CONSTRAINT ACT_RU_EXECUTION_PK primary key (ID_)
);

create table ACT_RU_JOB (
    ID_ nvarchar(64) NOT NULL,
    REV_ int,
    TYPE_ nvarchar(255) NOT NULL,
    LOCK_EXP_TIME_ timestamp,
    LOCK_OWNER_ nvarchar(255),
    EXCLUSIVE_ boolean,
    EXECUTION_ID_ nvarchar(64),
    PROCESS_INSTANCE_ID_ nvarchar(64),
    PROC_DEF_ID_ nvarchar(64),
    RETRIES_ int,
    EXCEPTION_STACK_ID_ nvarchar(64),
    EXCEPTION_MSG_ nvarchar(4000),
    DUEDATE_ timestamp,
    REPEAT_ nvarchar(255),
    HANDLER_TYPE_ nvarchar(255),
    HANDLER_CFG_ nvarchar(4000),
    TENANT_ID_ nvarchar(255) default '',
    CONSTRAINT ACT_RU_JOB_PK primary key (ID_)
);

create table ACT_RE_PROCDEF (
    ID_ nvarchar(64) NOT NULL,
    REV_ int,
    CATEGORY_ nvarchar(255),
    NAME_ nvarchar(255),
    KEY_ nvarchar(255) NOT NULL,
    VERSION_ int NOT NULL,
    DEPLOYMENT_ID_ nvarchar(64),
    RESOURCE_NAME_ nvarchar(4000),
    DGRM_RESOURCE_NAME_ nvarchar(4000),
    DESCRIPTION_ nvarchar(4000),
    HAS_START_FORM_KEY_ boolean,
    HAS_GRAPHICAL_NOTATION_ boolean,
    SUSPENSION_STATE_ int,
    TENANT_ID_ nvarchar(255) default '',
    CONSTRAINT ACT_RE_PROCDEF_PK primary key (ID_)
);

create table ACT_RU_TASK (
    ID_ nvarchar(64),
    REV_ int,
    EXECUTION_ID_ nvarchar(64),
    PROC_INST_ID_ nvarchar(64),
    PROC_DEF_ID_ nvarchar(64),
    NAME_ nvarchar(255),
    PARENT_TASK_ID_ nvarchar(64),
    DESCRIPTION_ nvarchar(4000),
    TASK_DEF_KEY_ nvarchar(255),
    OWNER_ nvarchar(255),
    ASSIGNEE_ nvarchar(255),
    DELEGATION_ nvarchar(64),
    PRIORITY_ int,
    CREATE_TIME_ timestamp,
    DUE_DATE_ timestamp,
    CATEGORY_ nvarchar(255),
    SUSPENSION_STATE_ int,
    TENANT_ID_ nvarchar(255) default '',
    FORM_KEY_ nvarchar(255),
    CONSTRAINT ACT_RU_TASK_PK primary key (ID_)
);

create table ACT_RU_IDENTITYLINK (
    ID_ nvarchar(64),
    REV_ int,
    GROUP_ID_ nvarchar(255),
    TYPE_ nvarchar(255),
    USER_ID_ nvarchar(255),
    TASK_ID_ nvarchar(64),
    PROC_INST_ID_ nvarchar(64),
    PROC_DEF_ID_ nvarchar (64),
    CONSTRAINT ACT_RU_IDENTITYLINK_PK primary key (ID_)
);

create table ACT_RU_VARIABLE (
    ID_ nvarchar(64) not null,
    REV_ int,
    TYPE_ nvarchar(255) not null,
    NAME_ nvarchar(255) not null,
    EXECUTION_ID_ nvarchar(64),
    PROC_INST_ID_ nvarchar(64),
    TASK_ID_ nvarchar(64),
    BYTEARRAY_ID_ nvarchar(64),
    DOUBLE_ double precision,
    LONG_ bigint,
    TEXT_ nvarchar(4000),
    TEXT2_ nvarchar(4000),
    CONSTRAINT ACT_RU_VARIABLE_PK primary key (ID_)
);

create table ACT_RU_EVENT_SUBSCR (
    ID_ nvarchar(64) not null,
    REV_ int,
    EVENT_TYPE_ nvarchar(255) not null,
    EVENT_NAME_ nvarchar(255),
    EXECUTION_ID_ nvarchar(64),
    PROC_INST_ID_ nvarchar(64),
    ACTIVITY_ID_ nvarchar(64),
    CONFIGURATION_ nvarchar(255),
    CREATED_ timestamp not null,
    PROC_DEF_ID_ nvarchar(64),
    TENANT_ID_ nvarchar(255) default '',
    CONSTRAINT ACT_RU_EVENT_SUBSCR_PK primary key (ID_)
);

create table ACT_EVT_LOG (
    LOG_NR_ bigint identity,
    TYPE_ nvarchar(64),
    PROC_DEF_ID_ nvarchar(64),
    PROC_INST_ID_ nvarchar(64),
    EXECUTION_ID_ nvarchar(64),
    TASK_ID_ nvarchar(64),
    TIME_STAMP_ timestamp not null,
    USER_ID_ nvarchar(255),
    DATA_ blob,
    LOCK_OWNER_ nvarchar(255),
    LOCK_TIME_ timestamp,
    IS_PROCESSED_ smallint default 0,
    CONSTRAINT ACT_EVT_LOG_PK PRIMARY KEY (LOG_NR_)
);

create index ACT_IDX_EXEC_BUSKEY on ACT_RU_EXECUTION(BUSINESS_KEY_);

create index ACT_IDX_TASK_CREATE on ACT_RU_TASK(CREATE_TIME_);

create index ACT_IDX_IDENT_LNK_USER on ACT_RU_IDENTITYLINK(USER_ID_);

create index ACT_IDX_IDENT_LNK_GROUP on ACT_RU_IDENTITYLINK(GROUP_ID_);

create index ACT_IDX_EVENT_SUBSCR_CONFIG_ on ACT_RU_EVENT_SUBSCR(CONFIGURATION_);

create index ACT_IDX_VARIABLE_TASK_ID on ACT_RU_VARIABLE(TASK_ID_);

create index ACT_IDX_BYTEAR_DEPL on ACT_GE_BYTEARRAY(DEPLOYMENT_ID_);

alter table ACT_GE_BYTEARRAY
    add constraint ACT_FK_BYTEARR_DEPL
    foreign key (DEPLOYMENT_ID_) 
    references ACT_RE_DEPLOYMENT (ID_);

alter table ACT_RE_PROCDEF
    add constraint ACT_UNIQ_PROCDEF
    unique (KEY_,VERSION_, TENANT_ID_);
    
create index ACT_IDX_EXE_PROCINST on ACT_RU_EXECUTION(PROC_INST_ID_);

alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_PROCINST 
    foreign key (PROC_INST_ID_) 
    references ACT_RU_EXECUTION (ID_);

create index ACT_IDX_EXE_PARENT on ACT_RU_EXECUTION(PARENT_ID_);

alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_PARENT
    foreign key (PARENT_ID_) 
    references ACT_RU_EXECUTION (ID_);
    
create index ACT_IDX_EXE_SUPER on ACT_RU_EXECUTION(SUPER_EXEC_);

alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_SUPER
    foreign key (SUPER_EXEC_) 
    references ACT_RU_EXECUTION (ID_);

create index ACT_IDX_EXE_PROCDEF on ACT_RU_EXECUTION(PROC_DEF_ID_);

alter table ACT_RU_EXECUTION
    add constraint ACT_FK_EXE_PROCDEF 
    foreign key (PROC_DEF_ID_) 
    references ACT_RE_PROCDEF (ID_);    

create index ACT_IDX_TSKASS_TASK on ACT_RU_IDENTITYLINK(TASK_ID_);

alter table ACT_RU_IDENTITYLINK
    add constraint ACT_FK_TSKASS_TASK
    foreign key (TASK_ID_) 
    references ACT_RU_TASK (ID_);
    
create index ACT_IDX_ATHRZ_PROCEDEF on ACT_RU_IDENTITYLINK(PROC_DEF_ID_);

alter table ACT_RU_IDENTITYLINK
    add constraint ACT_FK_ATHRZ_PROCEDEF
    foreign key (PROC_DEF_ID_) 
    references ACT_RE_PROCDEF (ID_);
    
create index ACT_IDX_IDL_PROCINST on ACT_RU_IDENTITYLINK(PROC_INST_ID_);

alter table ACT_RU_IDENTITYLINK
    add constraint ACT_FK_IDL_PROCINST
    foreign key (PROC_INST_ID_) 
    references ACT_RU_EXECUTION (ID_);    
    
create index ACT_IDX_TASK_EXEC on ACT_RU_TASK(EXECUTION_ID_);

alter table ACT_RU_TASK
    add constraint ACT_FK_TASK_EXE
    foreign key (EXECUTION_ID_)
    references ACT_RU_EXECUTION (ID_);
    
create index ACT_IDX_TASK_PROCINST on ACT_RU_TASK(PROC_INST_ID_);

alter table ACT_RU_TASK
    add constraint ACT_FK_TASK_PROCINST
    foreign key (PROC_INST_ID_)
    references ACT_RU_EXECUTION (ID_);
    
create index ACT_IDX_TASK_PROCDEF on ACT_RU_TASK(PROC_DEF_ID_);

alter table ACT_RU_TASK
  add constraint ACT_FK_TASK_PROCDEF
  foreign key (PROC_DEF_ID_)
  references ACT_RE_PROCDEF (ID_);
  
create index ACT_IDX_VAR_EXE on ACT_RU_VARIABLE(EXECUTION_ID_);

alter table ACT_RU_VARIABLE 
    add constraint ACT_FK_VAR_EXE
    foreign key (EXECUTION_ID_) 
    references ACT_RU_EXECUTION (ID_);

create index ACT_IDX_VAR_PROCINST on ACT_RU_VARIABLE(PROC_INST_ID_);

alter table ACT_RU_VARIABLE
    add constraint ACT_FK_VAR_PROCINST
    foreign key (PROC_INST_ID_)
    references ACT_RU_EXECUTION(ID_);

create index ACT_IDX_VAR_BYTEARRAY on ACT_RU_VARIABLE(BYTEARRAY_ID_);

alter table ACT_RU_VARIABLE 
    add constraint ACT_FK_VAR_BYTEARRAY 
    foreign key (BYTEARRAY_ID_) 
    references ACT_GE_BYTEARRAY (ID_);

create index ACT_IDX_JOB_EXCEPTION on ACT_RU_JOB(EXCEPTION_STACK_ID_);

alter table ACT_RU_JOB 
    add constraint ACT_FK_JOB_EXCEPTION
    foreign key (EXCEPTION_STACK_ID_) 
    references ACT_GE_BYTEARRAY (ID_);

create index ACT_IDX_EVENT_SUBSCR on ACT_RU_EVENT_SUBSCR(EXECUTION_ID_);

alter table ACT_RU_EVENT_SUBSCR
    add constraint ACT_FK_EVENT_EXEC
    foreign key (EXECUTION_ID_)
    references ACT_RU_EXECUTION(ID_);

create index ACT_IDX_MODEL_SOURCE on ACT_RE_MODEL(EDITOR_SOURCE_VALUE_ID_);

alter table ACT_RE_MODEL 
    add constraint ACT_FK_MODEL_SOURCE 
    foreign key (EDITOR_SOURCE_VALUE_ID_) 
    references ACT_GE_BYTEARRAY (ID_);

create index ACT_IDX_MODEL_SOURCE_EXTRA on ACT_RE_MODEL(EDITOR_SOURCE_EXTRA_VALUE_ID_);

alter table ACT_RE_MODEL 
    add constraint ACT_FK_MODEL_SOURCE_EXTRA 
    foreign key (EDITOR_SOURCE_EXTRA_VALUE_ID_) 
    references ACT_GE_BYTEARRAY (ID_);
    
create index ACT_IDX_MODEL_DEPLOYMENT on ACT_RE_MODEL(DEPLOYMENT_ID_);

alter table ACT_RE_MODEL 
    add constraint ACT_FK_MODEL_DEPLOYMENT 
    foreign key (DEPLOYMENT_ID_) 
    references ACT_RE_DEPLOYMENT (ID_);        
