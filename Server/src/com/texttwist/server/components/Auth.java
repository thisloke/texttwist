package com.texttwist.server.components;
import com.texttwist.client.App;
import interfaces.IAuth;
import interfaces.INotificationClient;
import models.Response;
import org.json.simple.JsonObject;
import utilities.Logger;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;

import static com.texttwist.server.Server.notificationServer;

/**
 * Created by loke on 15/06/2017.
 */
public class Auth extends UnicastRemoteObject implements IAuth {

    private SecureRandom random = new SecureRandom();
    public int serverPort = 9999;


    public Auth(int serverPort) throws RemoteException{
        this.serverPort=serverPort;
        Logger.write("Auth Service running at "+serverPort+" port...");
    }

    @Override
    public Response register(String userName, String password) throws RemoteException {
        Logger.write("Invoked register with username=" + userName + " AND " + " password=" + password);
        if ((userName != null && !userName.isEmpty()) && (password != null && !password.equals(""))) {
            if(AccountsManager.getInstance().register(userName, password)){
                Logger.write("Registration successfull");
                return new Response("Registration successfull", 200, null);
            } else {
                Logger.write("Registration unsuccessfull");
                return new Response("Registration unsuccessfull: Username exist!", 400, null);
            }
        }
        return new Response("Registration unsuccessfull! All fields are mandatories", 400, null);
    }

    @Override
    public Response login(String userName, String password) throws RemoteException {
        Logger.write("Invoked login with username=" + userName + " AND " + " password=" + password);
        if ((userName != null && !userName.isEmpty()) && (password != null && !password.equals(""))) {
            if(AccountsManager.getInstance().exists(userName) && AccountsManager.getInstance().checkPassword(userName, password)) {
                JsonObject data = new JsonObject();
                String token = nextSessionId();
                data.put("token", token);
                SessionsManager.getInstance().add(userName,token);
                Logger.write("Login successfull");
                return new Response("Login successfull", 200, data);
            }
        }
        Logger.write("Login unsuccessfull");
        return new Response("Login unsuccessfull", 400, null);
    }

    @Override
    public Response logout(String userName, String token, INotificationClient stub) throws RemoteException {
        Logger.write("Invoked logout with username=" + userName + " AND " + " token=" + token);
        notificationServer.unregisterForCallback(stub);

        if ((userName != null && !userName.isEmpty()) && (token != null && !token.isEmpty())) {
            boolean res = SessionsManager.getInstance().remove(userName);
            if(res) {
                Logger.write("Logout successfull");

            }
        }

        SessionsManager.getInstance().remove(userName);
        Logger.write("Logout successfull (but something gone wrong)");
        return new Response("Logout successfull (but something gone wrong)", 200, null);
    }

    public String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }

}
