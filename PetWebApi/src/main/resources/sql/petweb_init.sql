-- -------------------------------------------------------------
-- 宠物管理平台 (petweb) 数据库初始化脚本
-- 版本: 1.0
-- 描述: 该脚本用于创建数据库 `petweb` 及其所有核心表结构。
-- -------------------------------------------------------------

-- 创建数据库 (如果不存在)
CREATE DATABASE IF NOT EXISTS `petweb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE `petweb`;

-- ----------------------------
-- 1. 用户表 (users)
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
  `password` VARCHAR(20) NOT NULL COMMENT '密码',
  `email` VARCHAR(100) NOT NULL UNIQUE COMMENT '电子邮箱',
  `phone_number` VARCHAR(20) UNIQUE COMMENT '手机号码',
  `nickname` VARCHAR(50) COMMENT '用户昵称',
  `avatar_url` VARCHAR(255) COMMENT '用户头像URL',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_username` (`username`),
  INDEX `idx_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ----------------------------
-- 2. 宠物表 (pets)
-- ----------------------------
DROP TABLE IF EXISTS `pets`;
CREATE TABLE `pets` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '宠物ID',
  `user_id` INT UNSIGNED NOT NULL COMMENT '所属用户ID',
  `name` VARCHAR(50) NOT NULL COMMENT '宠物名字',
  `species` VARCHAR(50) COMMENT '物种 (如: 猫, 狗)',
  `breed` VARCHAR(50) COMMENT '品种 (如: 布偶, 金毛)',
  `gender` ENUM('male', 'female', 'neutered', 'spayed', 'unknown') DEFAULT 'unknown' COMMENT '性别 (雄性, 雌性, 已绝育雄性, 已绝育雌性, 未知)',
  `birth_date` DATE COMMENT '出生日期',
  `avatar_url` VARCHAR(255) COMMENT '宠物头像URL',
  `notes` TEXT COMMENT '备注信息',
  `is_active` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否有效 (1:是, 0:否, 用于软删除)',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id`),
  CONSTRAINT `fk_pets_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='宠物信息表';

-- ----------------------------
-- 3. 健康记录表 (health_records)
-- ----------------------------
DROP TABLE IF EXISTS `health_records`;
CREATE TABLE `health_records` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `pet_id` INT UNSIGNED NOT NULL COMMENT '所属宠物ID',
  `record_type` ENUM('vaccination', 'deworming', 'vet_visit', 'medication', 'allergy', 'weight', 'other') NOT NULL COMMENT '记录类型',
  `title` VARCHAR(100) NOT NULL COMMENT '事件标题 (如: 猫三联第一针, 体内驱虫)',
  `description` TEXT COMMENT '详细描述/病历',
  `record_date` DATE NOT NULL COMMENT '事件发生日期',
  `vet_clinic` VARCHAR(100) COMMENT '兽医诊所名称',
  `next_due_date` DATE COMMENT '下次提醒日期 (如: 下一针疫苗)',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  INDEX `idx_pet_id` (`pet_id`),
  INDEX `idx_record_type` (`record_type`),
  CONSTRAINT `fk_health_records_pet_id` FOREIGN KEY (`pet_id`) REFERENCES `pets` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='宠物健康记录表';

-- ----------------------------
-- 4. 日程提醒表 (reminders)
-- ----------------------------
DROP TABLE IF EXISTS `reminders`;
CREATE TABLE `reminders` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '提醒ID',
  `pet_id` INT UNSIGNED NOT NULL COMMENT '关联宠物ID',
  `user_id` INT UNSIGNED NOT NULL COMMENT '所属用户ID (方便直接按用户查询)',
  `title` VARCHAR(100) NOT NULL COMMENT '提醒标题 (如: 该给主子铲屎了)',
  `event_type` ENUM('grooming', 'feeding', 'walking', 'medication', 'other') NOT NULL COMMENT '事件类型',
  `event_time` DATETIME NOT NULL COMMENT '事件发生时间',
  `is_recurring` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否为重复事件 (0:否, 1:是)',
  `recurrence_rule` VARCHAR(100) COMMENT '重复规则 (如: "daily", "weekly", 或更复杂的RRULE字符串)',
  `is_completed` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否已完成 (0:否, 1:是)',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  INDEX `idx_pet_id_event_time` (`pet_id`, `event_time`),
  INDEX `idx_user_id_event_time` (`user_id`, `event_time`),
  CONSTRAINT `fk_reminders_pet_id` FOREIGN KEY (`pet_id`) REFERENCES `pets` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_reminders_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='日程提醒表';


-- -----------------------------------------------------
-- 可选模块: 社交功能
-- -----------------------------------------------------

-- ----------------------------
-- 5. 动态/帖子表 (posts)
-- ----------------------------
DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '帖子ID',
  `user_id` INT UNSIGNED NOT NULL COMMENT '发布者ID',
  `title` VARCHAR(150) COMMENT '帖子标题',
  `content` TEXT NOT NULL COMMENT '帖子内容',
  `image_urls` JSON COMMENT '图片URL列表 (JSON数组)',
  `likes_count` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '点赞数',
  `comments_count` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '评论数',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  PRIMARY KEY (`id`),
  INDEX `idx_user_id` (`user_id`),
  CONSTRAINT `fk_posts_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='社区动态/帖子表';

-- ----------------------------
-- 6. 评论表 (comments)
-- ----------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `post_id` INT UNSIGNED NOT NULL COMMENT '所属帖子ID',
  `user_id` INT UNSIGNED NOT NULL COMMENT '评论者ID',
  `parent_comment_id` INT UNSIGNED DEFAULT NULL COMMENT '父评论ID (用于实现楼中楼)',
  `content` TEXT NOT NULL COMMENT '评论内容',
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '评论时间',
  PRIMARY KEY (`id`),
  INDEX `idx_post_id` (`post_id`),
  INDEX `idx_user_id` (`user_id`),
  CONSTRAINT `fk_comments_post_id` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_comments_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_comments_parent_id` FOREIGN KEY (`parent_comment_id`) REFERENCES `comments` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='帖子评论表';


-- ----------------------------
-- 脚本执行完毕
-- ----------------------------
SELECT '数据库 `petweb` 及所有表结构创建成功！' AS '状态';