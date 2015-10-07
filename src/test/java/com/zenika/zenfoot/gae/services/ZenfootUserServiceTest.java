package com.zenika.zenfoot.gae.services;

import com.zenika.zenfoot.gae.dao.UserDAO;
import com.zenika.zenfoot.gae.dto.UserDTO;
import com.zenika.zenfoot.gae.mapper.*;
import com.zenika.zenfoot.gae.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by armel on 02/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ZenfootUserServiceTest {


    private ZenfootUserService zenfootUserService;

    @Mock
    private UserDAO userDAO;

    private MapperFacadeFactory mapperFacadeFactory;

    @Before
    public void setUp(){
        mapperFacadeFactory = new MapperFacadeFactory(null, null, null, null);
        zenfootUserService = new ZenfootUserService(userDAO, mapperFacadeFactory);
    }

    @Test
    public void testGetAllDtos(){
        User user1 = new User();
        user1.setLastname("a");
        user1.setEmail("aaaa@zenika.com");
        User user2 = new User();
        user2.setFirstname("bb");
        user2.setPasswordHash("pwd");

        when(userDAO.getAll()).thenReturn(Arrays.asList(user1, user2));

        List<UserDTO> dtos = zenfootUserService.getAllAsDTO();
        assertEquals(2, dtos.size());
        UserDTO dto1 = dtos.get(0);
        assertEquals(dto1.getLastname(), user1.getLastname());
        assertEquals(dto1.getEmail(), user1.getEmail());
        UserDTO dto2 = dtos.get(1);
        assertEquals(dto2.getFirstname(), user2.getFirstname());
        assertEquals(dto2.getPasswordHash(), user2.getPasswordHash());

    }

    @Test
    public void testGetAllDtos_empty(){
        when(userDAO.getAll()).thenReturn(new ArrayList<User>());

        List<UserDTO> dtos = zenfootUserService.getAllAsDTO();
        assertTrue(dtos.isEmpty());

    }

    @Test
    public void testMigration_with_change(){
        User user1 = new User();
        user1.setName("aa");
        user1.setPrenom("toto");
        when(userDAO.getAll()).thenReturn(Arrays.asList(user1));

        zenfootUserService.migrateNameProps();

        assertEquals("toto", user1.getFirstname());
        assertEquals("aa", user1.getLastname());
    }

    @Test
    public void testMigration2_null(){
        User user1 = new User();
        when(userDAO.getAll()).thenReturn(Arrays.asList(user1));

        zenfootUserService.migrateNameProps();

        assertNull(user1.getFirstname());
        assertNull(user1.getLastname());
    }

    @Test
    public void testMigration_no_change(){
        User user1 = new User();
        user1.setFirstname("toto");
        user1.setLastname("aa");
        when(userDAO.getAll()).thenReturn(Arrays.asList(user1));

        zenfootUserService.migrateNameProps();

        assertEquals("toto", user1.getFirstname());
        assertEquals("aa", user1.getLastname());
    }

}
