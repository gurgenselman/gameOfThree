package com.example.gameofthree.player.impl;

import com.example.gameofthree.model.Message;
import com.example.gameofthree.player.Player;
import com.example.gameofthree.service.MessageInitiatorService;
import java.util.Observable;

public class InitiatorPlayer extends Player implements Runnable {

    private MessageInitiatorService messageInitiatorService;

    public InitiatorPlayer(int initialNumber, String host, int port, boolean autoReplyOn){
        super(initialNumber, autoReplyOn);
        messageInitiatorService = new MessageInitiatorService(host, port);
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

            messageFromServer = messageInitiatorService.getMessageFromServer();

            if (messageFromServer.isGameEnded())
                break;

            System.out.println(String.format("\nNew message received: %s", messageFromServer));
            setCurrentMessage(messageFromServer);

            System.out.println("Player1 chose number: " + getCurrentNumber() + " result = " + getCurrentMessage()
                .getCalculatedNumber());

            messageInitiatorService
                .sendMessage(new Message(getCurrentMessage().getCalculatedNumber(), getCurrentMessage().isGameEnded()));

            if(getCurrentMessage().isGameEnded())
                break;
        };

        messageInitiatorService.closeConnections();
        System.out.println("\nGame is Ended");
    }

    private void checkConnectionAlive() {
        if (!messageInitiatorService.isConnected())
            messageInitiatorService.getReadyConnections();
    }

    private void getReady() {
        messageInitiatorService.getReadyConnections();
        System.out.println( "Player1: init message ->" + getCurrentNumber());
    }

    private void sendInitialMessage(final Message message) {
        messageInitiatorService.sendMessage(message);
    }
}
