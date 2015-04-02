package com.zenika.zenfoot.gae.module;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.zenika.zenfoot.gae.common.SportEnum;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.model.Sport;
import com.zenika.zenfoot.gae.utils.Constants;

/**
 * Created by raphael on 06/05/14.
 */
public class GenerateMatches {

    //src/main/webapp/

    public static Match[] generate() {
        String croatie = "croatie";
        String bresil = "bresil";
        String cameroun = "cameroun";
        String mexique = "mexique";
        String espagne = "espagne";
        String paysBas = "paysBas";
        String chili = "chili";
        String australie = "australie";
        String colombie = "colombie";
        String grece = "grece";
        String uruguay = "uruguay";
        String costaRica = "costaRica";
        String angleterre = "angleterre";
        String italie = "italie";
        String coteIvoir = "coteIvoir";
        String japon = "japon";
        String suisse = "suisse";
        String equateur = "equateur";
        String honduras = "honduras";
        String france = "france";
        String argentine = "argentine";
        String bosnie = "bosnie";
        String allemagne = "allemagne";
        String portugal = "portugal";
        String iran = "iran";
        String nigeria = "nigeria";
        String ghana = "ghana";
        String usa = "usa";
        String belgique = "belgique";
        String algerie = "algerie";
        String russie = "russie";
        String coree = "coree";

        DateTimeZone timezone = Constants.timeZone;

        Match[] matches = {
                new Match(new DateTime(2014, 6, 12, 22, 0, timezone), "A", croatie, bresil, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 13, 18, 0, timezone), "A", cameroun, mexique, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 13, 21, 0, timezone), "B", espagne, paysBas, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 13, 0, 0, timezone), "B", chili, australie, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 14, 18, 0, timezone), "C", colombie, grece, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 14, 21, 0, timezone), "D", uruguay, costaRica, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 14, 0, 0, timezone), "D", angleterre, italie, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 14, 3, 0, timezone), "C", coteIvoir, japon, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 15, 18, 0, timezone), "E", suisse, equateur, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 15, 21, 0, timezone), "E", france, honduras, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 15, 0, 0, timezone), "F", argentine, bosnie, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 16, 18, 0, timezone), "G", allemagne, portugal, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 16, 21, 0, timezone), "F", iran, nigeria, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 16, 0, 0, timezone), "G", ghana, usa, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 17, 18, 0, timezone), "H", belgique, algerie, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 17, 21, 0, timezone), "A", bresil, mexique, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 17, 0, 0, timezone), "H", russie, coree, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 18, 18, 0, timezone), "B", australie, paysBas, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 18, 21, 0, timezone), "B", espagne, chili, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 18, 0, 0, timezone), "A", cameroun, croatie, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 19, 18, 0, timezone), "C", colombie, coteIvoir, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 19, 21, 0, timezone), "D", uruguay, angleterre, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 19, 0, 0, timezone), "C", japon, grece, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 20, 18, 0, timezone), "D", italie, costaRica, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 20, 21, 0, timezone), "E", suisse, france, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 20, 0, 0, timezone), "E", honduras, equateur, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 21, 18, 0, timezone), "F", argentine, iran, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 21, 21, 0, timezone), "G", allemagne, ghana, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 21, 0, 0, timezone), "F", nigeria, bosnie, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 22, 18, 0, timezone), "H", belgique, russie, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 22, 21, 0, timezone), "H", coree, algerie, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 22, 0, 0, timezone), "G", usa, portugal, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(), SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 23, 18, 0, timezone), "B", paysBas, chili, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(), SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 23, 18, 0, timezone), "B", australie, espagne, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(), SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 23, 22, 0, timezone), "A", cameroun, bresil, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(), SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 23, 22, 0, timezone), "A", croatie, mexique, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(), SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 24, 18, 0, timezone), "D", italie, uruguay, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(), SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 24, 18, 0, timezone), "D", costaRica, angleterre, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(), SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 24, 22, 0, timezone), "C", japon, colombie, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(), SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 24, 22, 0, timezone), "C", grece, coteIvoir, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(), SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 25, 18, 0, timezone), "F", nigeria, argentine, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(), SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 25, 18, 0, timezone), "F", bosnie, iran, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(), SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 25, 22, 0, timezone), "E", honduras, suisse, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(), SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 25, 22, 0, timezone), "E", equateur, france, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 26, 18, 0, timezone), "G", portugal, ghana, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(), SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 26, 18, 0, timezone), "G", usa, allemagne, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(), SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 26, 22, 0, timezone), "H", coree, belgique, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(), SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2014, 6, 26, 22, 0, timezone), "H", algerie, russie, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(), SportEnum.FOOTBALL.getNameSport())),

                
                new Match(new DateTime(2014, 6, 26, 22, 0, timezone), null, france, usa, new Sport(SportEnum.TENNIS.getIdentifiantSport(), SportEnum.TENNIS.getNameSport())),
                
                new Match(new DateTime(2014, 6, 26, 22, 0, timezone), "A", france, espagne, new Sport(SportEnum.BASKET.getIdentifiantSport(), SportEnum.BASKET.getNameSport())),

                new Match(new DateTime(2014, 6, 26, 22, 0, timezone), "B", france, grece, new Sport(SportEnum.HANDBALL.getIdentifiantSport(), SportEnum.HANDBALL.getNameSport())),
                
        };

        return matches;
    }
}
