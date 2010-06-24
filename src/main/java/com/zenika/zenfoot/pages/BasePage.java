package com.zenika.zenfoot.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zenika.zenfoot.ZenFootSession;
import com.zenika.zenfoot.dao.PlayerDao;
import com.zenika.zenfoot.model.Player;
import com.zenika.zenfoot.service.account.AccountService;
import java.util.Properties;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.util.time.Duration;

public class BasePage extends WebPage {

    private static final long serialVersionUID = 1L;
    transient Logger logger = LoggerFactory.getLogger(BasePage.class);
    @SpringBean
    private Properties applicationProperties;
    @SpringBean
    private AccountService accountService;
    @SpringBean
    private PlayerDao playerDao;

    public BasePage() {
        add(new BookmarkablePageLink("homePage", HomePage.class));
        add(new BookmarkablePageLink("rulesPage", RulesPage.class));
        add(new BookmarkablePageLink("feedbackPage", FeedbackPage.class).setVisible(ZenFootSession.get().isSignedIn()));
        final Component chatLink;
        add(chatLink = new BookmarkablePageLink("chatPage", ChatPage.class).setVisible(ZenFootSession.get().isSignedIn()));
        chatLink.setOutputMarkupPlaceholderTag(true);
        add(new BookmarkablePageLink("adminPage", AdminPage.class).setVisible(ZenFootSession.get().getRoles().hasRole(Roles.ADMIN)));

        if (ZenFootSession.get().isSignedIn()) {
            add(new AbstractAjaxTimerBehavior(Duration.seconds(10)) {

                @Override
                protected void onTimer(AjaxRequestTarget target) {
                    try {
                        if (ZenFootSession.get().hasNewMessages()) {
                            target.appendJavascript("new Effect.Pulsate($('" + chatLink.getMarkupId(true) + "'), { pulses: 8, duration: 8 });");
                            target.addComponent(chatLink);
                        }
                    } catch (Exception e) {
                        logger.error("BasePageChatTimer", e);
                    }
                }
            });
        }

        add(new LoginForm("loginForm"));
        add(loggedUser("loggedUser"));
        add(logout("logout"));
        add(new Label("headerTitle", applicationProperties.getProperty("application.title") + " | " + applicationProperties.getProperty("application.subtitle")));
        add(new Label("appName", applicationProperties.getProperty("application.title")));
        add(new Label("title", applicationProperties.getProperty("application.title")));
        add(new Label("subtitle", applicationProperties.getProperty("application.subtitle")));
        add(new Label("version", applicationProperties.getProperty("application.version")));
        add(new Label("buildNumber", applicationProperties.getProperty("pom.build.number")));
    }

    public static boolean userIsAdmin() {
        return ZenFootSession.get().isSignedIn() ? ZenFootSession.get().getRoles().hasRole(Roles.ADMIN) : false;
    }

    public static Player user() {
        return ZenFootSession.get().getUser();
    }

    private Label loggedUser(String id) {
        Label loggedUser = new Label(id, ZenFootSession.get().isSignedIn() ? ZenFootSession.get().getUser().getAlias() : "") {

            @Override
            public boolean isVisible() {
                return ZenFootSession.get().isSignedIn();
            }
        };
        return loggedUser;
    }

    private Link logout(String id) {
        Link logout = new Link(id) {

            @Override
            public void onClick() {
                ZenFootSession.get().signOut();
                ZenFootSession.get().invalidate();
                setResponsePage(getApplication().getHomePage());
            }
        };
        return logout;
    }

    public void setPlayerDao(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public PlayerDao getPlayerDao() {
        return playerDao;
    }

    private class LoginForm extends StatelessForm {

        private String email;
        private String password;

        @Override
        public boolean isVisible() {
            return !ZenFootSession.get().isSignedIn();
        }

        public LoginForm(String id) {
            super(id);
            setModel(new CompoundPropertyModel(this));
            add(emailField("email"));
            add(passwordField("password"));
            SubmitLink loginLink;
            add(loginLink = loginLink("loginLink"));
            add(registerLink("registerLink"));
            add(lostPasswordLink("lostPasswordLink"));
            add(loginFeedback("loginFeedback"));
            setDefaultButton(loginLink);
        }

        private WebMarkupContainer loginFeedback(String id) {
            return new FeedbackPanel(id, new ContainerFeedbackMessageFilter(LoginForm.this));
        }

        private WebMarkupContainer emailField(String id) {
            final RequiredTextField<String> emailField = new RequiredTextField<String>(id);
            emailField.add(EmailAddressValidator.getInstance());
            emailField.setPersistent(true);
            return emailField;
        }

        private WebMarkupContainer passwordField(String id) {
            final PasswordTextField passwordField = new PasswordTextField(id);
            passwordField.setResetPassword(false);
            passwordField.setRequired(false);
            return passwordField;
        }

        private SubmitLink loginLink(String id) {
            return new SubmitLink(id) {

                @Override
                public void onSubmit() {
                    if (password == null || password.isEmpty()) {
                        error("Le mot de passe est vide ?");
                    } else {
                        if (ZenFootSession.get().signIn(email, password)) {
                            if (!continueToOriginalDestination()) {
                                setResponsePage(getApplication().getHomePage());
                            }
                        } else {
                            error("Email/Mot de passe incorrect !");
                        }
                    }
                }
            };
        }

        private WebMarkupContainer registerLink(String id) {
            return new SubmitLink(id) {

                @Override
                public void onSubmit() {
                    if (password == null || password.isEmpty()) {
                        error("Le mot de passe est vide ?");
                    } else if (playerDao.find(email) != null) {
                        error("Ce compte existe déjà !");
                        warn("Avez vous perdu votre mot de passe ?");
                    } else {
                        try {
                            accountService.register(email, password);
                            info("Merci !");
                            info("Nous vous contacterons par mail très prochainement à l'adresse " + email);
                        } catch (Exception e) {
                            error("Impossible de vous enregistrer avec l'adresse " + email);
                        }
                    }
                }
            };
        }

        private WebMarkupContainer lostPasswordLink(String id) {
            return new SubmitLink(id) {

                @Override
                public void onSubmit() {
                    try {
                        Player player = playerDao.find(email);
                        if (player != null && player.isAdmin()) {
                            String msg = "Impossible de réinitialiser le mot de passe d'un admin !";
                            logger.error(msg);
                            throw new Exception(msg);
                        }
                        accountService.sendPassword(email);
                        info("Votre mot de passe vous a été ré-envoyé à l'adresse " + email);
                    } catch (Exception e) {
                        error("Impossible d'envoyez votre mot de passe à l'adresse " + email);
                    }

                }
            };
        }
    }
}
