/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/6/7 12:11:33                            */
/*==============================================================*/


drop table if exists 1_TBL_XXGK;

drop table if exists 2_TBL_XQDZ;

drop table if exists 3_TBL_XXXGDZDW;

drop table if exists 4_TBL_XXJXKYDW;

drop table if exists TBL_DATACOL;

drop table if exists TBL_FORM;

drop table if exists TBL_FORM_RULES;

drop table if exists TBL_USER;

/*==============================================================*/
/* Table: 1_TBL_XXGK                                            */
/*==============================================================*/
create table 1_TBL_XXGK
(
   id                   bigint not null auto_increment,
   TJSJ                 timestamp,
   XXMC                 varchar(255),
   DM                   varchar(255),
   YWMC                 varchar(255),
   BXLX                 varchar(255),
   XXXZ                 varchar(255),
   JBZ                  varchar(255),
   ZGBM                 varchar(255),
   XXWZ                 varchar(255),
   ZSPC                 varchar(255),
   KBBKJYNF             varchar(255),
   XM                   varchar(255),
   LXDH                 varchar(255),
   LXDZYX               varchar(255),
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: 2_TBL_XQDZ                                            */
/*==============================================================*/
create table 2_TBL_XQDZ
(
   id                   bigint not null auto_increment,
   TJSJ                 timestamp,
   XQMC                 varchar(255),
   SF                   varchar(255),
   SQ                   varchar(255),
   XXDZ                 varchar(255),
   SFBDXQ               varchar(255),
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: 3_TBL_XXXGDZDW                                        */
/*==============================================================*/
create table 3_TBL_XXXGDZDW
(
   id                   bigint not null auto_increment,
   TJSJ                 timestamp,
   DZDWMC               varchar(255),
   DWH                  varchar(255),
   DWZN                 varchar(255),
   DWFZR                varchar(255),
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: 4_TBL_XXJXKYDW                                        */
/*==============================================================*/
create table 4_TBL_XXJXKYDW
(
   id                   bigint not null auto_increment,
   TJSJ                 timestamp,
   JXKYDWMC             varchar(255),
   DWH                  varchar(255),
   DWZN                 varchar(255),
   DWFZR                varchar(255),
   SFKH                 varchar(255),
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: TBL_DATACOL                                           */
/*==============================================================*/
create table TBL_DATACOL
(
   id                   bigint not null auto_increment,
   user_id              bigint,
   form_id              bigint,
   status               char,
   update_time          timestamp,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: TBL_FORM                                              */
/*==============================================================*/
create table TBL_FORM
(
   id                   bigint not null auto_increment,
   user_id              bigint,
   tbl_name             varchar(255),
   bus_name             varchar(255),
   phsic_name           varchar(255),
   stats_time           date,
   form_type            varchar(255),
   is_null              char,
   dependency_form      bigint,
   template_loc         varchar(255),
   create_time          timestamp,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: TBL_FORM_FILEDS                                       */
/*==============================================================*/
create table TBL_FORM_FIELDS
(
   id                   bigint not NULL auto_increment,
   bus_name             varchar(255),
   physic_name          varchar(255),
   is_required          char,
   sequence             int,
   data_type            int,
   length               int,
   dis_method           varchar(255),
   is_report            char,
   is_hidden            char,
   compute              varchar(50),
   form_id              bigint,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: TBL_FORM_RULES                                        */
/*==============================================================*/
create table TBL_FORM_RULES
(
   id                   bigint not null auto_increment,
   user_id              bigint,
   form_id              bigint,
   rule_name            varchar(255),
   rule_class           int,
   rule_content         varchar(1000),
   fail_information     varchar(255),
   rule_pattern         char,
   rule_seq             int,
   rule_active          char,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* Table: TBL_USER                                              */
/*==============================================================*/
create table TBL_USER
(
   id                   bigint not null auto_increment,
   user_name            varchar(255),
   password             varchar(255),
   create_time          timestamp,
   last_login           timestamp,
   primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table TBL_DATACOL add constraint FK_Reference_2 foreign key (form_id)
      references TBL_FORM (id) on delete restrict on update restrict;

alter table TBL_FORM add constraint FK_Reference_1 foreign key (user_id)
      references TBL_USER (id) on delete restrict on update restrict;

alter table TBL_FORM_FIELDS add constraint FK_Reference_4 foreign key (form_id)
      references TBL_FORM (id) on delete restrict on update restrict;

alter table TBL_FORM_RULES add constraint FK_Reference_3 foreign key (form_id)
      references TBL_FORM (id) on delete restrict on update restrict;

