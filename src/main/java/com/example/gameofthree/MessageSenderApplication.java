package com.example.gameofthree;

import static com.example.gameofthree.util.GameRules.getRandomStartingNumber;
import static com.example.gameofthree.util.GameRules.isAutoReplyOn;

import com.example.gameofthree.player.impl.InitiatorPlayer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageSenderApplication {

    public static void main(String[] args){
        final ExecutorService pool = Executors.newFixedThreadPool(4);

        pool.execute(new InitiatorPlayer(getRandomStartingNumber(), "localhost", 4444, isAutoReplyOn()));

    }
}
