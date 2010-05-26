package com.zenika.zenfoot.pages;

import com.zenika.zenfoot.dao.TeamDao;
import com.zenika.zenfoot.dao.UserDao;
import com.zenika.zenfoot.dao.mock.MockTeamDao;
import com.zenika.zenfoot.dao.mock.MockUserDao;
import com.zenika.zenfoot.model.Team;
import com.zenika.zenfoot.model.User;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;

public class AdminPage extends BasePage {
    private static final long serialVersionUID = 1L;
    private transient UserDao userDao = new MockUserDao();
    private transient TeamDao teamDao = new MockTeamDao();

    public AdminPage() {
        add(new UserList("userList"));
        add(new UserPendingList("userPendingList"));
        add(new TeamList("teamList"));
    }

    public class TeamList extends ListView<Team> {
        public TeamList(String id) {
            super(id, new TeamListModel());
        }

        @Override
        protected void populateItem(ListItem<Team> li) {
            Team team = li.getModelObject();
            li.setModel(new CompoundPropertyModel<Team>(team));
            li.add(new Label("name"));
        }
    }

    public class UserList extends ListView<User> {
        public UserList(String id) {
            super(id, new UserListModel());
        }

        @Override
        protected void populateItem(ListItem<User> li) {
            User user = li.getModelObject();
            li.setModel(new CompoundPropertyModel<User>(user));
            li.add(new Label("email"));
            li.add(new Link<User>("rejectLink", li.getModel()) {
                @Override
                public void onClick() {
                    userDao.delete(getModelObject());
                }
            });
        }
    }

    public class UserPendingList extends ListView<User> {
        public UserPendingList(String id) {
            super(id, new UserPendingListModel());
        }

        @Override
        protected void populateItem(ListItem<User> li) {
            User user = li.getModelObject();
            li.setModel(new CompoundPropertyModel<User>(user));
            li.add(new Label("email"));
            li.add(new Link<User>("acceptLink", li.getModel()) {
                @Override
                public void onClick() {
                    userDao.accept(getModelObject());
                }
            });
            li.add(new Link<User>("rejectLink", li.getModel()) {
                @Override
                public void onClick() {
                    userDao.reject(getModelObject());
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

    public class UserListModel extends LoadableDetachableModel<List<? extends User>> {
        @Override
        protected List<? extends User> load() {
            return userDao.find();
        }
    }

    public class UserPendingListModel extends LoadableDetachableModel<List<? extends User>> {
        @Override
        protected List<? extends User> load() {
            return userDao.findPending();
        }
    }
}
