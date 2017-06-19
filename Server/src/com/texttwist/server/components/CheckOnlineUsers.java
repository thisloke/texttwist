package com.texttwist.server.components;

import javax.swing.*;
import java.util.concurrent.Callable;

/**
 * Created by loke on 19/06/2017.
 */
public class CheckOnlineUsers implements Callable<Boolean> {
    private final DefaultListModel<String> users;

    public CheckOnlineUsers( DefaultListModel<String> users) {
        this.users = users;
    }

    @Override
    public Boolean call() throws Exception {
        System.out.print("Check If users are online!");
        System.out.println(users);
        for(int i = 0; i < 1; i++){
            Thread.sleep(2000);
        }
        return true;
    }
}
