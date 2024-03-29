package com.texttwist.server.models;

import com.texttwist.server.services.JedisService;
import models.User;
import java.io.Serializable;
import java.util.*;


/**
 * Author:      Lorenzo Iovino on 18/06/2017.
 * Description: Accounts
 */
public class Accounts {

    public List<User> users = Collections.synchronizedList(new ArrayList<User>());

    private static class Holder {
        static final Accounts INSTANCE = new Accounts();
    }

    public static Accounts getInstance() {
        return Accounts.Holder.INSTANCE;
    }

    private Accounts(){
        List<Serializable> l = JedisService.get("users");
        for(int i=0; i<l.size(); i++) {
            users.add((User) l.get(i));
        }
    }

    public boolean register(String userName, String password) {
       if(!exists(userName)){
           User newUser = new User(userName, password,0);
           Boolean res = users.add(newUser);
           JedisService.add("users", newUser);
           return res;
        } else {
           return false;
       }
    }

    public boolean exists(String userName) {
        Iterator<User> i = users.iterator();
        while (i.hasNext()) {
            if (i.next().userName.equals(userName)) {
                return true;
            }
        }
        return false;

    }

    public boolean checkPassword(String userName, String password) {
        Iterator<User> i = users.iterator();
        while (i.hasNext()) {
            User account = i.next();
            if (account.userName.equals(userName) && account.password.equals(password)) {
                return true;
            }
        }
        return false;

    }

    public User findUser(String userName){
        Iterator<User> i = users.iterator();
        while (i.hasNext()) {
            User u = i.next();
            if (u.userName.equals(userName)) {
                return u;
            }
        }
        return null;
    }
}
