package com.example.gameofthree.player.impl;

import com.example.gameofthree.model.Message;
import com.example.gameofthree.player.Player;
import com.example.gameofthree.service.MessageReceiverService;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ReceiverPlayer extends Player implements Observer, Runnable {

    private MessageReceiverService messageReceiverService;

    public ReceiverPlayer(Socket socket, boolean autoReplyOn){
        super(0, autoReplyOn);
        messageReceiverService = new MessageReceiverService(socket);
    }

    public void update(Observable o, Object arg) {
        processMessage((Message) arg);
    }

    public void run() {
        while(true){
            Message messageFromClient = messageReceiverService.getMessageFromClient();

            if (messageFromClient.isGameEnded())
                break;

            System.out.println(String.format("\nNew message received: %s", messageFromClient));
            setCurrentMessage(messageFromClient);

            System.out.println("Player2 chose number: " + getCurrentNumber()  + " result = " + getCurrentMessage().getCalculatedNumber());

            final Message outGoingMessage = new Message(getCurrentMessage().getCalculatedNumber(), getCurrentMessage().isGameEnded());

            messageReceiverService.sendMessageBackToClient(outGoingMessage);

            if(getCurrentMessage().isGameEnded())
                break;
        }
        messageReceiverService.finalizeConnections();
        System.out.println("\nGame is Ended");
    }
}
