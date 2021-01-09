drop table if exists hibernate_sequence;
drop table if exists operation_log;
drop table if exists pwitter_comment;
drop table if exists pwitter_follow;
drop table if exists pwitter_like;
drop table if exists pwitter_tweet;
drop table if exists pwitter_user;

create table hibernate_sequence
(
    next_val bigint
) engine = InnoDB;

insert into hibernate_sequence
values (1);

create table pwitter_user
(
    id          bigint not null,
    username    varchar(32),
    password    varchar(32),
    salt        varchar(255),
    email       varchar(255),
    nickname    varchar(32),
    deleted     bit    not null,
    create_time datetime default current_timestamp,
    update_time datetime default current_timestamp on update current_timestamp,
    primary key (id)
) engine = InnoDB;

alter table pwitter_user
    add constraint UK_k6goijaxa267eks9g95jhmna1 unique (username);

create table pwitter_follow
(
    id                bigint not null,
    following_user_id bigint not null,
    follower_user_id  bigint not null,
    deleted           bit    not null,
    create_time       datetime default current_timestamp,
    update_time       datetime default current_timestamp on update current_timestamp,
    primary key (id)
) engine = InnoDB;

create table pwitter_tweet
(
    id          bigint not null,
    user_id     bigint not null,
    content     varchar(255),
    media_type  integer,
    media       varchar(255),
    deleted     bit    not null,
    create_time datetime default current_timestamp,
    update_time datetime default current_timestamp on update current_timestamp,
    primary key (id)
) engine = InnoDB;

create table pwitter_like
(
    id          bigint not null,
    user_id     bigint not null,
    tweet_id    bigint not null,
    deleted     bit    not null,
    create_time datetime default current_timestamp,
    update_time datetime default current_timestamp on update current_timestamp,
    primary key (id)
) engine = InnoDB;

create table pwitter_comment
(
    id               bigint not null,
    user_id          bigint not null,
    tweet_id         bigint not null,
    reply_comment_id bigint not null,
    content          varchar(255),
    deleted          bit    not null,
    create_time      datetime default current_timestamp,
    update_time      datetime default current_timestamp on update current_timestamp,
    primary key (id)
) engine = InnoDB;

create table operation_log
(
    id             bigint  not null,
    operation_type integer not null,
    object_id      bigint  not null,
    before_value   varchar(255),
    after_value    varchar(255),
    operator       bigint  not null,
    comment        varchar(255),
    create_time    datetime default current_timestamp,
    update_time    datetime default current_timestamp on update current_timestamp,
    primary key (id)
) engine = InnoDB;