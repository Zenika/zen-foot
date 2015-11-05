package com.zenika.zenfoot.gae.services;

import com.google.common.base.Optional;
import com.zenika.zenfoot.gae.AbstractGenericService;
import com.zenika.zenfoot.gae.AbstractModelToDtoService;
import com.zenika.zenfoot.gae.Roles;
import com.zenika.zenfoot.gae.dao.UserDAO;
import com.zenika.zenfoot.gae.dto.UserDTO;
import com.zenika.zenfoot.gae.exception.JsonWrappedErrorWebException;
import com.zenika.zenfoot.gae.mapper.MapperFacadeFactory;
import com.zenika.zenfoot.gae.model.Ligue;
import com.zenika.zenfoot.gae.model.User;
import com.zenika.zenfoot.gae.utils.PasswordUtils;
import ma.glasnost.orika.MapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restx.security.UserRepository;
import restx.security.UserService;

import java.util.ArrayList;
import java.util.List;

public class ZenfootUserService extends AbstractModelToDtoService<User, String, UserDTO> implements UserService<User>, UserRepository<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZenfootUserService.class);

    final private MapperFacadeFactory mapper;
    public ZenfootUserService(UserDAO userDao, MapperFacadeFactory mapperFacadeFactory) {
        super(userDao, mapperFacadeFactory);
        this.mapper = mapperFacadeFactory;
    }
    
    @Override
    public Optional<User> findUserByName(String email) {
        if (email != null) {
            User user = this.getUserbyEmail(email);
            return Optional.fromNullable(user);
        } else {
            return Optional.absent();
        }
    }

    public User getUserbyEmail(String email) {
        return ((UserDAO) this.getDao()).getUser(email);
    }

    /**
     * Try to find a user based on email and check password. Also checks that the user is activated.
     * @param email email
     * @param passwordHash pwd
     * @return user found
     */
    @Override
    public Optional<User> findAndCheckCredentials(String email, String passwordHash) {
        Optional<User> optionalUser = this.findUserByName(email);

        if (!optionalUser.isPresent()) {
            return optionalUser;
        }

        String credentials = optionalUser.get().getPasswordHash();
        // user need to provide good pwd and to have validated its account
        if (credentials.equals(PasswordUtils.getPasswordHash(passwordHash)) && (optionalUser.get().getIsActive() == null || optionalUser.get().getIsActive())) {
            return optionalUser;
        } else {
            return Optional.absent();
        }
    }

    public void resetPWD(String userEmail,String oldPW, String newPW) {
        Optional<User> userOpt = this.findAndCheckCredentials(userEmail, oldPW);
        if(!userOpt.isPresent()){
            throw new JsonWrappedErrorWebException("WRONG_PWD","le mot de passe renseigné n'est pas le bon");
        }
        else{
            User user = userOpt.get();
            user.hashAndSetPassword(newPW);
            this.createOrUpdate(user);
        }
    }
    
    @Override
    public Optional<String> findCredentialByUserName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isAdminDefined() {
        return true;
    }

    @Override
    public User defaultAdmin() {
        return this.getUserbyEmail("admin@zenika.com");
    }

    public List<UserDTO> getAllAsDTO(String name) {
        List<User> all = ((UserDAO) this.getDao()).getAll(name);
        List<UserDTO> dtos = new ArrayList<>(all.size());
        mapperFacadeFactory.getMapper().mapAsCollection(all, dtos, UserDTO.class);
        return dtos;
    }

    /**
     * A utiliser une fois lors du déployement de la nouvelle version.
     * Migrate users to use new properties:
     *  - name --> lastname
     *  - prenom --> firstname
     */
    public void migrateNameProps() {
        List<User> all = this.getAll();
        for(User user : all){
            boolean updated = false;
            if(user.getPrenom() != null && !user.getPrenom().equals(user.getFirstname())){
                user.setFirstname(user.getPrenom());
                updated = true;
            }
            if(user.getName2() != null && !user.getName2().equals(user.getLastname())){
                user.setLastname(user.getName2());
                updated = true;
            }
            if(updated){
                this.createOrUpdate(user);
            }
        }
    }

    public void activate(String email) {
        User user = this.getFromID(email);
        user.setIsActive(true);
        this.createOrUpdate(user);
    }

    /**
     * Update user's information.
     * @param userDTO
     * @return
     */
    public Optional<UserDTO> update(final UserDTO userDTO) {
        return Optional.of(mapper.getMapper().map(this.createOrUpdateAndReturn(mapper.getMapper().map(userDTO, User.class)), UserDTO.class));
        // TODO update gambler associés
    }

    /**
     * Get one user given its identifier
     * @param id
     * @return
     */
    public Optional<UserDTO> getOne(final String id) {
        return Optional.of(mapper.getMapper().map(this.getFromID(id), UserDTO.class));
    }

}
