-- ============================================================
-- DataSourceMod 动态多数据源模块 - 账套与数据源配置表
-- 数据库: MySQL 8.x
-- 作者: Eugene-Forest
-- ============================================================

-- 如果数据库不存在，可手动创建
-- CREATE DATABASE IF NOT EXISTS aileen_datasource DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- -----------------------------------------------------------
-- 表: ds_account_set  账套表
-- 说明: 记录系统中的所有账套信息，每个账套可包含多个数据源
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `ds_account_set`;
CREATE TABLE `ds_account_set` (
    `id`               BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID（账套ID）',
    `account_set_name` VARCHAR(128) NOT NULL                COMMENT '账套名称',
    `service_name`     VARCHAR(128) DEFAULT NULL            COMMENT '服务名称',
    `defaulted`        TINYINT      NOT NULL DEFAULT 0      COMMENT '是否默认账套: 0-否 1-是',
    `create_time`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`          TINYINT      NOT NULL DEFAULT 0      COMMENT '逻辑删除: 0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_account_set_name` (`account_set_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据源模块-账套表';

-- -----------------------------------------------------------
-- 表: ds_data_source  数据源配置表
-- 说明: 记录每个账套下的数据源配置信息，支持多账套多数据源
--       db_server 和 db_password 存储 RSA 加密后的密文
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `ds_data_source`;
CREATE TABLE `ds_data_source` (
    `id`           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `account_set_id` BIGINT     NOT NULL                COMMENT '所属账套ID（关联 ds_account_set.id）',
    `db_id`        VARCHAR(64)  NOT NULL                COMMENT '逻辑数据源标识（如 d1, d2，对应配置中的 logic.names）',
    `db_name`      VARCHAR(128) NOT NULL                COMMENT '数据库名称',
    `db_type`      VARCHAR(16)  NOT NULL                COMMENT '数据库类型: 0-MySQL 1-SQL Server',
    `db_server`    VARCHAR(256) NOT NULL                COMMENT '数据库服务器地址（RSA加密存储）',
    `db_user`      VARCHAR(64)  NOT NULL                COMMENT '数据库用户名',
    `db_password`  VARCHAR(256) NOT NULL                COMMENT '数据库密码（RSA加密存储）',
    `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      TINYINT      NOT NULL DEFAULT 0      COMMENT '逻辑删除: 0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_account_set_id` (`account_set_id`),
    KEY `idx_db_id` (`db_id`),
    CONSTRAINT `fk_ds_data_source_account_set` FOREIGN KEY (`account_set_id`) REFERENCES `ds_account_set` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据源模块-数据源配置表';

-- -----------------------------------------------------------
-- 初始数据: 插入示例账套及数据源配置
-- 对应 datasourceset.json 中的配置数据
-- -----------------------------------------------------------

-- 账套 1: TuTor_Ali（默认账套）
INSERT INTO `ds_account_set` (`id`, `account_set_name`, `service_name`, `defaulted`)
VALUES (1, 'TuTor_Ali', 'TuTor_Ali', 1);

-- 账套 2: TuTor_tencent
INSERT INTO `ds_account_set` (`id`, `account_set_name`, `service_name`, `defaulted`)
VALUES (2, 'TuTor_tencent', 'TuTor_tencent', 0);

-- 账套 TuTor_Ali 的数据源
INSERT INTO `ds_data_source` (`account_set_id`, `db_id`, `db_name`, `db_type`, `db_server`, `db_user`, `db_password`)
VALUES (1, 'd1', 'Tutor_Ali', '0', 'pvtWUUSGkPkI5UgY/21XAA==', 'tutor', 'FOkp6qZys0VmXMASWM5lmg==');

INSERT INTO `ds_data_source` (`account_set_id`, `db_id`, `db_name`, `db_type`, `db_server`, `db_user`, `db_password`)
VALUES (1, 'd2', 'LC', '1', 'IMHhRmSp8RFNqPnbZodhCw==', 'sa', 'uHs8DLDrL1wF2ZucAzdNDw==');

-- 账套 TuTor_tencent 的数据源
INSERT INTO `ds_data_source` (`account_set_id`, `db_id`, `db_name`, `db_type`, `db_server`, `db_user`, `db_password`)
VALUES (2, 'd1', 'seata_example_order', '1', 'IMHhRmSp8RFNqPnbZodhCw==', 'sa', 'uHs8DLDrL1wF2ZucAzdNDw==');

INSERT INTO `ds_data_source` (`account_set_id`, `db_id`, `db_name`, `db_type`, `db_server`, `db_user`, `db_password`)
VALUES (2, 'd3', 'seata_example_account', '1', 'IMHhRmSp8RFNqPnbZodhCw==', 'sa', 'uHs8DLDrL1wF2ZucAzdNDw==');

INSERT INTO `ds_data_source` (`account_set_id`, `db_id`, `db_name`, `db_type`, `db_server`, `db_user`, `db_password`)
VALUES (2, 'd2', 'seata_example_product', '1', 'IMHhRmSp8RFNqPnbZodhCw==', 'sa', 'uHs8DLDrL1wF2ZucAzdNDw==');

