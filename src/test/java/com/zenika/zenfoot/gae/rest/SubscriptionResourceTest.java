package com.zenika.zenfoot.gae.rest;

import com.googlecode.catchexception.apis.BDDCatchException;
import com.zenika.zenfoot.gae.AppInfoService;
import com.zenika.zenfoot.gae.dto.UserAndTeams;
import com.zenika.zenfoot.gae.exception.JsonWrappedErrorWebException;
import com.zenika.zenfoot.gae.model.User;
import com.zenika.zenfoot.gae.services.MailSenderService;
import com.zenika.zenfoot.gae.services.ZenfootUserService;
import org.assertj.core.api.BDDAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


/**
 * Created by armel on 01/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SubscriptionResourceTest {

    private SubscriptionResource subscriptionResource;

    @Mock
    private ZenfootUserService userService;
    @Mock
    private MailSenderService mailSenderService;
    @Mock
    private AppInfoService appInfoService;

    @Before
    public void setUp(){
        subscriptionResource = new SubscriptionResource(userService, mailSenderService, appInfoService);
    }


    @Test
    public void testSubscribe_user_already_exists(){
        UserAndTeams subscriber = new UserAndTeams();
        User user = newUser();
        subscriber.setUser(user);

        when(userService.getUserbyEmail(user.getEmail())).thenReturn(new User());

        //call & verify
        BDDCatchException.when(subscriptionResource).subscribe(subscriber);
        BDDAssertions.assertThat(BDDCatchException.caughtException())
                .isInstanceOf(JsonWrappedErrorWebException.class)
                .hasMessage("Bad Request");
    }

    @Test
    public void testSubscribe_ok(){
        UserAndTeams subscriber = new UserAndTeams();
        User user = newUser();
        subscriber.setUser(user);

        subscriptionResource.subscribe(subscriber);

        verify(userService).createOrUpdate(user);
    }


    @Test
    public void testConfirmation_wrong_email(){
        when(userService.getFromID("aa@zenika.com")).thenReturn(null);

        BDDCatchException.when(subscriptionResource).confirmSubscription("aa@zenika.com", "32");
        BDDAssertions.assertThat(BDDCatchException.caughtException())
                .hasMessage("Wrong email.");
    }

    @Test
    public void testConfirmation_wrong_token_NaN(){
        BDDCatchException.when(subscriptionResource).confirmSubscription("aa@zenika.com", "3aa2");
        BDDAssertions.assertThat(BDDCatchException.caughtException())
                .hasMessage("Wrong token.");
    }

    @Test
    public void testConfirmation_wrong_token(){
        User u = newUser();
        u.setActivationToken(456);
        u.setIsActive(false);
        when(userService.getFromID("aa@zenika.com")).thenReturn(u);

        BDDCatchException.when(subscriptionResource).confirmSubscription("aa@zenika.com", "32");
        BDDAssertions.assertThat(BDDCatchException.caughtException())
                .hasMessage("Wrong token.");
    }


    @Test
    public void testConfirmation_ok_user_already_active(){
        User u = newUser();
        u.setActivationToken(456);
        u.setIsActive(true);
        when(userService.getFromID("aa@zenika.com")).thenReturn(u);

        subscriptionResource.confirmSubscription("aa@zenika.com", "456");

        assertTrue(u.getIsActive());
    }


    @Test
    public void testConfirmation_ok(){
        User u = newUser();
        u.setActivationToken(456);
        u.setIsActive(false);
        when(userService.getFromID("aa@zenika.com")).thenReturn(u);

        subscriptionResource.confirmSubscription("aa@zenika.com", "456");

        assertTrue(u.getIsActive());
    }

    @Test
    public void testConfirmation_ok_2(){
        User u = newUser();
        u.setActivationToken(456);
        when(userService.getFromID("aa@zenika.com")).thenReturn(u);

        subscriptionResource.confirmSubscription("aa@zenika.com", "456");

        assertTrue(u.getIsActive());
    }



    /**
     * New initialized user
     * @return
     */
    public User newUser(){
        User user = new User();
        user.setEmail("toto@zenika.com");
        user.setLastname("aa");
        user.setPasswordHash("aaa");
        return user;
    }
}
