CREATE TABLE t_user
(
    id          bigint       NOT NULL PRIMARY KEY,
    email       VARCHAR(255) NULL,
    username    VARCHAR(32)  NOT NULL,
    nickname    VARCHAR(32)  NULL,
    password    VARCHAR(32)  NOT NULL,
    salt        VARCHAR(255) NOT NULL,
    deleted     BIT          NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY `t_user_username_index` (`username`)
) ENGINE = InnoDB
  CHARSET = utf8mb4;


CREATE TABLE t_tweet
(
    id          BIGINT       NOT NULL PRIMARY KEY,
    user_id     BIGINT       NOT NULL,
    content     VARCHAR(255) NULL,
    -- 此处媒体类型应设置默认值，需要考虑枚举与数据库值的对应
    media_type  INT          NULL,
    media       VARCHAR(255) NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    KEY `t_tweet_user_id_index` (`user_id`)
) ENGINE = InnoDB
  CHARSET = utf8mb4;


CREATE TABLE t_comment
(
    id          BIGINT       NOT NULL PRIMARY KEY,
    user_id     BIGINT       NOT NULL,
    tweet_id    BIGINT       NOT NULL,
    content     VARCHAR(255) NOT NULL,
    reply_to    BIGINT       NOT NULL,
    deleted     BIT          NOT NULL,
    create_time DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY `t_comment_user_id_index` (`user_id`),
    KEY `t_comment_tweet_id_index` (`tweet_id`)
) ENGINE = InnoDB
  CHARSET = utf8mb4;


CREATE TABLE t_follow
(
    id          BIGINT   NOT NULL PRIMARY KEY,
    user_id     BIGINT   NOT NULL,
    follower_id BIGINT   NOT NULL,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY `t_follow_user_id_index` (`user_id`),
    KEY `t_follow_follower_id_index` (`follower_id`)
) ENGINE = InnoDB
  CHARSET = utf8mb4;


CREATE TABLE t_like
(
    id          BIGINT   NOT NULL PRIMARY KEY,
    user_id     BIGINT   NOT NULL,
    tweet_id    BIGINT   NOT NULL,
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY `t_like_user_id_index` (`user_id`),
    KEY `t_like_tweet_id_index` (`tweet_id`)
) ENGINE = InnoDB
  CHARSET = utf8mb4;