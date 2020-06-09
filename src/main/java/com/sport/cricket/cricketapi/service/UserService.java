package com.sport.cricket.cricketapi.service;

import com.sport.cricket.cricketapi.domain.response.User;
import com.sport.cricket.cricketapi.persistance.QUser;
import com.sport.cricket.cricketapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    QUser qUser;


    public List<User> getUsers(){
        return userRepository.findAll().stream().map(user -> populateDomainUser(user)).collect(Collectors.toList());
    }

    public User addUser(final User user){
        userRepository.save(populateDBUser(user));
        return user;
    }

    private com.sport.cricket.cricketapi.persistance.User populateDBUser(User user) {
        com.sport.cricket.cricketapi.persistance.User userDb = new com.sport.cricket.cricketapi.persistance.User();
        userDb.setUserName(user.getUserName());
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());
        userDb.setEmail(user.getEmail());
        userDb.setPhone(user.getPhone());
        return userDb;
    }

    private User populateDomainUser(com.sport.cricket.cricketapi.persistance.User user) {
        User userDomain = new User();
        userDomain.setUserName(user.getUserName());
        userDomain.setFirstName(user.getFirstName());
        userDomain.setLastName(user.getLastName());
        userDomain.setEmail(user.getEmail());
        userDomain.setPhone(user.getPhone());
        return userDomain;
    }




    public User getUser(final String username){

        Optional<com.sport.cricket.cricketapi.persistance.User> userDbOpt = userRepository.findById(username);
        if(userDbOpt.isPresent()){
            return populateDomainUser(userDbOpt.get());
        }
        return new User(username);
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
