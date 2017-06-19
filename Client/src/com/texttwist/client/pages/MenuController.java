package com.texttwist.client.pages;

import com.texttwist.client.App;
import models.Response;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by loke on 17/06/2017.
 */
public class MenuController {

    public MenuController(){
    }

    public Response logout(String userName) throws RemoteException, NotBoundException, MalformedURLException {
        Response res =  App.authService.logout(userName);
        if (res.code == 200){
            App.sessionService.remove();
        }
        return res;
    }
}