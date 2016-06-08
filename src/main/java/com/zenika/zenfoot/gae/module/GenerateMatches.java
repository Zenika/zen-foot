package com.zenika.zenfoot.gae.module;

import com.neovisionaries.i18n.CountryCode;
import com.zenika.zenfoot.gae.model.Country;
import com.zenika.zenfoot.gae.services.CountryService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.zenika.zenfoot.gae.common.SportEnum;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.model.Sport;
import com.zenika.zenfoot.gae.utils.Constants;

import javax.inject.Named;
import java.util.Locale;

/**
 * Created by raphael on 06/05/14.
 */
public class GenerateMatches {

    //src/main/webapp/
    public static Match[] generate(CountryService countryService) {
        DateTimeZone timezone = Constants.timeZone;
        Country wales = countryService.getByAlpha2Code("gb-wls");
        Country england = countryService.getByAlpha2Code("gb-eng");
        Country northernIreland = countryService.getByAlpha2Code("gb-nir");

        Match[] matches = {
                new Match(new DateTime(2016, 6, 10, 21, 0, timezone), "A", new Country(CountryCode.FR), new Country(CountryCode.RO), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 11, 15, 0, timezone), "A", new Country(CountryCode.AL), new Country(CountryCode.CH), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 15, 18, 0, timezone), "A", new Country(CountryCode.RO), new Country(CountryCode.CH), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 15, 21, 0, timezone), "A", new Country(CountryCode.FR), new Country(CountryCode.AL), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 19, 21, 0, timezone), "A", new Country(CountryCode.RO), new Country(CountryCode.AL), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 19, 21, 0, timezone), "A", new Country(CountryCode.CH), new Country(CountryCode.FR), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),

                new Match(new DateTime(2016, 6, 11, 18, 0, timezone), "B", wales, new Country(CountryCode.SK), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 11, 21, 0, timezone), "B", england, new Country(CountryCode.RU), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 15, 15, 0, timezone), "B", new Country(CountryCode.RU), new Country(CountryCode.SK), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 16, 15, 0, timezone), "B", england, wales, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 20, 21, 0, timezone), "B", new Country(CountryCode.RU), wales, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 20, 21, 0, timezone), "B", new Country(CountryCode.SK), england, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),

                new Match(new DateTime(2016, 6, 12, 18, 0, timezone), "C", new Country(CountryCode.PL), northernIreland, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 12, 21, 0, timezone), "C", new Country(CountryCode.DE), new Country(CountryCode.UA), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 16, 18, 0, timezone), "C", new Country(CountryCode.UA), northernIreland, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 16, 21, 0, timezone), "C", new Country(CountryCode.DE), new Country(CountryCode.PL), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 21, 18, 0, timezone), "C", new Country(CountryCode.UA), new Country(CountryCode.PL), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 21, 18, 0, timezone), "C", northernIreland, new Country(CountryCode.DE), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),

                new Match(new DateTime(2016, 6, 12, 15, 0, timezone), "D", new Country(CountryCode.TR), new Country(CountryCode.HR), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 13, 15, 0, timezone), "D", new Country(CountryCode.ES), new Country(CountryCode.CZ), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 17, 18, 0, timezone), "D", new Country(CountryCode.CZ), new Country(CountryCode.HR), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 17, 21, 0, timezone), "D", new Country(CountryCode.ES), new Country(CountryCode.TR), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 21, 21, 0, timezone), "D", new Country(CountryCode.CZ), new Country(CountryCode.TR), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 21, 21, 0, timezone), "D", new Country(CountryCode.HR), new Country(CountryCode.ES), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),

                new Match(new DateTime(2016, 6, 13, 18, 0, timezone), "E", new Country(CountryCode.IE), new Country(CountryCode.SE), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 13, 21, 0, timezone), "E", new Country(CountryCode.BE), new Country(CountryCode.IT), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 17, 15, 0, timezone), "E", new Country(CountryCode.IT), new Country(CountryCode.SE), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 18, 15, 0, timezone), "E", new Country(CountryCode.BE), new Country(CountryCode.IE), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 22, 21, 0, timezone), "E", new Country(CountryCode.IT), new Country(CountryCode.IE), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 22, 21, 0, timezone), "E", new Country(CountryCode.SE), new Country(CountryCode.BE), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),

                new Match(new DateTime(2016, 6, 14, 18, 0, timezone), "F", new Country(CountryCode.AT), new Country(CountryCode.HU), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 14, 21, 0, timezone), "F", new Country(CountryCode.PT), new Country(CountryCode.IS), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 18, 18, 0, timezone), "F", new Country(CountryCode.IS), new Country(CountryCode.HU), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 18, 21, 0, timezone), "F", new Country(CountryCode.PT), new Country(CountryCode.AT), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 22, 18, 0, timezone), "F", new Country(CountryCode.IS), new Country(CountryCode.AT), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
                new Match(new DateTime(2016, 6, 22, 18, 0, timezone), "F", new Country(CountryCode.HU), new Country(CountryCode.PT), new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),

               /* new Match(new DateTime(2014, 6, 13, 18, 0, timezone), "A", cameroun, mexique, new Sport(SportEnum.FOOTBALL.getIdentifiantSport(),SportEnum.FOOTBALL.getNameSport())),
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
            */
        };

        return matches;
    }
}
