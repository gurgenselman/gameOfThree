package com.example.gameofthree.player.impl;

import com.example.gameofthree.model.Message;
import com.example.gameofthree.player.Player;
import com.example.gameofthree.connection.MessageSender;
import java.util.Observable;

public class InitiatorPlayer extends Player implements Runnable {

    private MessageSender messageSender;

    public InitiatorPlayer(int initialNumber, String host, int port, boolean autoReplyOn){
        super(initialNumber, autoReplyOn);
        messageSender = new MessageSender(host, port);
    }

    public void update(Observable o, Object arg) {
        processMessage((Message) arg);
    }

    public void run() {

        getReady();
        sendInitialMessage(new Message(getCurrentNumber(), getCurrentMessage().isGameEnded()));
        Message messageFromServer = null;

        while (true){
            checkConnectionAlive();

            messageFromServer = messageSender.getMessageFromServer();

            if (messageFromServer.isGameEnded())
                break;

            System.out.println(String.format("\nNew message received: %s", messageFromServer));
            setCurrentMessage(messageFromServer);

            System.out.println(String.format("Player1 chose number: %s and result = %s will be sending back. " , getCurrentNumber(), getCurrentMessage().getCalculatedNumber()));

            messageSender
                .sendMessage(new Message(getCurrentMessage().getCalculatedNumber(), getCurrentMessage().isGameEnded()));

            if(getCurrentMessage().isGameEnded())
                break;
        };

        messageSender.closeConnections();
        System.out.println("\nGame is Ended");
    }

    private void checkConnectionAlive() {
        if (!messageSender.isConnected())
            messageSender.getReadyConnections();
    }

    private void getReady() {
        messageSender.getReadyConnections();
        System.out.println( "Player1: init message -> " + getCurrentNumber());
    }

    private void sendInitialMessage(final Message message) {
        messageSender.sendMessage(message);
    }
}
