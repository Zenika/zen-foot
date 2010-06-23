package com.zenika.zenfoot.pages;

import com.zenika.zenfoot.ZenFootSession;
import com.zenika.zenfoot.dao.MessageDao;
import com.zenika.zenfoot.model.Message;
import java.util.List;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.ajax.markup.html.AjaxEditableLabel;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

@AuthorizeInstantiation({Roles.USER, Roles.ADMIN})
public class ChatPage extends BasePage {

    private static final long serialVersionUID = 1L;
    @SpringBean
    private MessageDao messageDao;
    WebMarkupContainer messageListWrapper;
    FeedbackPanel feedbackPanel;
    String message;

    public ChatPage() {
        ZenFootSession.get().setLastMessage();
        add(new AjaxEditableLabel("newMessage", new PropertyModel(this, "message")) {

            @Override
            protected String defaultNullLabel() {
                return "Ajouter un message...";
            }

            @Override
            protected FormComponent newEditor(MarkupContainer parent, String componentId, IModel model) {
                FormComponent editor = super.newEditor(parent, componentId, model);
                editor.add(new SimpleAttributeModifier("class", "chatinput"));
                return editor;
            }

            @Override
            protected WebComponent newLabel(MarkupContainer parent, String componentId, IModel model) {
                WebComponent label = super.newLabel(parent, componentId, model);
                label.add(new SimpleAttributeModifier("class", "chatlabel"));
                return label;
            }

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                try {
                    messageDao.save(new Message(ZenFootSession.get().getUser(), message));
                    ZenFootSession.get().setLastMessage();
                } catch (Exception e) {
                    logger.error("Chat", e);
                }
                message = "";
                target.addComponent(messageListWrapper);
                super.onSubmit(target);
            }
        });
        messageListWrapper = new WebMarkupContainer("messageListWrapper");
        messageListWrapper.setOutputMarkupId(true);
        messageListWrapper.add(new MessageList("messageList"));
        add(messageListWrapper);
    }

    public class MessageList extends ListView<Message> {

        public MessageList(String id) {
            super(id, new MessageListModel());
            setOutputMarkupId(true);
        }

        @Override
        protected void populateItem(ListItem<Message> li) {
            Message message = li.getModelObject();
            li.setModel(new CompoundPropertyModel<Message>(message));
            li.add(new DateLabel("datetime", new PatternDateConverter("E d MMM H:mm", true)));
            li.add(new Label("player.alias"));
            li.add(new Label("message"));
        }
    }

    public class MessageListModel extends LoadableDetachableModel<List<? extends Message>> {

        @Override
        protected List<? extends Message> load() {
            return messageDao.findLast30();
        }
    }

    public MessageDao getMessageDao() {
        return messageDao;
    }

    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }
}
