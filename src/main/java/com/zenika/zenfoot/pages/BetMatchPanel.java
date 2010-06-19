package com.zenika.zenfoot.pages;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.zenika.zenfoot.dao.BetDao;
import com.zenika.zenfoot.dao.GameDao;
import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.Player;
import com.zenika.zenfoot.pages.common.Flag;
import com.zenika.zenfoot.service.DataService;

public class BetMatchPanel extends Panel {
	
    @SpringBean
    private UserDao userDao;
    @SpringBean
    private GameDao gameDao;
    @SpringBean
    private BetDao betDao;
    @SpringBean
    private DataService dataService;
	private Player user;

	public BetMatchPanel(String id) {
		super(id);
		InjectorHolder.getInjector().inject(this);
        add(new PastMatchList("pastMatchList"));
        add(new RunningMatchList("runningMatchList"));

	}
	
    public class PastMatchList extends ListView<Match> {

        public PastMatchList(String id) {
            super(id, new PastMatchListModel());
        }
        
        String parse(int goalAsInt) {
            return goalAsInt < 0 ? null : String.valueOf(goalAsInt);
        }

        @Override
        protected void populateItem(ListItem<Match> li) {
            Match match = li.getModelObject();
            li.setModel(new CompoundPropertyModel<Match>(match));
            li.add(new Flag("team1.imageName", new Model(match.getTeam1().getImageName())));
            li.add(new Label("team1.name"));
            li.add(new Flag("team2.imageName", new Model(match.getTeam2().getImageName())));
            li.add(new Label("team2.name"));
            li.add(new Label("kickoff", new Model<String>(new SimpleDateFormat("d MMM").format(match.getKickoff()))));
            li.add(new Label("goalsForTeam1"));
            li.add(new Label("goalsForTeam2"));
          
            li.add(buildBetLabel("betResult", match));
        }
        
        private Label buildBetLabel(String wicketId, Match match){
            boolean isSignedIn = user != null;
            String betResult = null;
            String color = null;
            Label label = new Label(wicketId);
            int betGoalForTeam2 = -1;
            if ( isSignedIn ){
            	Bet bet = betDao.find(userDao.find(user.getEmail()), match);
            	if ( bet != null && bet.isBetSet() ){
	            	betResult = bet.getGoalsForTeam1() + " - " +  bet.getGoalsForTeam2(); 
	            	int points = dataService.computePoints(bet, match);
	            	if ( points == 3 ){
	             		color = "green";
	             	} else if ( points == 1 ){
	            		color = "orange";
	            	} else if ( points == 0 ){
	            		color = "red";
	            	} 
	            	label.setDefaultModel(new Model(betResult));
	            	label.add(new SimpleAttributeModifier("class", color));
            	}
            }  
            label.setVisible(isSignedIn && betResult != null);
            return label;
        }
    }

    public class RunningMatchList extends ListView<Match> {

        public RunningMatchList(String id) {
            super(id, new RunningMatchListModel());
        }

        @Override
        protected void populateItem(ListItem<Match> li) {
            Match match = li.getModelObject();
            li.setModel(new CompoundPropertyModel<Match>(match));
            li.add(new Flag("team1.imageName", new Model(match.getTeam1().getImageName())));
            li.add(new Label("team1.name"));
            li.add(new Flag("team2.imageName", new Model(match.getTeam2().getImageName())));
            li.add(new Label("team2.name"));
            li.add(new Label("kickoff", new Model<String>(new SimpleDateFormat("d MMM H:mm").format(match.getKickoff()))));
            int betGoalForTeam1 = -1;
            int betGoalForTeam2 = -1;
            if ( user != null ){
            	Bet bet = betDao.findOrCreate(userDao.find(user.getEmail()), match);
            	betGoalForTeam1 = bet.getGoalsForTeam1();
            	betGoalForTeam2 = bet.getGoalsForTeam2();
            }
            li.add(new Label("betGoalsForTeam1", new Model(betGoalForTeam1)).setVisible(user != null && betGoalForTeam1 != -1));
            li.add(new Label("betGoalsForTeam2", new Model(betGoalForTeam2)).setVisible(user != null && betGoalForTeam1 != -1));
        }
    }


    public class RunningMatchListModel extends LoadableDetachableModel<List<? extends Match>> {

        @Override
        protected List<? extends Match> load() {
            return gameDao.findRunning();
        }
    }

    public class PastMatchListModel extends LoadableDetachableModel<List<? extends Match>> {

        @Override
        protected List<? extends Match> load() {
            return gameDao.findPast();
        }
    }
    
    public void setUser(Player user){
    	this.user = user;
    }

}
