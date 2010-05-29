package com.zenika.zenfoot.pages;

import com.zenika.zenfoot.ZenFootSession;
import com.zenika.zenfoot.service.DataService;
import com.zenika.zenfoot.service.MailService;
import com.zenika.zenfoot.service.mock.MockDataService;
import com.zenika.zenfoot.service.mock.MockMailService;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasePage extends WebPage {
    private static final long serialVersionUID = 1L;
    transient Logger logger = LoggerFactory.getLogger(BasePage.class);
    private transient DataService dataService = new MockDataService();
    private transient MailService mailService = new MockMailService();

    public BasePage() {
        System.out.println("session.isSignedIn:" + ZenFootSession.get().isSignedIn());
        add(new BookmarkablePageLink("homePage", HomePage.class));
        add(new BookmarkablePageLink("rulesPage", RulesPage.class));
        add(new BookmarkablePageLink("adminPage", AdminPage.class));

        add(new LoginForm("loginForm"));
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
            add(loginLink("loginLink"));
            add(registerLink("registerLink"));
            add(lostPasswordLink("lostPasswordLink"));
            add(loginFeedback("loginFeedback"));
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
            return passwordField;
        }

        private WebMarkupContainer loginLink(String id) {
            return new SubmitLink(id) {
                @Override
                public void onSubmit() {
                    if (ZenFootSession.get().signIn(email, password)) {
                        if (!continueToOriginalDestination()) {
                            setResponsePage(getApplication().getHomePage());
                        }
                    } else {
                        error("Email/Mot de passe incorrect !");
                    }
                }
            };
        }

        private WebMarkupContainer registerLink(String id) {
            return new SubmitLink(id) {
                @Override
                public void onSubmit() {
                    try {
                        dataService.registerUser(email, password);
                        info("Merci !");
                        info("Nous vous contacterons par mail très prochainement à l'adresse " + email);
                    } catch (Exception e) {
                        error("Impossible de vous enregistrer avec l'adresse " + email);
                    }
                }
            };
        }

        private WebMarkupContainer lostPasswordLink(String id) {
            return new SubmitLink(id) {
                @Override
                public void onSubmit() {
                    try {
                        mailService.sendPassword(email);
                        info("Votre mot de passe vous a été ré-envoyé à l'adresse " + email);
                    } catch (Exception e) {
                        error("Impossible d'envoyez votre mot de passe à l'adresse " + email);
                    }

                }
            };
        }
    }
}
