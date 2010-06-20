package com.zenika.zenfoot.pages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.injection.web.InjectorHolder;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.zenika.zenfoot.ZenFootSession;
import com.zenika.zenfoot.dao.BetDao;
import com.zenika.zenfoot.dao.GameDao;
import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.Player;
import com.zenika.zenfoot.pages.common.Flag;
import com.zenika.zenfoot.pages.common.StaticImage;
import com.zenika.zenfoot.service.DataService;

public class HomePage extends BasePage {

    private static final long serialVersionUID = 1L;
    @SpringBean
    private UserDao userDao;
    @SpringBean
    private GameDao gameDao;
    @SpringBean
    private BetDao betDao;
    @SpringBean
    private DataService dataService;
    WebMarkupContainer userListWrapper;

    public HomePage(final PageParameters parameters) {
        InjectorHolder.getInjector().inject(this);
        ModalWindow modal = buildModalWindow("modal");
        BetMatchPanel betMatchPanel = buildBetMatchPanel(modal);
        add(updatePts("updatePts"));
        add(labelFooter("footer"));
        userListWrapper = new WebMarkupContainer("userListWrapper");
        userListWrapper.setOutputMarkupId(true);
        userListWrapper.add(new UserList("userList",modal, betMatchPanel));
        add(userListWrapper);
        add(new IncomingMatchList("incomingMatchList"));
        add(new PastMatchList("pastMatchList"));
        add(new RunningMatchList("runningMatchList"));
        add(modal);
    }
    
    private ModalWindow buildModalWindow(String id) {
    	final ModalWindow modal = new ModalWindow("modal");
    	modal.setCssClassName(ModalWindow.CSS_CLASS_GRAY);
    	modal.setMaskType(ModalWindow.MaskType.TRANSPARENT);
    	modal.setWidthUnit("px");
    	modal.setResizable(false);
    	modal.setUseInitialHeight(false);
    	modal.setCookieName("wicket-tips/styledModal");
        return modal;
    }
    
    private BetMatchPanel buildBetMatchPanel(ModalWindow modal){
        BetMatchPanel betMatchPanel = new BetMatchPanel(modal.getContentId());
		modal.setContent(betMatchPanel );
		return betMatchPanel;
    }



	private Label labelFooter(String id) {
    	Label footer = new Label(id,"* Paris que vous avez effectués") {

            @Override
            public boolean isVisible() {
                return ZenFootSession.get().getUser() != null && !userIsAdmin();
            }
        };
        return footer;
    }


    private Link updatePts(String id) {
        Link updatePts = new Link(id) {

            @Override
            public void onClick() {
                dataService.updateUserPoints();
            }

            @Override
            public boolean isVisible() {
                return userIsAdmin();
            }
        };
        return updatePts;
    }

    public class UserList extends ListView<Player> {
    	
    	private ModalWindow modal;
		private BetMatchPanel betMatchPanel;

        public UserList(String id, ModalWindow modal, BetMatchPanel betMatchPanel) {
            super(id, new UserListModel());
            this.modal = modal;
            this.betMatchPanel = betMatchPanel;
            setOutputMarkupId(true);
        }

        @Override
        protected void populateItem(ListItem<Player> li) {
            Player user = li.getModelObject();
            li.setModel(new CompoundPropertyModel<Player>(user));
            li.add(new StaticImage("medal", new Model("medal" + li.getIndex() + ".png")).setVisible(li.getIndex() < 3 && user.getPoints() > 0));
            li.add(new Label("points"));
            li.add(buildLink("link", user));
        }

        private AjaxLink<?> buildLink(String id, final Player user) {
        	AjaxLink<?> aliasLink = new AjaxLink<Long>(id) {
        		
        		
        		@Override
        		public boolean isEnabled() {
        			return ZenFootSession.get().getUser() != null;
        		}

				@Override
				public void onClick(AjaxRequestTarget target) {
					betMatchPanel.setUser(user);
					modal.setTitle("Pari de "+user.getAlias());
					modal.show(target);
				}
        		
        	};
        	aliasLink.add(new Label("alias"));
        	return aliasLink;
        }
        
    }

    public class IncomingMatchList extends ListView<Match> {

        public IncomingMatchList(String id) {
            super(id, new IncomingMatchListModel());
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
            li.add(new BetAjaxForm("betAjaxForm", li.getModelObject()).setVisible(ZenFootSession.get().isSignedIn()));
        }
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
            li.add(new Label("comments"));
            li.add(buildBetLabel("betResult", match));
        }
        
        private Label buildBetLabel(String wicketId, Match match){
            boolean isSignedIn = ZenFootSession.get().getUser() != null;
            String betResult = null;
            String color = null;
            Label label = new Label(wicketId);
            int betGoalForTeam2 = -1;
            if ( isSignedIn ){
            	Bet bet = betDao.find(userDao.find(ZenFootSession.get().getUser().getEmail()), match);
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
            li.add(new MatchAjaxForm("matchAjaxForm", li.getModelObject()).setVisible(userIsAdmin()));
            li.add(new Label("kickoff", new Model<String>(new SimpleDateFormat("d MMM H:mm").format(match.getKickoff()))));
            Player user = ZenFootSession.get().getUser();
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

    public class UserListModel extends LoadableDetachableModel<List<? extends Player>> {

        @Override
        protected List<? extends Player> load() {
            return userDao.findActive();
        }
    }

    public class IncomingMatchListModel extends LoadableDetachableModel<List<? extends Match>> {

        @Override
        protected List<? extends Match> load() {
            return gameDao.findIncoming();
        }
    }

    public class PastMatchListModel extends LoadableDetachableModel<List<? extends Match>> {

        @Override
        protected List<? extends Match> load() {
            return gameDao.findPast();
        }
    }

    public class RunningMatchListModel extends LoadableDetachableModel<List<? extends Match>> {

        @Override
        protected List<? extends Match> load() {
            return gameDao.findRunning();
        }
    }

    public final class BetAjaxForm extends Form {

        public static final String ONKEYEVENT = "onchange";
        private String goalsForTeam1;
        private String goalsForTeam2;

        String parse(int goalAsInt) {
            return goalAsInt < 0 ? null : String.valueOf(goalAsInt);
        }

        int parse(String goalAsString) {
            try {
                return Integer.parseInt(goalAsString);
            } catch (Exception e) {
                return -1;
            }
        }

        public BetAjaxForm(String id, final Match match) {
            super(id);
            Bet bet = betDao.find(ZenFootSession.get().getUser(), match);
            if (bet != null) {
                goalsForTeam1 = parse(bet.getGoalsForTeam1());
                goalsForTeam2 = parse(bet.getGoalsForTeam2());
            }
            final TextField goal1 = new TextField("goalsForTeam1", new PropertyModel(BetAjaxForm.this, "goalsForTeam1"));
            goal1.setOutputMarkupId(true);
            goal1.add(new AjaxFormComponentUpdatingBehavior(ONKEYEVENT) {

                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    if (match.getKickoff().after(new Date())) {
                        Bet bet = dataService.saveBet(ZenFootSession.get().getUser(), match, parse(goalsForTeam1), parse(goalsForTeam2));
                        goalsForTeam1 = parse(bet.getGoalsForTeam1());
                        target.addComponent(goal1);
                        target.appendJavascript("new Effect.Highlight($('" + goal1.getMarkupId(true) + "'), { startcolor: '#ff0000',endcolor: '#ffffff' });");
                    }
                }
            });
            final TextField goal2 = new TextField("goalsForTeam2", new PropertyModel(BetAjaxForm.this, "goalsForTeam2"));
            goal2.setOutputMarkupId(true);
            goal2.add(new AjaxFormComponentUpdatingBehavior(ONKEYEVENT) {

                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    if (match.getKickoff().after(new Date())) {
                        Bet bet = dataService.saveBet(ZenFootSession.get().getUser(), match, parse(goalsForTeam1), parse(goalsForTeam2));
                        goalsForTeam2 = parse(bet.getGoalsForTeam2());
                        target.addComponent(goal2);
                        target.appendJavascript("new Effect.Highlight($('" + goal2.getMarkupId(true) + "'), { startcolor: '#ff0000',endcolor: '#ffffff' });");
                    }
                }
            });
            add(goal1);
            add(goal2);
        }
    }

    public final class MatchAjaxForm extends Form {

        public static final String ONKEYEVENT = "onchange";
        private String goalsForTeam1;
        private String goalsForTeam2;
        private String comments = null;

        String parse(int goalAsInt) {
            return goalAsInt < 0 ? null : String.valueOf(goalAsInt);
        }

        int parse(String goalAsString) {
            try {
                return Integer.parseInt(goalAsString);
            } catch (Exception e) {
                return -1;
            }
        }

        public MatchAjaxForm(String id, final Match match) {
            super(id);
            goalsForTeam1 = parse(match.getGoalsForTeam1());
            goalsForTeam2 = parse(match.getGoalsForTeam2());
            final TextField goal1 = new TextField("goalsForTeam1", new PropertyModel(MatchAjaxForm.this, "goalsForTeam1"));
            goal1.setOutputMarkupId(true);
            goal1.add(new AjaxFormComponentUpdatingBehavior(ONKEYEVENT) {

                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    Match m = dataService.saveMatch(match, parse(goalsForTeam1), parse(goalsForTeam2), comments);
                    goalsForTeam1 = parse(m.getGoalsForTeam1());
                    target.addComponent(goal1);
                    target.addComponent(userListWrapper);
                    target.appendJavascript("new Effect.Highlight($('" + goal1.getMarkupId(true) + "'), { startcolor: '#ff0000',endcolor: '#ffffff' });");
                }
            });
            final TextField goal2 = new TextField("goalsForTeam2", new PropertyModel(MatchAjaxForm.this, "goalsForTeam2"));
            goal2.setOutputMarkupId(true);
            goal2.add(new AjaxFormComponentUpdatingBehavior(ONKEYEVENT) {

                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    Match m = dataService.saveMatch(match, parse(goalsForTeam1), parse(goalsForTeam2), comments);
                    goalsForTeam2 = parse(m.getGoalsForTeam2());
                    target.addComponent(goal2);
                    target.addComponent(userListWrapper);
                    target.appendJavascript("new Effect.Highlight($('" + goal2.getMarkupId(true) + "'), { startcolor: '#ff0000',endcolor: '#ffffff' });");
                }
            });
            final TextField commentsField = new TextField("comments", new PropertyModel(MatchAjaxForm.this, "comments"));
            commentsField.setOutputMarkupId(true);
            commentsField.add(new AjaxFormComponentUpdatingBehavior(ONKEYEVENT) {

                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    Match m = dataService.saveMatch(match, parse(goalsForTeam1), parse(goalsForTeam2), comments);
                    goalsForTeam2 = parse(m.getGoalsForTeam2());
                    target.addComponent(goal2);
                    target.addComponent(userListWrapper);
                    target.appendJavascript("new Effect.Highlight($('" + goal2.getMarkupId(true) + "'), { startcolor: '#ff0000',endcolor: '#ffffff' });");
                }
            });
            add(goal1);
            add(goal2);
            add(commentsField);
        }
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public GameDao getGameDao() {
        return gameDao;
    }

    public void setGameDao(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public BetDao getBetDao() {
        return betDao;
    }

    public void setBetDao(BetDao betDao) {
        this.betDao = betDao;
    }

    public DataService getDataService() {
        return dataService;
    }

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }
}
