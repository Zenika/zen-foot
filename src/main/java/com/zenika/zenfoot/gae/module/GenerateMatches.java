package com.zenika.zenfoot.gae.module;

import com.zenika.zenfoot.gae.model.Match;
import org.joda.time.DateTime;

import java.util.ArrayList;

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

        Match[] matches = {
                new Match(new DateTime(2014, 6, 12, 22, 0), "A", croatie, bresil),
                new Match(new DateTime(2014, 6, 13, 18, 0), "A", cameroun, mexique),
                new Match(new DateTime(2014, 6, 13, 21, 0), "B", espagne, paysBas),
                new Match(new DateTime(2014, 6, 13, 0, 0), "B", chili, australie),
                new Match(new DateTime(2014, 6, 14, 18, 0), "C", colombie, grece),
                new Match(new DateTime(2014, 6, 14, 21, 0), "D", uruguay, costaRica),
                new Match(new DateTime(2014, 6, 14, 0, 0), "D", angleterre, italie),
                new Match(new DateTime(2014, 6, 14, 3, 0), "C", coteIvoir, japon),
                new Match(new DateTime(2014, 6, 15, 18, 0), "E", suisse, equateur),
                new Match(new DateTime(2014, 6, 15, 21, 0), "E", france, honduras),
                new Match(new DateTime(2014, 6, 15, 0, 0), "F", argentine, bosnie),
                new Match(new DateTime(2014, 6, 16, 18, 0), "G", allemagne, portugal),
                new Match(new DateTime(2014, 6, 16, 21, 0), "F", iran, nigeria),
                new Match(new DateTime(2014, 6, 16, 0, 0), "G", ghana, usa),
                new Match(new DateTime(2014, 6, 17, 18, 0), "H", belgique, algerie),
                new Match(new DateTime(2014, 6, 17, 21, 0), "A", bresil, mexique),
                new Match(new DateTime(2014, 6, 17, 0, 0), "H", russie, coree),
                new Match(new DateTime(2014, 6, 18, 18, 0), "B", australie, paysBas),
                new Match(new DateTime(2014, 6, 18, 21, 0), "B", espagne, chili),
                new Match(new DateTime(2014, 6, 18, 0, 0), "A", cameroun, croatie),
                new Match(new DateTime(2014, 6, 19, 18, 0), "C", colombie, coteIvoir),
                new Match(new DateTime(2014, 6, 19, 21, 0), "D", uruguay, angleterre),
                new Match(new DateTime(2014, 6, 19, 0, 0), "C", japon, grece),
                new Match(new DateTime(2014, 6, 20, 18, 0), "D", italie, costaRica),
                new Match(new DateTime(2014, 6, 20, 21, 0), "E", suisse, france),
                new Match(new DateTime(2014, 6, 20, 0, 0), "E", honduras, equateur),
                new Match(new DateTime(2014, 6, 21, 18, 0), "F", argentine, iran),
                new Match(new DateTime(2014, 6, 21, 21, 0), "G", allemagne, ghana),
                new Match(new DateTime(2014, 6, 21, 0, 0), "F", nigeria, bosnie),
                new Match(new DateTime(2014, 6, 22, 18, 0), "H", belgique, russie),
                new Match(new DateTime(2014, 6, 22, 21, 0), "H", coree, algerie),
                new Match(new DateTime(2014, 6, 22, 0, 0), "G", usa, portugal),
                new Match(new DateTime(2014, 6, 23, 18, 0), "B", paysBas, chili),
                new Match(new DateTime(2014, 6, 23, 18, 0), "B", australie, espagne),
                new Match(new DateTime(2014, 6, 23, 22, 0), "A", cameroun, bresil),
                new Match(new DateTime(2014, 6, 23, 22, 0), "A", croatie, mexique),
                new Match(new DateTime(2014, 6, 24, 18, 0), "D", italie, uruguay),
                new Match(new DateTime(2014, 6, 24, 18, 0), "D", costaRica, angleterre),
                new Match(new DateTime(2014, 6, 24, 22, 0), "C", japon, colombie),
                new Match(new DateTime(2014, 6, 24, 22, 0), "C", grece, coteIvoir),
                new Match(new DateTime(2014, 6, 25, 18, 0), "F", nigeria, argentine),
                new Match(new DateTime(2014, 6, 25, 18, 0), "F", bosnie, iran),
                new Match(new DateTime(2014, 6, 25, 22, 0), "E", honduras, suisse),
                new Match(new DateTime(2014, 6, 25, 22, 0), "E", equateur, france),
                new Match(new DateTime(2014, 6, 26, 18, 0), "G", portugal, ghana),
                new Match(new DateTime(2014, 6, 26, 18, 0), "G", usa, allemagne),
                new Match(new DateTime(2014, 6, 26, 22, 0), "H", coree, belgique),
                new Match(new DateTime(2014, 6, 26, 22, 0), "H", algerie, russie)
        };

        return matches;
    }
}
