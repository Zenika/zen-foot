package com.zenika.zenfoot.pages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.RangeValidator;

import com.zenika.zenfoot.dao.GameDao;
import com.zenika.zenfoot.dao.TeamDao;
import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.model.Match;
import com.zenika.zenfoot.model.Team;
import com.zenika.zenfoot.model.Player;
import com.zenika.zenfoot.pages.common.ConfirmLink;
import com.zenika.zenfoot.pages.common.Flag;
import com.zenika.zenfoot.service.account.AccountService;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;

@AuthorizeInstantiation(Roles.ADMIN)
public class AdminPage extends BasePage {

    private static final long serialVersionUID = 1L;
    @SpringBean
    private UserDao userDao;
    @SpringBean
    private TeamDao teamDao;
    @SpringBean
    private GameDao matchDao;
    @SpringBean
    private AccountService accountService;

    public AdminPage() {
        add(new UserList("userList"));
        add(new UserPendingList("userPendingList"));
        add(new TeamForm("newTeamForm").setVisible(false));
        add(new TeamList("teamList"));
        add(new MatchForm("newMatchForm"));
        add(new MatchList("matchList"));
    }

    public class MatchList extends ListView<Match> {

        public MatchList(String id) {
            super(id, new MatchListModel());
        }

        @Override
        protected void populateItem(ListItem<Match> li) {
            Match match = li.getModelObject();
            li.setModel(new CompoundPropertyModel<Match>(match));
            li.add(new Flag("team1.imageName", new Model(match.getTeam1().getImageName())));
            li.add(new Label("team1.name"));
            li.add(new Flag("team2.imageName", new Model(match.getTeam2().getImageName())));
            li.add(new Label("team2.name"));
            li.add(new Label("kickoff", new Model<String>(new SimpleDateFormat("d MMM H:mm z", Locale.FRANCE).format(match.getKickoff()))));
            li.add(new ConfirmLink<Match>("deleteLink", li.getModel()) {

                @Override
                public void onClick() {
                    matchDao.delete(getModelObject());
                }
            });
        }
    }

    public class MatchListModel extends LoadableDetachableModel<List<? extends Match>> {

        @Override
        protected List<? extends Match> load() {
            return matchDao.find();
        }
    }

    private class TeamForm extends Form {

        private String name;
        private String imageName;

        public TeamForm(String id) {
            super(id);
            add(new FeedbackPanel("teamFeedback", new ContainerFeedbackMessageFilter(TeamForm.this)));
            add(nameField("name"));
            add(imageNameField("imageName"));
        }

        private WebMarkupContainer nameField(String id) {
            final RequiredTextField<String> nameField = new RequiredTextField<String>(id, new PropertyModel<String>(TeamForm.this, "name"));
            return nameField;
        }

        private WebMarkupContainer imageNameField(String id) {
            final RequiredTextField<String> nameField = new RequiredTextField<String>(id, new PropertyModel<String>(TeamForm.this, "imageName"));
            return nameField;
        }

        @Override
        protected void onSubmit() {
            teamDao.save(new Team(name, imageName));
        }
    }

    private class MatchForm extends Form {

        private Team team1;
        private Team team2;
        private Date kickoff;
        private int kickoffHours;
        private int kickoffMinutes;

        public MatchForm(String id) {
            super(id);
            add(new FeedbackPanel("matchFeedback", new ContainerFeedbackMessageFilter(MatchForm.this)));
            add(team1Field("team1Field"));
            add(team2Field("team2Field"));
            add(kickoffField("kickoffField"));
            add(kickoffHours("kickoffHours"));
            add(kickoffMinutes("kickoffMinutes"));
        }

        @Override
        protected void onSubmit() {
            Calendar calendar = new GregorianCalendar(Locale.FRANCE);
            calendar.setTime(kickoff);
            calendar.set(Calendar.HOUR_OF_DAY, kickoffHours);
            calendar.set(Calendar.MINUTE, kickoffMinutes);

            matchDao.save(new Match(teamDao.findOrCreate(team1), teamDao.findOrCreate(team2), calendar.getTime()));
            setResponsePage(AdminPage.class);
        }

        private List<Team> buildAllCountriesTeam() {
            Set<Team> uniqueLocales = new HashSet<Team>();
            for (Locale locale : Locale.getAvailableLocales()) {
                if (!locale.getCountry().isEmpty()) {
                    uniqueLocales.add(new Team(locale.getDisplayCountry(), locale.getCountry().toLowerCase() + ".png"));
                }
            }
            List<Team> locales = new ArrayList<Team>(uniqueLocales);
            Collections.sort(locales);
            return locales;
        }

        private ChoiceRenderer buildTeamChoiceRenderer() {
            return new ChoiceRenderer("name", "imageName");
        }

        private DateField kickoffField(String id) {
            DateField dateField = new DateField(id, new PropertyModel<Date>(MatchForm.this, "kickoff"));
            dateField.setRequired(true);
            return dateField;
        }

        private RequiredTextField<Integer> kickoffHours(String id) {
            RequiredTextField<Integer> hours = new RequiredTextField<Integer>(id, new PropertyModel<Integer>(MatchForm.this, "kickoffHours"));
            hours.add(new RangeValidator<Integer>(0, 23));
            return hours;
        }

        private RequiredTextField<Integer> kickoffMinutes(String id) {
            RequiredTextField<Integer> minutes = new RequiredTextField<Integer>(id, new PropertyModel<Integer>(MatchForm.this, "kickoffMinutes"));
            minutes.add(new RangeValidator<Integer>(0, 59));
            return minutes;
        }

        private DropDownChoice team1Field(String id) {
            DropDownChoice<Team> team = new DropDownChoice<Team>(id, new PropertyModel<Team>(MatchForm.this, "team1"), buildAllCountriesTeam(), buildTeamChoiceRenderer());
            team.setRequired(true);
            return team;
        }

        private DropDownChoice team2Field(String id) {
            DropDownChoice<Team> team = new DropDownChoice<Team>(id, new PropertyModel<Team>(MatchForm.this, "team2"), buildAllCountriesTeam(), buildTeamChoiceRenderer());
            team.setRequired(true);
            return team;
        }
    }

    public class TeamList extends ListView<Team> {

        public TeamList(String id) {
            super(id, new TeamListModel());
        }

        @Override
        protected void populateItem(ListItem<Team> li) {
            Team team = li.getModelObject();
            li.setModel(new CompoundPropertyModel<Team>(team));
            li.add(new Flag("flag", new Model(team.getImageName())));
            li.add(new Label("name"));
            li.add(new ConfirmLink<Team>("deleteLink", li.getModel()) {

                @Override
                public void onClick() {
                    teamDao.delete(getModelObject());
                }
            }.setVisible(false));
        }
    }

    public class UserList extends ListView<Player> {

        public UserList(String id) {
            super(id, new UserListModel());
        }

        @Override
        protected void populateItem(ListItem<Player> li) {
            Player user = li.getModelObject();
            li.setModel(new CompoundPropertyModel<Player>(user));
            li.add(new Label("email"));
            li.add(new ConfirmLink<Player>("rejectLink", li.getModel()) {

                @Override
                public void onClick() {
                    accountService.reject(getModelObject());
                }
            });
            li.add(new ConfirmLink<Player>("adminLink", li.getModel()) {

                @Override
                public void onClick() {
                    getModelObject().setAdmin(!getModelObject().isAdmin());
                    userDao.save(getModelObject());
                }
            }.add(starImg("starImg", li.getModel())));
        }

        private WebMarkupContainer starImg(String id, IModel<Player> model) {
            WebMarkupContainer star = new WebMarkupContainer(id);
            String starImg = model.getObject().isAdmin() ? "star.png" : "bullet_star.png";
            star.add(new SimpleAttributeModifier("src", "images/" + starImg));
            return star;
        }
    }

    public class UserPendingList extends ListView<Player> {

        public UserPendingList(String id) {
            super(id, new UserPendingListModel());
        }

        @Override
        protected void populateItem(ListItem<Player> li) {
            Player user = li.getModelObject();
            li.setModel(new CompoundPropertyModel<Player>(user));
            li.add(new Label("email"));
            li.add(new ConfirmLink<Player>("acceptLink", li.getModel()) {

                @Override
                public void onClick() {
                    accountService.accept(getModelObject());
                }
            });
            li.add(new ConfirmLink<Player>("rejectLink", li.getModel()) {

                @Override
                public void onClick() {
                    accountService.reject(getModelObject());
                }
            });
        }
    }

    public class TeamListModel extends LoadableDetachableModel<List<? extends Team>> {

        @Override
        protected List<? extends Team> load() {
            return teamDao.find();
        }
    }

    public class UserListModel extends LoadableDetachableModel<List<? extends Player>> {

        @Override
        protected List<? extends Player> load() {
            return userDao.find();
        }
    }

    public class UserPendingListModel extends LoadableDetachableModel<List<? extends Player>> {

        @Override
        protected List<? extends Player> load() {
            return userDao.findPending();
        }
    }
}
