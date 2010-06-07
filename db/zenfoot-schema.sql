--
-- Base de données: `zenfoot`
--

-- --------------------------------------------------------

--
-- Structure de la table `User`
--
CREATE TABLE `User` (
	`id` bigint(20) NOT NULL auto_increment,
	`email` varchar(150),
	`password` varchar(50),
	`points` int(11),
	`pending` boolean,
	`admin` boolean,
  	PRIMARY KEY  (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=0 ;

CREATE TABLE `Team` (
	`id` bigint(20) NOT NULL auto_increment,
	`name` varchar(150),
	`imagename` varchar(50),
  	PRIMARY KEY  (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=0 ;


CREATE TABLE `Game` (
	`id` bigint(20) NOT NULL auto_increment,
	`team1` bigint(20),
	`team2` bigint(20),
	`kickoff` datetime,
	`goalsforteam1` int(2),
	`goalsforteam2` int(2),
  	PRIMARY KEY  (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=0 ;


CREATE TABLE `Bet` (
	`id` bigint(200) NOT NULL auto_increment,
	`user` bigint(20),
	`game` bigint(20),
	`goalsforteam1` int(2),
	`goalsforteam2` int(2),
  	PRIMARY KEY  (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=0 ;

