package com.example.gameofthree.player.impl;

import com.example.gameofthree.model.Message;
import com.example.gameofthree.player.Player;
import com.example.gameofthree.connection.MessageReceiver;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ReceiverPlayer extends Player implements Observer, Runnable {

    private MessageReceiver messageReceiver;

    public ReceiverPlayer(Socket socket, boolean autoReplyOn){
        super(0, autoReplyOn);
        messageReceiver = new MessageReceiver(socket);
    }

    public void update(Observable o, Object arg) {
        processMessage((Message) arg);
    }

    public void run() {
        while(true){
            Message messageFromClient = messageReceiver.getMessageFromClient();

            if (messageFromClient.isGameEnded())
                break;

            System.out.println(String.format("\nNew message received: %s", messageFromClient));
            setCurrentMessage(messageFromClient);

            System.out.println(String.format("Player2 chose number: %s and result = %s will be sending back. " , getCurrentNumber(), getCurrentMessage().getCalculatedNumber()));

            final Message outGoingMessage = new Message(getCurrentMessage().getCalculatedNumber(), getCurrentMessage().isGameEnded());

            messageReceiver.sendMessageBackToClient(outGoingMessage);

            if(getCurrentMessage().isGameEnded())
                break;
        }
        messageReceiver.finalizeConnections();
        System.out.println("\nGame is Ended");
    }
}
