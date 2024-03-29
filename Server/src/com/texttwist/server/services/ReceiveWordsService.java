package com.texttwist.server.services;

import com.texttwist.server.Server;
import com.texttwist.server.models.Sessions;
import com.texttwist.server.models.Match;
import com.texttwist.server.tasks.ComputeScore;
import constants.Config;
import models.Message;
import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author:      Lorenzo Iovino on 27/06/2017.
 * Description: Receive Words Service
 */
public class ReceiveWordsService implements Runnable {

    private ExecutorService threadPool = Executors.newCachedThreadPool();

    public ReceiveWordsService() {
        Server.logger.write("ReceiveWords Service running at "+Config.WordsReceiverServicePort +" port...");
    }

    @Override
    public void run(){
        Message msg;
        DatagramSocket s = null;

        try {
            s = new DatagramSocket(Config.WordsReceiverServicePort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        DatagramPacket packet;

        while(true) {
            byte[] buf = new byte[1024];

            packet = new DatagramPacket(buf, buf.length);
            try {
                s.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String rcv = new String(packet.getData());
            if (rcv.startsWith("MESSAGE")) {
                msg = Message.toMessage(rcv);
                if(Sessions.getInstance().isValidToken(msg.token)) {
                    Match match = Match.findMatchByPlayerName(msg.sender);
                    threadPool.submit(new ComputeScore(msg.sender, msg.data, match));
                }
            }
        }
    }
}
