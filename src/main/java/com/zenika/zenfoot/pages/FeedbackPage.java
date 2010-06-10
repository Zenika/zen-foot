package com.zenika.zenfoot.pages;

import com.zenika.zenfoot.ZenFootSession;
import com.zenika.zenfoot.service.account.AccountService;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

@AuthorizeInstantiation({Roles.USER, Roles.ADMIN})
public class FeedbackPage extends BasePage {

    private static final long serialVersionUID = 1L;
    @SpringBean
    private AccountService accountService;

    public FeedbackPage() {
        add(new FeedbackForm("feedbackForm"));
    }

    private class FeedbackForm extends StatelessForm {

        private String message;

        public FeedbackForm(String id) {
            super(id);
            add(new TextArea("feedbackMessage", new PropertyModel(FeedbackForm.this, "message")).setRequired(true));
            add(new FeedbackPanel("errors", new ContainerFeedbackMessageFilter(FeedbackForm.this)));
        }

        @Override
        protected void onSubmit() {
            try {
                accountService.feedback(ZenFootSession.get().getUser(), message);
                info("Merci !");
            } catch (Exception e) {
                logger.error("Feedback", e);
                error("Impossible d'envoyer votre message, vous pouvez nous contacter à l'adresse zenfoot@gmail.com");
            }
            message = "";
        }
    }
}
