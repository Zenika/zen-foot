package com.zenika.zenfoot.gae.services;

import com.googlecode.objectify.Ref;
import com.zenika.zenfoot.gae.dao.LigueDAO;
import com.zenika.zenfoot.gae.dto.GamblerDTO;
import com.zenika.zenfoot.gae.mapper.GamblerDtoToGamblerMapper;
import com.zenika.zenfoot.gae.mapper.MapperFacadeFactory;
import com.zenika.zenfoot.gae.model.Event;
import com.zenika.zenfoot.gae.model.Gambler;
import com.zenika.zenfoot.gae.model.Ligue;
import io.codearte.catchexception.shade.mockito.Mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by armel on 06/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class LigueServiceTest {


    private LigueService ligueService;

    private MapperFacadeFactory mapperFacadeFactory;

    @Mock
    private LigueDAO ligueDAO;

    @Before
    public void setUp(){
        mapperFacadeFactory = new MapperFacadeFactory(null, new GamblerDtoToGamblerMapper(), null, null);
        ligueService = new LigueService(ligueDAO, mapperFacadeFactory);
        ligueService = spy(ligueService);
    }

    @Test
    public void testGetLigueMembersAndOwner_just_owner(){
        Event e = new Event();
        Ligue l = new Ligue();
        Ref refG = mock(Ref.class);
        when(refG.get()).thenReturn(newGambler("aa", "bb"));
        l.setOwner(refG);
        doReturn(l).when(ligueService).getLigueWithMembersFromEvent(e, 1l);

        List<GamblerDTO> gamblers = ligueService.getLigueMembersAndOwner(e, 1l);
        assertEquals(1, gamblers.size());
        assertEquals("aa", gamblers.get(0).getLastName());
        assertEquals("bb", gamblers.get(0).getFirstName());
    }

    @Test
    public void testGetLigueMembersAndOwner(){
        Event e = new Event();
        Ligue l = new Ligue();
        Ref refG = mock(Ref.class);
        l.setOwner(refG);
        when(refG.get()).thenReturn(newGambler("aa", "bb"));

        List<Ref<Gambler>> acceptedGamblers = new ArrayList<>();
        Ref refG2 = mock(Ref.class);
        when(refG2.get()).thenReturn(newGambler("aa2", "bb2"));
        acceptedGamblers.add(refG2);
        Ref refG3 = mock(Ref.class);
        when(refG3.get()).thenReturn(newGambler("aa3", "bb3"));
        acceptedGamblers.add(refG3);
        l.setAccepted(acceptedGamblers);

        doReturn(l).when(ligueService).getLigueWithMembersFromEvent(e, 1l);

        List<GamblerDTO> gamblers = ligueService.getLigueMembersAndOwner(e, 1l);
        assertEquals(3, gamblers.size());
    }

    public Gambler newGambler(String lastName, String firstName){
        Gambler g = new Gambler();
        g.setLastName(lastName);
        g.setFirstName(firstName);
        return g;
    }
}
