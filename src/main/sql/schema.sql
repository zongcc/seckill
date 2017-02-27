CREATE database seckill;

use seckill;

CREATE TABLE `seckill` (
	`seckill_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(120) NOT NULL,
	`number` INT(11) NOT NULL DEFAULT '0',
	`start_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`end_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`seckill_id`)
)
COMMENT='秒杀库存表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `success_killed` (
	`seckill_id` BIGINT(20) NOT NULL,
	`user_phone` BIGINT(20) NOT NULL,
	`state` TINYINT(4) NOT NULL DEFAULT '-1' COMMENT '-1:无效 0:成功',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (`seckill_id`, `user_phone`)
)
COMMENT='秒杀成功明细表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;
