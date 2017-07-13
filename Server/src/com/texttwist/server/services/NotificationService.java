package com.texttwist.server.services;

import interfaces.INotificationClient;
import interfaces.INotificationServer;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Author:      Lorenzo Iovino on 19/06/2017.
 * Description: Jedis Service
 */
public class NotificationService implements INotificationServer {

    private List<INotificationClient> clients;
    public NotificationService() throws RemoteException {
        super();
        clients = new ArrayList<>();
    }

    public synchronized void registerForCallback(INotificationClient clientInterface) throws RemoteException {
        if(!clients.contains(clientInterface)){
            clients.add(clientInterface);
            System.out.println(clientInterface);
            System.out.println("New client registered");
        }
    }

    public synchronized void unregisterForCallback(INotificationClient client) throws RemoteException {
        if (clients.remove(client)) {
            System.out.println("Client unregistered");
        } else {
            System.out.println("Unable to unregister client");
        }

    }

    public synchronized void sendInvitations(String username, DefaultListModel<String> users){
        Iterator i = clients.iterator();
        INotificationClient client = null;
        System.out.println("Starting callbacks");
        while (i.hasNext()) {
            client = (INotificationClient) i.next();
            try {

                System.out.println("SENDING INVITE TO "+users);
                client.sendInvite(username, users);
            } catch (RemoteException e) {
                    System.out.println("Sembra down");
                try {
                    unregisterForCallback(client);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }
}