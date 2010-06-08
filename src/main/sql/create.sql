CREATE DATABASE IF NOT EXISTS zenfoot;
USE zenfoot;

CREATE TABLE players (
  id            INTEGER         NOT NULL AUTO_INCREMENT PRIMARY KEY
 ,email         VARCHAR(255)	NOT NULL UNIQUE
 ,password      VARCHAR(255)	NOT NULL
 ,points        INTEGER         NOT NULL DEFAULT 0
 ,pending       BOOLEAN         NOT NULL DEFAULT FALSE
 ,admin         BOOLEAN         NOT NULL DEFAULT FALSE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=0;

CREATE TABLE teams (
  id            INTEGER         NOT NULL AUTO_INCREMENT PRIMARY KEY
 ,name          VARCHAR(255)    NOT NULL UNIQUE
 ,image_name    VARCHAR(255)    NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=0;


CREATE TABLE matches (
  id            INTEGER         NOT NULL AUTO_INCREMENT PRIMARY KEY
 ,team_1_id     INTEGER         NOT NULL
 ,team_2_id     INTEGER         NOT NULL
 ,kickoff	DATETIME    	NOT NULL
 ,goals_team_1  INTEGER         NOT NULL DEFAULT -1
 ,goals_team_2  INTEGER         NOT NULL DEFAULT -1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=0;

CREATE TABLE bets (
  id            INTEGER         NOT NULL AUTO_INCREMENT PRIMARY KEY
 ,player_id     INTEGER         NOT NULL
 ,match_id      INTEGER         NOT NULL
 ,goals_team_1  INTEGER         NOT NULL DEFAULT -1
 ,goals_team_2  INTEGER         NOT NULL DEFAULT -1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=0;

ALTER TABLE matches ADD FOREIGN KEY (team_1_id) REFERENCES teams(id)
	ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE matches ADD FOREIGN KEY (team_2_id) REFERENCES teams(id)
	ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE bets ADD FOREIGN KEY (player_id) REFERENCES players(id)
	ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE bets ADD FOREIGN KEY (match_id) REFERENCES matches(id)
	ON DELETE CASCADE ON UPDATE CASCADE;