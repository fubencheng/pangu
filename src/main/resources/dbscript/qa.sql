CREATE TABLE qa_category (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  category_name VARCHAR(64) NOT NULL DEFAULT '' COMMENT '分类名称',
  is_delete CHAR(1) NOT NULL DEFAULT '0' COMMENT '是否逻辑删除',
  gmt_created DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
  gmt_modified DATETIME COMMENT '修改时间',
  PRIMARY KEY (id)
)
  ENGINE = InnoDB
  COMMENT = '问题分类表';

INSERT INTO qa_category (id,category_name, is_delete, gmt_created, gmt_modified) VALUES (21, '技术', '0', NOW(), NOW());
INSERT INTO qa_category (id,category_name, is_delete, gmt_created, gmt_modified) VALUES (22, '工作', '0', NOW(), NOW());
INSERT INTO qa_category (id,category_name, is_delete, gmt_created, gmt_modified) VALUES (25, '生活', '0', NOW(), NOW());

CREATE TABLE qa_question (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  category_id BIGINT NOT NULL DEFAULT 0 COMMENT '问题分类ID',
  category_name VARCHAR(64) NOT NULL DEFAULT '' COMMENT '分类名称',
  title VARCHAR(128) NOT NULL DEFAULT '' COMMENT '问题标题',
  question TEXT NOT NULL COMMENT '问题内容',
  user_id CHAR(64) NOT NULL DEFAULT '' COMMENT '提问人ID',
  user_name VARCHAR(64) NOT NULL DEFAULT '' COMMENT '提问人姓名',
  views BIGINT DEFAULT 0 COMMENT '浏览数',
  answers BIGINT DEFAULT 0 COMMENT '回答数',
  thumb_up BIGINT DEFAULT 0 COMMENT '点赞数',
  thumb_down BIGINT DEFAULT 0 COMMENT '踩数',
  is_delete CHAR(1) NOT NULL DEFAULT '0' COMMENT '是否逻辑删除',
  gmt_created DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
  gmt_modified DATETIME COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX idx_user_id USING BTREE(user_id ASC),
  INDEX idx_views USING BTREE(views ASC),
  INDEX idx_answers USING BTREE(answers ASC),
  INDEX idx_thumb_up USING BTREE (thumb_up ASC),
  INDEX idx_gmt_created USING BTREE(gmt_created ASC)
)
  ENGINE = InnoDB
  COMMENT = '问题表';

CREATE TABLE qa_answer (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  question_id BIGINT NOT NULL DEFAULT 0 COMMENT '问题ID',
  user_id CHAR(64) NOT NULL DEFAULT '' COMMENT '回答人ID',
  user_name VARCHAR(64) DEFAULT '' COMMENT '回答人姓名',
  answer TEXT NOT NULL COMMENT '回答内容',
  comments BIGINT DEFAULT 0 COMMENT '评论数',
  likes BIGINT DEFAULT 0 COMMENT '赞',
  dislikes BIGINT DEFAULT 0 COMMENT '踩',
  is_delete CHAR(1) NOT NULL DEFAULT '0' COMMENT '是否逻辑删除',
  gmt_created DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
  gmt_modified DATETIME COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX idx_question_id USING BTREE(question_id ASC),
  INDEX idx_likes USING BTREE(likes ASC),
  INDEX idx_comments USING BTREE(comments ASC),
  INDEX idx_gmt_created USING BTREE(gmt_created ASC)
)
  ENGINE = InnoDB
  COMMENT = '回答表';

CREATE TABLE qa_comment (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  answer_id BIGINT NOT NULL DEFAULT 0 COMMENT '回答ID',
  user_id CHAR(64) NOT NULL DEFAULT '' COMMENT '评论人ID',
  user_name VARCHAR(64) DEFAULT '' COMMENT '评论人姓名',
  comment TEXT NOT NULL COMMENT '评论',
  reply_to_comment_id BIGINT DEFAULT 0 COMMENT '被回复评论ID',
  reply_to_user_id CHAR(64) DEFAULT '' COMMENT '被回复用户ID',
  reply_to_user_name VARCHAR(64) DEFAULT '' COMMENT '被回复用户名',
  is_delete CHAR(1) NOT NULL DEFAULT '0' COMMENT '是否逻辑删除',
  gmt_created DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
  gmt_modified DATETIME COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX idx_answer_id USING BTREE(answer_id ASC),
  INDEX idx_user_id USING BTREE(user_id ASC),
  INDEX idx_gmt_created USING BTREE(gmt_created ASC)
)
  ENGINE = InnoDB
  COMMENT = '回答评论表';

CREATE TABLE qa_question_evaluate (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  user_id CHAR(64) NOT NULL DEFAULT '' COMMENT '用户ID',
  user_name VARCHAR(64) DEFAULT '' COMMENT '用户姓名',
  question_id BIGINT NOT NULL DEFAULT 0 COMMENT '问题ID',
  evaluate TINYINT(2) NOT NULL DEFAULT 0 COMMENT '-1:踩,1:赞',
  is_delete CHAR(1) NOT NULL DEFAULT '0' COMMENT '是否逻辑删除',
  gmt_created DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
  gmt_modified DATETIME COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX idx_question_id USING BTREE (question_id ASC)
)
  ENGINE = InnoDB
  COMMENT = '提问评价记录表';

CREATE TABLE qa_answer_evaluate (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  user_id CHAR(64) NOT NULL DEFAULT '' COMMENT '用户ID',
  user_name VARCHAR(64) DEFAULT '' COMMENT '用户姓名',
  answer_id BIGINT NOT NULL DEFAULT 0 COMMENT '回答ID',
  evaluate TINYINT NOT NULL DEFAULT 0 COMMENT '-1:踩,1:赞',
  is_delete CHAR(1) NOT NULL DEFAULT '0' COMMENT '是否逻辑删除',
  gmt_created DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',
  gmt_modified DATETIME COMMENT '修改时间',
  PRIMARY KEY (id),
  INDEX idx_answer_id USING BTREE (answer_id ASC)
)
  ENGINE = InnoDB
  COMMENT = '回答评价记录表';