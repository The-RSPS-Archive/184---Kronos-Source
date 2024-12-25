-- --------------------------------------------------------
-- Host:                         104.248.55.78
-- Server version:               5.7.22-log - MySQL Community Server (GPL)
-- Server OS:                    Linux
-- HeidiSQL Version:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for game
CREATE DATABASE IF NOT EXISTS `game` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `game`;

-- Dumping structure for table game.abyss_victims
CREATE TABLE IF NOT EXISTS `abyss_victims` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `dateline` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ip_address` varchar(255) DEFAULT NULL,
  `totalCount` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.account_log
CREATE TABLE IF NOT EXISTS `account_log` (
  `uid` int(11) DEFAULT NULL,
  `log` mediumtext,
  `timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.actions
CREATE TABLE IF NOT EXISTS `actions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mod_name` varchar(255) NOT NULL,
  `offender` varchar(255) NOT NULL,
  `action_type` varchar(255) NOT NULL,
  `expires` varchar(255) NOT NULL,
  `reason` varchar(255) NOT NULL,
  `punished_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.bug_reports
CREATE TABLE IF NOT EXISTS `bug_reports` (
  `bug_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `userid` int(11) NOT NULL,
  `postbody` text,
  `posted_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(255) DEFAULT 'open',
  PRIMARY KEY (`bug_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.collection_box
CREATE TABLE IF NOT EXISTS `collection_box` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `item_id` int(11) DEFAULT NULL,
  `item_amount` int(11) DEFAULT '1',
  `price` double NOT NULL,
  `claimed` tinyint(1) NOT NULL DEFAULT '0',
  `bought_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `item_name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.columns_priv
CREATE TABLE IF NOT EXISTS `columns_priv` (
  `Host` char(60) COLLATE utf8_bin NOT NULL DEFAULT '',
  `Db` char(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `User` char(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `Table_name` char(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `Column_name` char(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `Timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Column_priv` set('Select','Insert','Update','References') CHARACTER SET utf8 NOT NULL DEFAULT '',
  PRIMARY KEY (`Host`,`Db`,`User`,`Table_name`,`Column_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Column privileges';

-- Data exporting was unselected.

-- Dumping structure for table game.column_stats
CREATE TABLE IF NOT EXISTS `column_stats` (
  `db_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `table_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `column_name` varchar(64) COLLATE utf8_bin NOT NULL,
  `min_value` varbinary(255) DEFAULT NULL,
  `max_value` varbinary(255) DEFAULT NULL,
  `nulls_ratio` decimal(12,4) DEFAULT NULL,
  `avg_length` decimal(12,4) DEFAULT NULL,
  `avg_frequency` decimal(12,4) DEFAULT NULL,
  `hist_size` tinyint(3) unsigned DEFAULT NULL,
  `hist_type` enum('SINGLE_PREC_HB','DOUBLE_PREC_HB') COLLATE utf8_bin DEFAULT NULL,
  `histogram` varbinary(255) DEFAULT NULL,
  PRIMARY KEY (`db_name`,`table_name`,`column_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Statistics on Columns';

-- Data exporting was unselected.

-- Dumping structure for table game.db
CREATE TABLE IF NOT EXISTS `db` (
  `Host` char(60) COLLATE utf8_bin NOT NULL DEFAULT '',
  `Db` char(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `User` char(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `Select_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Insert_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Update_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Delete_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Create_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Drop_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Grant_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `References_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Index_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Alter_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Create_tmp_table_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Lock_tables_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Create_view_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Show_view_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Create_routine_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Alter_routine_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Execute_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Event_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Trigger_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  PRIMARY KEY (`Host`,`Db`,`User`),
  KEY `User` (`User`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Database privileges';

-- Data exporting was unselected.

-- Dumping structure for table game.error_log
CREATE TABLE IF NOT EXISTS `error_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `message` varchar(255) NOT NULL,
  `json_data` json DEFAULT NULL,
  `call_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.event
CREATE TABLE IF NOT EXISTS `event` (
  `db` char(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `name` char(64) NOT NULL DEFAULT '',
  `body` longblob NOT NULL,
  `definer` char(141) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `execute_at` datetime DEFAULT NULL,
  `interval_value` int(11) DEFAULT NULL,
  `interval_field` enum('YEAR','QUARTER','MONTH','DAY','HOUR','MINUTE','WEEK','SECOND','MICROSECOND','YEAR_MONTH','DAY_HOUR','DAY_MINUTE','DAY_SECOND','HOUR_MINUTE','HOUR_SECOND','MINUTE_SECOND','DAY_MICROSECOND','HOUR_MICROSECOND','MINUTE_MICROSECOND','SECOND_MICROSECOND') DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_executed` datetime DEFAULT NULL,
  `starts` datetime DEFAULT NULL,
  `ends` datetime DEFAULT NULL,
  `status` enum('ENABLED','DISABLED','SLAVESIDE_DISABLED') NOT NULL DEFAULT 'ENABLED',
  `on_completion` enum('DROP','PRESERVE') NOT NULL DEFAULT 'DROP',
  `sql_mode` set('REAL_AS_FLOAT','PIPES_AS_CONCAT','ANSI_QUOTES','IGNORE_SPACE','IGNORE_BAD_TABLE_OPTIONS','ONLY_FULL_GROUP_BY','NO_UNSIGNED_SUBTRACTION','NO_DIR_IN_CREATE','POSTGRESQL','ORACLE','MSSQL','DB2','MAXDB','NO_KEY_OPTIONS','NO_TABLE_OPTIONS','NO_FIELD_OPTIONS','MYSQL323','MYSQL40','ANSI','NO_AUTO_VALUE_ON_ZERO','NO_BACKSLASH_ESCAPES','STRICT_TRANS_TABLES','STRICT_ALL_TABLES','NO_ZERO_IN_DATE','NO_ZERO_DATE','INVALID_DATES','ERROR_FOR_DIVISION_BY_ZERO','TRADITIONAL','NO_AUTO_CREATE_USER','HIGH_NOT_PRECEDENCE','NO_ENGINE_SUBSTITUTION','PAD_CHAR_TO_FULL_LENGTH') NOT NULL DEFAULT '',
  `comment` char(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `originator` int(10) unsigned NOT NULL,
  `time_zone` char(64) CHARACTER SET latin1 NOT NULL DEFAULT 'SYSTEM',
  `character_set_client` char(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `collation_connection` char(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `db_collation` char(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `body_utf8` longblob,
  PRIMARY KEY (`db`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Events';

-- Data exporting was unselected.

-- Dumping structure for table game.func
CREATE TABLE IF NOT EXISTS `func` (
  `name` char(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `ret` tinyint(1) NOT NULL DEFAULT '0',
  `dl` char(128) COLLATE utf8_bin NOT NULL DEFAULT '',
  `type` enum('function','aggregate') CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='User defined functions';

-- Data exporting was unselected.

-- Dumping structure for table game.general_log
CREATE TABLE IF NOT EXISTS `general_log` (
  `event_time` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `user_host` mediumtext NOT NULL,
  `thread_id` bigint(21) unsigned NOT NULL,
  `server_id` int(10) unsigned NOT NULL,
  `command_type` varchar(64) NOT NULL,
  `argument` mediumtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='General log';

-- Data exporting was unselected.

-- Dumping structure for table game.giveaway_entries
CREATE TABLE IF NOT EXISTS `giveaway_entries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `ip_address` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.help_category
CREATE TABLE IF NOT EXISTS `help_category` (
  `help_category_id` smallint(5) unsigned NOT NULL,
  `name` char(64) NOT NULL,
  `parent_category_id` smallint(5) unsigned DEFAULT NULL,
  `url` text NOT NULL,
  PRIMARY KEY (`help_category_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='help categories';

-- Data exporting was unselected.

-- Dumping structure for table game.help_keyword
CREATE TABLE IF NOT EXISTS `help_keyword` (
  `help_keyword_id` int(10) unsigned NOT NULL,
  `name` char(64) NOT NULL,
  PRIMARY KEY (`help_keyword_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='help keywords';

-- Data exporting was unselected.

-- Dumping structure for table game.help_relation
CREATE TABLE IF NOT EXISTS `help_relation` (
  `help_topic_id` int(10) unsigned NOT NULL,
  `help_keyword_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`help_keyword_id`,`help_topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='keyword-topic relation';

-- Data exporting was unselected.

-- Dumping structure for table game.help_topic
CREATE TABLE IF NOT EXISTS `help_topic` (
  `help_topic_id` int(10) unsigned NOT NULL,
  `name` char(64) NOT NULL,
  `help_category_id` smallint(5) unsigned NOT NULL,
  `description` text NOT NULL,
  `example` text NOT NULL,
  `url` text NOT NULL,
  PRIMARY KEY (`help_topic_id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='help topics';

-- Data exporting was unselected.

-- Dumping structure for table game.ip_banned_users
CREATE TABLE IF NOT EXISTS `ip_banned_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `banned_ip` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_index` (`banned_ip`),
  UNIQUE KEY `user_id_uindex` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.items
CREATE TABLE IF NOT EXISTS `items` (
  `user_id` int(11) DEFAULT NULL,
  `id` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `container` varchar(20) DEFAULT NULL,
  `slot` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.item_definitions
CREATE TABLE IF NOT EXISTS `item_definitions` (
  `id` int(11) DEFAULT NULL,
  `name` text,
  `descriptive_name` text,
  `value` bigint(20) DEFAULT NULL,
  `stackable` tinyint(1) DEFAULT NULL,
  `placeholder` tinyint(1) DEFAULT NULL,
  `noted` tinyint(4) DEFAULT NULL,
  KEY `id` (`id`),
  KEY `id_2` (`id`),
  KEY `id_3` (`id`),
  KEY `id_4` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_clan_chat
CREATE TABLE IF NOT EXISTS `logs_clan_chat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `user_ip` varchar(20) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `world_id` smallint(6) DEFAULT NULL,
  `world_stage` varchar(20) DEFAULT NULL,
  `world_type` varchar(20) DEFAULT NULL,
  `time_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=134381 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_commands
CREATE TABLE IF NOT EXISTS `logs_commands` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `user_ip` varchar(20) DEFAULT NULL,
  `cmd_query` varchar(255) DEFAULT NULL,
  `world_id` smallint(6) DEFAULT NULL,
  `world_stage` varchar(20) DEFAULT NULL,
  `world_type` varchar(20) DEFAULT NULL,
  `time_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=115343 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_dangerous_deaths
CREATE TABLE IF NOT EXISTS `logs_dangerous_deaths` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `user_ip` varchar(20) DEFAULT NULL,
  `killer_id` int(11) DEFAULT NULL,
  `killer_name` varchar(20) DEFAULT NULL,
  `killer_ip` varchar(20) DEFAULT NULL,
  `items_kept` json DEFAULT NULL,
  `items_lost` json DEFAULT NULL,
  `world_id` smallint(6) DEFAULT NULL,
  `world_stage` varchar(20) DEFAULT NULL,
  `world_type` varchar(20) DEFAULT NULL,
  `time_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3791 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_drop_trades
CREATE TABLE IF NOT EXISTS `logs_drop_trades` (
  `taker_id` int(11) DEFAULT NULL,
  `dropper_id` int(11) DEFAULT NULL,
  `taker_ip` varchar(100) DEFAULT NULL,
  `dropper_ip` varchar(100) DEFAULT NULL,
  `taker_name` varchar(12) DEFAULT NULL,
  `dropper_name` varchar(12) DEFAULT NULL,
  `id` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `value` int(11) DEFAULT NULL,
  `x` int(11) DEFAULT NULL,
  `y` int(11) DEFAULT NULL,
  `z` int(11) DEFAULT NULL,
  `world` int(11) DEFAULT NULL,
  `time_dropped` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_duel_stakes
CREATE TABLE IF NOT EXISTS `logs_duel_stakes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id1` int(11) DEFAULT NULL,
  `user_name1` varchar(20) DEFAULT NULL,
  `user_ip1` varchar(20) DEFAULT NULL,
  `user_id2` int(11) DEFAULT NULL,
  `user_name2` varchar(20) DEFAULT NULL,
  `user_ip2` varchar(20) DEFAULT NULL,
  `items1` json DEFAULT NULL,
  `items2` json DEFAULT NULL,
  `winner_id` int(11) DEFAULT NULL,
  `world_id` smallint(6) DEFAULT NULL,
  `world_stage` varchar(20) DEFAULT NULL,
  `world_type` varchar(20) DEFAULT NULL,
  `time_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_instances
CREATE TABLE IF NOT EXISTS `logs_instances` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `instance_type` varchar(50) DEFAULT NULL,
  `instance_cost` int(11) DEFAULT NULL,
  `time_created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `time_destroyed` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `world_id` int(11) DEFAULT NULL,
  `world_stage` varchar(10) DEFAULT NULL,
  `world_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=184 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_item_drops
CREATE TABLE IF NOT EXISTS `logs_item_drops` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `user_ip` varchar(20) DEFAULT NULL,
  `item_id` smallint(6) DEFAULT NULL,
  `item_name` varchar(40) DEFAULT NULL,
  `item_amount` int(11) DEFAULT NULL,
  `x` smallint(6) DEFAULT NULL,
  `y` smallint(6) DEFAULT NULL,
  `z` tinyint(4) DEFAULT NULL,
  `world_id` smallint(6) DEFAULT NULL,
  `world_stage` varchar(20) DEFAULT NULL,
  `world_type` varchar(20) DEFAULT NULL,
  `time_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=318193 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_item_pickups
CREATE TABLE IF NOT EXISTS `logs_item_pickups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `user_ip` varchar(20) DEFAULT NULL,
  `item_id` smallint(6) DEFAULT NULL,
  `item_name` varchar(40) DEFAULT NULL,
  `item_amount` int(11) DEFAULT NULL,
  `x` smallint(6) DEFAULT NULL,
  `y` smallint(6) DEFAULT NULL,
  `z` tinyint(4) DEFAULT NULL,
  `world_id` smallint(6) DEFAULT NULL,
  `world_stage` varchar(20) DEFAULT NULL,
  `world_type` varchar(20) DEFAULT NULL,
  `time_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=634854 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_loyalty_chest
CREATE TABLE IF NOT EXISTS `logs_loyalty_chest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `user_ip` varchar(20) DEFAULT NULL,
  `spree` tinyint(4) DEFAULT NULL,
  `rewards` json DEFAULT NULL,
  `world_id` smallint(6) DEFAULT NULL,
  `world_stage` varchar(20) DEFAULT NULL,
  `world_type` varchar(20) DEFAULT NULL,
  `time_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_players
CREATE TABLE IF NOT EXISTS `logs_players` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `world_stage` varchar(20) DEFAULT NULL,
  `world_type` varchar(20) DEFAULT NULL,
  `bank` longtext,
  `inventory` longtext,
  `equipment` longtext,
  `total_wealth` mediumtext,
  `total_play_time` mediumtext,
  `rights` int(11) DEFAULT NULL,
  `icon` int(11) DEFAULT NULL,
  `ironmode` int(11) DEFAULT NULL,
  `x` int(11) DEFAULT NULL,
  `z` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `last_ip` varchar(16) DEFAULT NULL,
  `last_online` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `muted_until` timestamp NULL DEFAULT NULL,
  `banned_until` timestamp NULL DEFAULT NULL,
  `wilderness_points` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `user_id_2` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2831 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_private_chat
CREATE TABLE IF NOT EXISTS `logs_private_chat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `user_ip` varchar(20) DEFAULT NULL,
  `friend_name` varchar(20) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `world_id` smallint(6) DEFAULT NULL,
  `world_stage` varchar(20) DEFAULT NULL,
  `world_type` varchar(20) DEFAULT NULL,
  `time_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=85924 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_public_chat
CREATE TABLE IF NOT EXISTS `logs_public_chat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `user_ip` varchar(20) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL,
  `effects` smallint(6) DEFAULT NULL,
  `world_id` smallint(6) DEFAULT NULL,
  `world_stage` varchar(20) DEFAULT NULL,
  `world_type` varchar(20) DEFAULT NULL,
  `time_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=310028 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_raids_completed
CREATE TABLE IF NOT EXISTS `logs_raids_completed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `players` varchar(250) DEFAULT NULL,
  `duration` varchar(250) DEFAULT NULL,
  `total_points` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_raids_uniques
CREATE TABLE IF NOT EXISTS `logs_raids_uniques` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player` varchar(50) DEFAULT NULL,
  `item` varchar(50) DEFAULT NULL,
  `raids_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_sessions
CREATE TABLE IF NOT EXISTS `logs_sessions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `user_ip` varchar(20) DEFAULT NULL,
  `world_id` smallint(6) DEFAULT NULL,
  `world_stage` varchar(20) DEFAULT NULL,
  `world_type` varchar(20) DEFAULT NULL,
  `time_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `logs_sessions_user_id` (`user_id`),
  KEY `logs_sessions_user_ip_index` (`user_ip`)
) ENGINE=InnoDB AUTO_INCREMENT=32240 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_shop_buys
CREATE TABLE IF NOT EXISTS `logs_shop_buys` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `user_ip` varchar(20) DEFAULT NULL,
  `item_id` smallint(6) DEFAULT NULL,
  `item_name` varchar(40) DEFAULT NULL,
  `item_price` int(11) DEFAULT NULL,
  `buy_amount` int(11) DEFAULT NULL,
  `world_id` smallint(6) DEFAULT NULL,
  `world_stage` varchar(20) DEFAULT NULL,
  `world_type` varchar(20) DEFAULT NULL,
  `time_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2257 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_staff_bounty_kills
CREATE TABLE IF NOT EXISTS `logs_staff_bounty_kills` (
  `killer_name` varchar(11) DEFAULT NULL,
  `killed_name` varchar(11) DEFAULT NULL,
  `killed_group_id` int(11) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_tournament_results
CREATE TABLE IF NOT EXISTS `logs_tournament_results` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `user_ip` varchar(20) DEFAULT NULL,
  `place` tinyint(4) DEFAULT NULL,
  `world_id` smallint(6) DEFAULT NULL,
  `world_stage` varchar(20) DEFAULT NULL,
  `world_type` varchar(20) DEFAULT NULL,
  `time_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_trades
CREATE TABLE IF NOT EXISTS `logs_trades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id1` int(11) DEFAULT NULL,
  `user_name1` varchar(20) DEFAULT NULL,
  `user_ip1` varchar(20) DEFAULT NULL,
  `user_id2` int(11) DEFAULT NULL,
  `user_name2` varchar(20) DEFAULT NULL,
  `user_ip2` varchar(20) DEFAULT NULL,
  `items1` json DEFAULT NULL,
  `items2` json DEFAULT NULL,
  `world_id` smallint(6) DEFAULT NULL,
  `world_stage` varchar(20) DEFAULT NULL,
  `world_type` varchar(20) DEFAULT NULL,
  `time_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.logs_yell
CREATE TABLE IF NOT EXISTS `logs_yell` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(12) DEFAULT NULL,
  `user_ip` varchar(20) DEFAULT NULL,
  `time_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `message` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12236 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.lost_cannons
CREATE TABLE IF NOT EXISTS `lost_cannons` (
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table game.member_notes
CREATE TABLE IF NOT EXISTS `member_notes` (
  `note` text,
  `account_id` int(11) DEFAULT NULL,
  `by_id` int(11) DEFAULT NULL,
  `time_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `account_name` varchar(12) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.npc_tables
CREATE TABLE IF NOT EXISTS `npc_tables` (
  `npc_id` int(11) NOT NULL,
  `npc_name` varchar(255) DEFAULT NULL,
  `drops` json NOT NULL,
  `stats` json NOT NULL,
  PRIMARY KEY (`npc_id`),
  UNIQUE KEY `npc_tables_npc_id_uindex` (`npc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.online_characters
CREATE TABLE IF NOT EXISTS `online_characters` (
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `world_id` int(11) DEFAULT NULL,
  `ip` varchar(16) DEFAULT NULL,
  `since` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_helper` tinyint(1) DEFAULT NULL,
  `is_moderator` tinyint(1) DEFAULT NULL,
  `is_administrator` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.online_statistics
CREATE TABLE IF NOT EXISTS `online_statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `world` int(11) DEFAULT NULL,
  `players` int(11) DEFAULT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13740 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.pvp_hiscores
CREATE TABLE IF NOT EXISTS `pvp_hiscores` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `kills` int(11) NOT NULL,
  `deaths` int(11) NOT NULL,
  `killstreak` int(11) NOT NULL,
  `shutdown` int(11) NOT NULL,
  `pk_rating` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.sigmund_sales
CREATE TABLE IF NOT EXISTS `sigmund_sales` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `sigmund_sales_item_id_index` (`item_id`),
  KEY `sigmund_sales_user_id_index` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3447 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table game.staff_bounty
CREATE TABLE IF NOT EXISTS `staff_bounty` (
  `active` tinyint(1) DEFAULT NULL,
  `support_deaths` int(11) DEFAULT NULL,
  `moderator_deaths` int(11) DEFAULT NULL,
  `administrator_deaths` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.trades
CREATE TABLE IF NOT EXISTS `trades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trader1_account_id` int(11) DEFAULT NULL,
  `trader2_account_id` int(11) DEFAULT NULL,
  `trader1_value` bigint(20) DEFAULT NULL,
  `trader2_value` bigint(20) DEFAULT NULL,
  `trader1_ip` varchar(16) DEFAULT NULL,
  `trader2_ip` varchar(16) DEFAULT NULL,
  `world` int(11) DEFAULT NULL,
  `x` int(11) DEFAULT NULL,
  `z` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `trader1_user` varchar(12) DEFAULT NULL,
  `trader2_user` varchar(12) DEFAULT NULL,
  `time_added` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `time_added` (`time_added`)
) ENGINE=InnoDB AUTO_INCREMENT=8418 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.trade_items
CREATE TABLE IF NOT EXISTS `trade_items` (
  `trade_id` int(11) DEFAULT NULL,
  `account_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `amount` bigint(20) DEFAULT NULL,
  KEY `trade_items_trades_id_fk` (`trade_id`),
  CONSTRAINT `trade_items_trades_id_fk` FOREIGN KEY (`trade_id`) REFERENCES `trades` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.

-- Dumping structure for table game.user
CREATE TABLE IF NOT EXISTS `user` (
  `Host` char(60) COLLATE utf8_bin NOT NULL DEFAULT '',
  `User` char(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `Password` char(41) CHARACTER SET latin1 COLLATE latin1_bin NOT NULL DEFAULT '',
  `Select_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Insert_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Update_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Delete_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Create_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Drop_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Reload_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Shutdown_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Process_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `File_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Grant_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `References_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Index_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Alter_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Show_db_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Super_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Create_tmp_table_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Lock_tables_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Execute_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Repl_slave_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Repl_client_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Create_view_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Show_view_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Create_routine_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Alter_routine_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Create_user_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Event_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Trigger_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `Create_tablespace_priv` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `ssl_type` enum('','ANY','X509','SPECIFIED') CHARACTER SET utf8 NOT NULL DEFAULT '',
  `ssl_cipher` blob NOT NULL,
  `x509_issuer` blob NOT NULL,
  `x509_subject` blob NOT NULL,
  `max_questions` int(11) unsigned NOT NULL DEFAULT '0',
  `max_updates` int(11) unsigned NOT NULL DEFAULT '0',
  `max_connections` int(11) unsigned NOT NULL DEFAULT '0',
  `max_user_connections` int(11) NOT NULL DEFAULT '0',
  `plugin` char(64) CHARACTER SET latin1 NOT NULL DEFAULT '',
  `authentication_string` text COLLATE utf8_bin NOT NULL,
  `password_expired` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `is_role` enum('N','Y') CHARACTER SET utf8 NOT NULL DEFAULT 'N',
  `default_role` char(80) COLLATE utf8_bin NOT NULL DEFAULT '',
  `max_statement_time` decimal(12,6) NOT NULL DEFAULT '0.000000',
  PRIMARY KEY (`Host`,`User`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Users and global privileges';

-- Data exporting was unselected.

-- Dumping structure for table game.votes
CREATE TABLE IF NOT EXISTS `votes` (
  `userid` int(11) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `site` varchar(255) DEFAULT NULL,
  `claimed` tinyint(1) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping database structure for kronos
CREATE DATABASE IF NOT EXISTS `kronos` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `kronos`;

-- Dumping structure for table kronos.categories
CREATE TABLE IF NOT EXISTS `categories` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sort_order` int(10) unsigned NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table kronos.failed_jobs
CREATE TABLE IF NOT EXISTS `failed_jobs` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `connection` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `queue` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `payload` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `exception` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table kronos.highscores
CREATE TABLE IF NOT EXISTS `highscores` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gamemode` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `xpmode` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `overall_exp` bigint(20) unsigned NOT NULL,
  `overall_level` int(10) unsigned NOT NULL,
  `attack_exp` int(10) unsigned NOT NULL,
  `attack_level` int(10) unsigned NOT NULL,
  `defence_exp` int(10) unsigned NOT NULL,
  `defence_level` int(10) unsigned NOT NULL,
  `strength_exp` int(10) unsigned NOT NULL,
  `strength_level` int(10) unsigned NOT NULL,
  `hitpoints_exp` int(10) unsigned NOT NULL,
  `hitpoints_level` int(10) unsigned NOT NULL,
  `ranged_exp` int(10) unsigned NOT NULL,
  `ranged_level` int(10) unsigned NOT NULL,
  `prayer_exp` int(10) unsigned NOT NULL,
  `prayer_level` int(10) unsigned NOT NULL,
  `magic_exp` int(10) unsigned NOT NULL,
  `magic_level` int(10) unsigned NOT NULL,
  `cooking_exp` int(10) unsigned NOT NULL,
  `cooking_level` int(10) unsigned NOT NULL,
  `woodcutting_exp` int(10) unsigned NOT NULL,
  `woodcutting_level` int(10) unsigned NOT NULL,
  `fletching_exp` int(10) unsigned NOT NULL,
  `fletching_level` int(10) unsigned NOT NULL,
  `fishing_exp` int(10) unsigned NOT NULL,
  `fishing_level` int(10) unsigned NOT NULL,
  `firemaking_exp` int(10) unsigned NOT NULL,
  `firemaking_level` int(10) unsigned NOT NULL,
  `crafting_exp` int(10) unsigned NOT NULL,
  `crafting_level` int(10) unsigned NOT NULL,
  `smithing_exp` int(10) unsigned NOT NULL,
  `smithing_level` int(10) unsigned NOT NULL,
  `mining_exp` int(10) unsigned NOT NULL,
  `mining_level` int(10) unsigned NOT NULL,
  `herblore_exp` int(10) unsigned NOT NULL,
  `herblore_level` int(10) unsigned NOT NULL,
  `agility_exp` int(10) unsigned NOT NULL,
  `agility_level` int(10) unsigned NOT NULL,
  `thieving_exp` int(10) unsigned NOT NULL,
  `thieving_level` int(10) unsigned NOT NULL,
  `slayer_exp` int(10) unsigned NOT NULL,
  `slayer_level` int(10) unsigned NOT NULL,
  `farming_exp` int(10) unsigned NOT NULL,
  `farming_level` int(10) unsigned NOT NULL,
  `runecrafting_exp` int(10) unsigned NOT NULL,
  `runecrafting_level` int(10) unsigned NOT NULL,
  `hunter_exp` int(10) unsigned NOT NULL,
  `hunter_level` int(10) unsigned NOT NULL,
  `construction_exp` int(10) unsigned DEFAULT NULL,
  `construction_level` int(10) unsigned DEFAULT NULL,
  `kills` int(10) unsigned DEFAULT NULL,
  `deaths` int(10) unsigned DEFAULT NULL,
  `highest_shutdown` int(10) unsigned DEFAULT NULL,
  `highest_killspress` int(10) unsigned DEFAULT NULL,
  `pk_rating` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2722 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table kronos.migrations
CREATE TABLE IF NOT EXISTS `migrations` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `migration` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table kronos.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `status` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `player_name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `promo_code` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `payment_method` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Paypal',
  `total_payment_amount` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `payee_email` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `payee_first_name` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `payee_last_name` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `payee_payer_id` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `payee_country_code` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `payee_business_name` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `payee_ip` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `payee_receipt_id` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `payee_transaction_id` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `total_payment_amount_after_fee` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT '0',
  `transaction_fee` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `claimed_at` datetime DEFAULT NULL,
  `claimed_ip` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `claimed_mac` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `stripe_order_id` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=894 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table kronos.order_lines
CREATE TABLE IF NOT EXISTS `order_lines` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `product_id` bigint(20) unsigned DEFAULT NULL,
  `order_id` bigint(20) unsigned DEFAULT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `product_name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_category` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_description` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_image` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_price` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_item_id` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_amount` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `product_discount` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_lines_product_id_foreign` (`product_id`),
  KEY `order_lines_order_id_foreign` (`order_id`),
  CONSTRAINT `order_lines_order_id_foreign` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE SET NULL,
  CONSTRAINT `order_lines_product_id_foreign` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=1540 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table kronos.password_resets
CREATE TABLE IF NOT EXISTS `password_resets` (
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  KEY `password_resets_email_index` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table kronos.players_online
CREATE TABLE IF NOT EXISTS `players_online` (
  `players_online` int(10) unsigned NOT NULL,
  `wilderness_count` int(10) unsigned DEFAULT NULL,
  `instance_count` int(10) unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table kronos.products
CREATE TABLE IF NOT EXISTS `products` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) unsigned DEFAULT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `image` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `price` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `discount` decimal(8,2) unsigned DEFAULT '0.00',
  PRIMARY KEY (`id`),
  KEY `products_category_id_foreign` (`category_id`),
  CONSTRAINT `products_category_id_foreign` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table kronos.promo_codes
CREATE TABLE IF NOT EXISTS `promo_codes` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `limit` int(10) unsigned NOT NULL,
  `percent_discount` int(10) unsigned NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table kronos.referal_links
CREATE TABLE IF NOT EXISTS `referal_links` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `referrer` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `referrer_key` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `referrer_ip` text COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=664 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table kronos.referal_link_tracking
CREATE TABLE IF NOT EXISTS `referal_link_tracking` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `site_id` bigint(20) unsigned DEFAULT NULL,
  `ip` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `referal_link_tracking_site_id_foreign` (`site_id`),
  CONSTRAINT `referal_link_tracking_site_id_foreign` FOREIGN KEY (`site_id`) REFERENCES `referal_sites` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=186 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table kronos.referal_sites
CREATE TABLE IF NOT EXISTS `referal_sites` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `site_name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `site_url` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `url_slug` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='select * ';

-- Data exporting was unselected.

-- Dumping structure for table kronos.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_email_unique` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table kronos.votes
CREATE TABLE IF NOT EXISTS `votes` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ip` varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `site_id` bigint(20) unsigned DEFAULT NULL,
  `vote_key` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `completed` tinyint(4) NOT NULL DEFAULT '0',
  `date_voted` datetime NOT NULL,
  `date_claimed` datetime DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `votes_site_id_foreign` (`site_id`),
  CONSTRAINT `votes_site_id_foreign` FOREIGN KEY (`site_id`) REFERENCES `vote_sites` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=14091 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table kronos.vote_sites
CREATE TABLE IF NOT EXISTS `vote_sites` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `url` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `image` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `points_given` int(10) unsigned NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
