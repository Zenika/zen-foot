CREATE TABLE messages (
  id            INTEGER         NOT NULL AUTO_INCREMENT PRIMARY KEY
 ,player_id     INTEGER         NOT NULL
 ,message       VARCHAR(255)	NOT NULL
 ,datetime      DATETIME    	NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=0;

ALTER TABLE messages ADD FOREIGN KEY (player_id) REFERENCES players(id)
	ON DELETE CASCADE ON UPDATE CASCADE;
