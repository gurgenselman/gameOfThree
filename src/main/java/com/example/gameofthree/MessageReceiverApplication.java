package com.example.gameofthree;

import static com.example.gameofthree.util.GameRules.isAutoReplyOn;

import com.example.gameofthree.player.impl.ReceiverPlayer;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageReceiverApplication {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4444);

        final ExecutorService pool = Executors.newFixedThreadPool(20);
        while (true) {
            final ReceiverPlayer receiverPlayer = new ReceiverPlayer(serverSocket.accept(), false);
            pool.execute(receiverPlayer);
        }
    }
}
