package com.zenika.zenfoot.pages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
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
import com.zenika.zenfoot.dao.MatchDao;
import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.model.Bet;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.User;
import com.zenika.zenfoot.pages.common.Flag;
import com.zenika.zenfoot.pages.common.StaticImage;
import com.zenika.zenfoot.service.DataService;

public class HomePage extends BasePage {
    private static final long serialVersionUID = 1L;
//    private transient UserDao userDao = new MockUserDao();
//    private transient MatchDao matchDao = new MockMatchDao();
//    private transient BetDao betDao = new MockBetDao();
    
    @SpringBean
    private UserDao userDao;
    @SpringBean
    private MatchDao matchDao;
    @SpringBean
    private BetDao betDao;
    @SpringBean
    private DataService dataService;
    
    WebMarkupContainer userListWrapper;

    public HomePage(final PageParameters parameters) {
    	InjectorHolder.getInjector().inject(this);
        add(updatePts("updatePts"));
        userListWrapper = new WebMarkupContainer("userListWrapper");
        userListWrapper.setOutputMarkupId(true);
        userListWrapper.add(new UserList("userList"));
        add(userListWrapper);
        add(new IncomingMatchList("incomingMatchList"));
        add(new PastMatchList("pastMatchList"));
        add(new RunningMatchList("runningMatchList"));
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

    public class UserList extends ListView<User> {
        public UserList(String id) {
            super(id, new UserListModel());
            setOutputMarkupId(true);
        }

        @Override
        protected void populateItem(ListItem<User> li) {
            User user = li.getModelObject();
            li.setModel(new CompoundPropertyModel<User>(user));
            li.add(new StaticImage("medal", new Model("medal" + li.getIndex() + ".png")).setVisible(li.getIndex() < 3 && user.getPoints() > 0));
            li.add(new Label("points"));
            li.add(new Label("alias"));
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
            li.add(new Label("kickoff", new Model<String>(new SimpleDateFormat("d MMM H:mm z").format(match.getKickoff()))));
            li.add(new BetAjaxForm("betAjaxForm", li.getModelObject()).setVisible(ZenFootSession.get().isSignedIn()));
        }
    }

    public class PastMatchList extends ListView<Match> {
        public PastMatchList(String id) {
            super(id, new PastMatchListModel());
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
            li.add(new Label("kickoff", new Model<String>(new SimpleDateFormat("d MMM H:mm z").format(match.getKickoff()))));
        }
    }

    public class UserListModel extends LoadableDetachableModel<List<? extends User>> {
        @Override
        protected List<? extends User> load() {
            return userDao.find();
        }
    }

    public class IncomingMatchListModel extends LoadableDetachableModel<List<? extends Match>> {
        @Override
        protected List<? extends Match> load() {
            return matchDao.findIncoming();
        }
    }

    public class PastMatchListModel extends LoadableDetachableModel<List<? extends Match>> {
        @Override
        protected List<? extends Match> load() {
            return matchDao.findPast();
        }
    }

    public class RunningMatchListModel extends LoadableDetachableModel<List<? extends Match>> {
        @Override
        protected List<? extends Match> load() {
            return matchDao.findRunning();
        }
    }

    public class BetAjaxForm extends Form {
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
                this.goalsForTeam1 = parse(bet.getGoalsForTeam1());
                this.goalsForTeam2 = parse(bet.getGoalsForTeam2());
            }
            final TextField goal1 = new TextField("goalsForTeam1", new PropertyModel(BetAjaxForm.this, "goalsForTeam1"));
            goal1.setOutputMarkupId(true);
            goal1.add(new AjaxFormComponentUpdatingBehavior(ONKEYEVENT) {
                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    if (match.getKickoff().after(new Date())) {
                        Bet bet = betDao.createOrUpdate(ZenFootSession.get().getUser(), match, parse(goalsForTeam1), parse(goalsForTeam2));
                        goalsForTeam1 = parse(bet.getGoalsForTeam1());
                        goalsForTeam2 = parse(bet.getGoalsForTeam2());
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
                        Bet bet = betDao.createOrUpdate(ZenFootSession.get().getUser(), match, parse(goalsForTeam1), parse(goalsForTeam2));
                        goalsForTeam1 = parse(bet.getGoalsForTeam1());
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

    public class MatchAjaxForm extends Form {
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

        public MatchAjaxForm(String id, final Match match) {
            super(id);
            this.goalsForTeam1 = parse(match.getGoalsForTeam1());
            this.goalsForTeam2 = parse(match.getGoalsForTeam2());
            final TextField goal1 = new TextField("goalsForTeam1", new PropertyModel(MatchAjaxForm.this, "goalsForTeam1"));
            goal1.setOutputMarkupId(true);
            goal1.add(new AjaxFormComponentUpdatingBehavior(ONKEYEVENT) {
                @Override
                protected void onUpdate(AjaxRequestTarget target) {
                    match.setGoalsForTeam1(parse(goalsForTeam1));
                    match.setGoalsForTeam2(parse(goalsForTeam2));
                    matchDao.save(match);
                    dataService.updateUserPoints();
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
                    match.setGoalsForTeam1(parse(goalsForTeam1));
                    match.setGoalsForTeam2(parse(goalsForTeam2));
                    matchDao.save(match);
                    dataService.updateUserPoints();
                    target.addComponent(goal2);
                    target.addComponent(userListWrapper);
                    target.appendJavascript("new Effect.Highlight($('" + goal2.getMarkupId(true) + "'), { startcolor: '#ff0000',endcolor: '#ffffff' });");
                }
            });
            add(goal1);
            add(goal2);
        }
    }

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public MatchDao getMatchDao() {
		return matchDao;
	}

	public void setMatchDao(MatchDao matchDao) {
		this.matchDao = matchDao;
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
