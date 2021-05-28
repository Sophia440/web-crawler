SET GLOBAL time_zone = '-3:00';
DROP DATABASE IF EXISTS statistics_db;
CREATE DATABASE statistics_db DEFAULT CHARACTER SET UTF8MB4;

USE statistics_db;
DROP TABLE IF EXISTS term, link, page_statistics;

CREATE TABLE term (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL UNIQUE,
	PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARACTER SET UTF8MB4;

CREATE TABLE link (
	id BIGINT NOT NULL AUTO_INCREMENT,
	url VARCHAR(255) NOT NULL UNIQUE,
	PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARACTER SET UTF8MB4;

CREATE TABLE page_statistics (
	id BIGINT NOT NULL AUTO_INCREMENT,
	link_id BIGINT NOT NULL,
	term_id BIGINT NOT NULL,
    term_count BIGINT NOT NULL,
	PRIMARY KEY (id),
    FOREIGN KEY (link_id) 
    REFERENCES link (id)
	ON UPDATE CASCADE
	ON DELETE RESTRICT,
    FOREIGN KEY (term_id)
    REFERENCES term (id)
    ON UPDATE CASCADE
	ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARACTER SET UTF8MB4;