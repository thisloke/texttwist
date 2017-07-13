package com.texttwist.server.tasks;

import com.texttwist.server.services.SessionsService;

import javax.swing.*;
import java.util.concurrent.Callable;

/**
 * Author:      Lorenzo Iovino on 19/06/2017.
 * Description: Jedis Service
 */
public class CheckOnlineUsers implements Callable<Boolean> {
    private final DefaultListModel<String> users;

    public CheckOnlineUsers( DefaultListModel<String> users) {
        this.users = users;
    }

    @Override
    public Boolean call() throws Exception {
        for(int i = 0; i < users.size(); i++){
            if(!(SessionsService.getInstance().exists(users.get(i)))){
                return false;
            }
        }
        return true;
    }
}
