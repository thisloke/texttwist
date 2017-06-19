package com.texttwist.server.components;

import interfaces.INotificationClient;
import interfaces.INotificationServer;

import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by loke on 19/06/2017.
 */
public class NotificationServer implements INotificationServer {

    private List<INotificationClient> clients;
    public NotificationServer() throws RemoteException {
        super();
        clients = new ArrayList<INotificationClient>();
    }

    public synchronized void registerForCallback(INotificationClient clientInterface) throws RemoteException {
        if(!clients.contains(clientInterface)){
            clients.add(clientInterface);
            System.out.print("New client registered");
        }
    }

    public synchronized void unregisterForCallback(INotificationClient client) throws RemoteException {
        if(clients.remove(client)) {
            System.out.print("Client unregistered");
        } else {
            System.out.print("Unable to unregister client");
        }
    }

    public void update(String username, DefaultListModel<String> users) throws RemoteException {
        doCallbacks(username, users);
    }

    private synchronized void doCallbacks(String username, DefaultListModel<String> users) throws RemoteException{
        System.out.print("Starting callbacks");
        Iterator i = clients.iterator();
        while(i.hasNext()){
            INotificationClient client = (INotificationClient) i.next();

            //Calcola i valori da inviare e inviali
            client.sendInvite(username, users);
        }
    }
}