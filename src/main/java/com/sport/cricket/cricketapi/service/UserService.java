package com.sport.cricket.cricketapi.service;

import com.sport.cricket.cricketapi.domain.persistance.QUserAggregate;
import com.sport.cricket.cricketapi.domain.persistance.UserAggregate;
import com.sport.cricket.cricketapi.domain.response.User;
import com.sport.cricket.cricketapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    QUserAggregate qUserAggregate;


    public List<User> getUsers(){
        return userRepository.findAll().stream().map(user -> populateDomainUser(user)).collect(Collectors.toList());
    }

    public User addUser(final User user){
        userRepository.save(populateDBUser(user));
        return user;
    }

    private UserAggregate populateDBUser(User user) {
        UserAggregate userDb = new UserAggregate();
        userDb.setUserName(user.getUserName());
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());
        userDb.setEmail(user.getEmail());
        userDb.setPassword(user.getPassword());
        return userDb;
    }

    private User populateDomainUser(UserAggregate user) {
        User userDomain = new User();
        userDomain.setUserName(user.getUserName());
        userDomain.setFirstName(user.getFirstName());
        userDomain.setLastName(user.getLastName());
        userDomain.setEmail(user.getEmail());
        userDomain.setPassword(user.getPassword());
        return userDomain;
    }




    public User getUser(final String username){

        Optional<UserAggregate> userDbOpt = userRepository.findById(username);
        if(userDbOpt.isPresent()){
            return populateDomainUser(userDbOpt.get());
        }
        return new User(username);
    }

    public boolean authenticateUser(final String username, final String password){

        Optional<UserAggregate> userDbOpt = userRepository.findOne(qUserAggregate.userName.eq(username).and(qUserAggregate.password.eq(password)));
        return  userDbOpt.isPresent();
    }

    public boolean deleteUser(final String username) {

        if (StringUtils.isEmpty(username)){
            return false;
        }else {
            userRepository.deleteById(username);
            return true;
        }
    }

    public void updateUser(final String username, final User user){
        deleteUser(username);
        userRepository.save(populateDBUser(user));
    }
}
