-- 为用户表添加email字段
ALTER TABLE eb_user 
ADD COLUMN `email` varchar(64) DEFAULT NULL COMMENT '邮箱' AFTER `phone`;

-- 为用户表添加spreadCode字段
ALTER TABLE eb_user 
ADD COLUMN `spread_code` varchar(32) DEFAULT NULL COMMENT '推广码' AFTER `spread_time`;

-- 为email字段添加索引以提高查询效率
CREATE INDEX idx_user_email ON eb_user(email);

-- 为spread_code字段添加索引以提高查询效率
CREATE INDEX idx_user_spread_code ON eb_user(spread_code);


-- 更新现有用户，生成随机推广码
UPDATE eb_user SET spread_code = CONCAT(SUBSTRING(MD5(RAND()), 1, 8), uid) WHERE spread_code IS NULL;


