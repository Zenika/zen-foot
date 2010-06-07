--
-- Base de données: `zenfoot`
--

-- --------------------------------------------------------

--
-- Structure de la table `User`
--
CREATE TABLE `User` (
	`user_id` bigint(20) NOT NULL auto_increment,
	`email` varchar(150),
	`password` varchar(50),
	`points` int(11),
	`pending` boolean,
	`admin` boolean,
  	PRIMARY KEY  (`user_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=0 ;

CREATE TABLE `Team` (
	`team_id` bigint(20) NOT NULL auto_increment,
	`name` varchar(150),
	`imagename` varchar(50),
  	PRIMARY KEY  (`team_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=0 ;


CREATE TABLE `Game` (
	`game_id` bigint(20) NOT NULL auto_increment,
	`team1id` bigint(20),
	`team2id` bigint(20),
	`kickoff` datetime,
	`goalsforteam1` int(2),
	`goalsforteam2` int(2),
  	PRIMARY KEY  (`game_id`),
  	FOREIGN KEY  (`team1id`) REFERENCES Team(`team_id`),
  	FOREIGN KEY  (`team2id`) REFERENCES Team(`team_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=0 ;


CREATE TABLE `Bet` (
	`bet_id` bigint(200) NOT NULL auto_increment,
	`userid` bigint(20),
	`gameid` bigint(20),
	`goalsforteam1` int(2),
	`goalsforteam2` int(2),
  	PRIMARY KEY  (`bet_id`),
  	FOREIGN KEY  (`userid`) REFERENCES User(`user_id`),
  	FOREIGN KEY  (`gameid`) REFERENCES Game(`game_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=0 ;

