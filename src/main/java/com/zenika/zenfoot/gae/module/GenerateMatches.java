package com.zenika.zenfoot.gae.module;

import com.zenika.zenfoot.gae.model.Groupe;
import com.zenika.zenfoot.gae.model.Match;
import com.zenika.zenfoot.gae.model.Participant;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by raphael on 06/05/14.
 */
public class GenerateMatches {

    public static ArrayList<Match> generate(){
        //12 juin
        Participant croatie = new Participant().setPays("Croatie").setGroupe(Groupe.A);
        Participant bresil = new Participant().setPays("Bresil").setGroupe(Groupe.A);
        DateTime dateTime=new DateTime(2014, 6, 12, 22, 0);
        Match match1 = new Match(dateTime, croatie, bresil);


        //13 juin
        Participant cameroun = new Participant().setPays("Cameroun").setGroupe(Groupe.A);
        Participant mexique = new Participant().setPays("Mexique").setGroupe(Groupe.A);
        Match match2 = new Match(new DateTime(2014, 6, 13, 18, 0)
                , cameroun, mexique);

        Participant espagne = new Participant().setPays("Espagne").setGroupe(Groupe.B);
        Participant paysBas = new Participant().setPays("Pays-Bas").setGroupe(Groupe.B);
        Match match3 = new Match(new DateTime(2014, 6, 13, 21, 0), espagne, paysBas);

        Participant chili = new Participant().setPays("Chili").setGroupe(Groupe.B);
        Participant australie = new Participant().setPays("Australie").setGroupe(Groupe.B);
        Match match4 = new Match(new DateTime(2014, 6, 13, 0, 0), chili, australie);

        //14 juin
        Participant colombie = new Participant().setPays("Colombie").setGroupe(Groupe.C);
        Participant grece = new Participant().setPays("Grece").setGroupe(Groupe.C);
        Match match5 = new Match(new DateTime(2014, 6, 14, 18, 0), colombie, grece);

        Participant uruguay = new Participant().setPays("Uruguay").setGroupe(Groupe.D);
        Participant costaRica = new Participant().setPays("Costa Rica").setGroupe(Groupe.D);
        Match match6 = new Match(new DateTime(2014, 6, 14, 21, 0), uruguay, costaRica);

        Participant angleterre = new Participant().setPays("Angleterre").setGroupe(Groupe.D);
        Participant italie = new Participant().setPays("Italie").setGroupe(Groupe.D);
        Match match7 = new Match(new DateTime(2014, 6, 14, 0, 0), angleterre, italie);

        Participant coteIvoir = new Participant().setPays("Côte d'Ivoire").setGroupe(Groupe.C);
        Participant japon = new Participant().setPays("Japon").setGroupe(Groupe.C);
        Match match8 = new Match(new DateTime(2014, 6, 14, 3, 0), coteIvoir, japon);

        //15 juin
        Participant suisse = new Participant().setPays("Suisse").setGroupe(Groupe.E);
        Participant equateur = new Participant().setPays("Equateur").setGroupe(Groupe.E);
        Match match9 = new Match(new DateTime(2014, 6, 15, 18, 0), suisse, equateur);

        Participant honduras = new Participant().setPays("Honduras").setGroupe(Groupe.E);
        Participant france = new Participant().setPays("France").setGroupe(Groupe.E);
        Match match10 = new Match(new DateTime(2014, 6, 15, 21, 0), france, honduras);

        Participant argentine = new Participant().setPays("Argentine").setGroupe(Groupe.F);
        Participant bosnie = new Participant().setPays("Bosnie-et-Herzégovine").setGroupe(Groupe.F);
        Match match11 = new Match(new DateTime(2014, 6, 15, 0, 0), argentine, bosnie);

        //16 juin
        Participant allemagne = new Participant().setPays("Allemagne").setGroupe(Groupe.G);
        Participant portugal = new Participant().setPays("Portugal").setGroupe(Groupe.G);
        Match match12 = new Match(new DateTime(2014, 6, 16, 18, 0), allemagne, portugal);

        Participant iran = new Participant().setPays("Iran").setGroupe(Groupe.F);
        Participant nigeria = new Participant().setPays("Nigéria").setGroupe(Groupe.F);
        Match match13 = new Match(new DateTime(2014, 6, 16, 21, 0), iran, nigeria);

        Participant ghana = new Participant().setPays("Ghana").setGroupe(Groupe.G);;
        Participant usa = new Participant().setPays("USA").setGroupe(Groupe.G);;
        Match match14 = new Match(new DateTime(2014, 6, 16, 0, 0), ghana, usa);


        //17 juin
        Participant belgique = new Participant().setPays("Belgique").setGroupe(Groupe.H);
        Participant algerie = new Participant().setPays("Algérie").setGroupe(Groupe.H);
        Match match15 = new Match(new DateTime(2014, 6, 17, 18, 0), belgique, algerie);

        Match match16 = new Match(new DateTime(2014, 6, 17, 21, 0), bresil, mexique);

        Participant russie = new Participant().setPays("Russie").setGroupe(Groupe.H);
        Participant coree = new Participant().setPays("République de Corée").setGroupe(Groupe.H);
        Match match17 = new Match(new DateTime(2014, 6, 17, 0, 0), russie, coree);

        //18 juin
        Match match18 = new Match(new DateTime(2014, 6, 18, 18, 0), australie, paysBas);
        Match match19 = new Match(new DateTime(2014, 6, 18, 21, 0), espagne, chili);
        Match match20 = new Match(new DateTime(2014, 6, 18, 0, 0), cameroun, croatie);

        //19 juin
        Match match21 = new Match(new DateTime(2014, 6, 19, 18, 0), colombie, coteIvoir);
        Match match22 = new Match(new DateTime(2014, 6, 19, 21, 0), uruguay, angleterre);
        Match match23 = new Match(new DateTime(2014, 6, 19, 0, 0), japon, grece);


        //20 juin
        Match match24 = new Match(new DateTime(2014,6,20,18,0),italie,costaRica);
        Match match25 = new Match(new DateTime(2014,6,20,21,0),suisse,france);
        Match match26=new Match(new DateTime(2014,6,20,0,0),honduras,equateur);

        //21 juin
        Match match27=new Match(new DateTime(2014,6,21,18,0),argentine,iran);
        Match match28=new Match(new DateTime(2014,6,21,21,0),allemagne,ghana);
        Match match29=new Match(new DateTime(2014,6,21,0,0),nigeria,bosnie);

        //22 juin
        Match match30=new Match(new DateTime(2014,6,22,18,0),belgique,russie);
        Match match31=new Match(new DateTime(2014,6,22,21,0),coree,algerie);
        Match match32=new Match(new DateTime(2014,6,22,0,0),usa,portugal);

        //23 juin
        Match match33=new Match(new DateTime(2014,6,23,18,0),paysBas,chili);
        Match match34=new Match(new DateTime(2014,6,23,18,0),australie,espagne);
        Match match35=new Match(new DateTime(2014,6,23,22,0),cameroun,bresil);
        Match match36=new Match(new DateTime(2014,6,23,22,0),croatie,mexique);

        //24 juin
        Match match37=new Match(new DateTime(2014,6,24,18,0),uruguay,italie);
        Match match38=new Match(new DateTime(2014,6,24,18,0),costaRica,angleterre);
        Match match39=new Match(new DateTime(2014,6,24,22,0),japon,colombie);
        Match match40=new Match(new DateTime(2014,6,24,22,0),grece,coteIvoir);


        //25 juin
        Match match41=new Match(new DateTime(2014,6,25,18,0),nigeria,argentine);
        Match match42=new Match(new DateTime(2014,6,25,18,0),bosnie,iran);
        Match match43=new Match(new DateTime(2014,6,25,22,0),honduras,suisse);
        Match match44=new Match(new DateTime(2014,6,25,22,0),equateur,france);


        //26 juin
        Match match45=new Match(new DateTime(2014,6,26,18,0),portugal,ghana);
        Match match46=new Match(new DateTime(2014,6,26,18,0),usa,allemagne);
        Match match47=new Match(new DateTime(2014,6,26,22,0),coree,belgique);
        Match match48=new Match(new DateTime(2014,6,26,22,0),algerie,russie);





        ArrayList<Match> matchsArray = new ArrayList<>();


        matchsArray.add(match1);
        matchsArray.add(match2);
        matchsArray.add(match3);
        matchsArray.add(match4);
        matchsArray.add(match5);
        matchsArray.add(match6);
        matchsArray.add(match7);
        matchsArray.add(match8);
        matchsArray.add(match9);
        matchsArray.add(match10);
        matchsArray.add(match11);
        matchsArray.add(match12);
        matchsArray.add(match13);
        matchsArray.add(match14);
        matchsArray.add(match15);
        matchsArray.add(match16);
        matchsArray.add(match17);
        matchsArray.add(match18);
        matchsArray.add(match19);
        matchsArray.add(match20);
        matchsArray.add(match21);
        matchsArray.add(match22);
        matchsArray.add(match23);
        matchsArray.add(match24);
        matchsArray.add(match25);
        matchsArray.add(match26);
        matchsArray.add(match27);
        matchsArray.add(match28);
        matchsArray.add(match29);
        matchsArray.add(match30);
        matchsArray.add(match31);
        matchsArray.add(match32);
        matchsArray.add(match33);
        matchsArray.add(match34);
        matchsArray.add(match35);
        matchsArray.add(match36);
        matchsArray.add(match37);
        matchsArray.add(match38);
        matchsArray.add(match39);
        matchsArray.add(match40);
        matchsArray.add(match41);
        matchsArray.add(match42);
        matchsArray.add(match43);
        matchsArray.add(match44);
        matchsArray.add(match45);
        matchsArray.add(match46);
        matchsArray.add(match47);
        matchsArray.add(match48);

        return matchsArray;
    }
}
