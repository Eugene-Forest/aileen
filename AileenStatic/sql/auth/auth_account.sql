-- ============================================================
-- AuthMod 登录模块 - 账号表
-- 数据库: MySQL 8.x
-- 作者: Eugene-Forest
-- ============================================================

-- 如果数据库不存在，可手动创建
-- CREATE DATABASE IF NOT EXISTS aileen_auth DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- -----------------------------------------------------------
-- 表: auth_account  账号表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `auth_account`;
CREATE TABLE `auth_account` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username`    VARCHAR(64)  NOT NULL                COMMENT '用户名（登录名）',
    `password`    VARCHAR(128) NOT NULL                COMMENT '密码（BCrypt加密存储）',
    `nickname`    VARCHAR(64)  DEFAULT NULL            COMMENT '昵称',
    `email`       VARCHAR(128) DEFAULT NULL            COMMENT '邮箱',
    `phone`       VARCHAR(20)  DEFAULT NULL            COMMENT '手机号',
    `avatar`      VARCHAR(256) DEFAULT NULL            COMMENT '头像URL',
    `status`      TINYINT      NOT NULL DEFAULT 1      COMMENT '账号状态: 0-禁用 1-正常 2-锁定',
    `login_ip`    VARCHAR(64)  DEFAULT NULL            COMMENT '最后登录IP',
    `login_time`  DATETIME     DEFAULT NULL            COMMENT '最后登录时间',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     TINYINT      NOT NULL DEFAULT 0      COMMENT '逻辑删除: 0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='账号表';

-- -----------------------------------------------------------
-- 初始数据: 插入默认管理员账号
-- 密码为 123456 的 BCrypt 加密值
-- -----------------------------------------------------------
INSERT INTO `auth_account` (`username`, `password`, `nickname`, `status`)
VALUES ('admin', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36PQm1z0NBee.SKmmfQLDli', '系统管理员', 1);

