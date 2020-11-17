package com.example.gameofthree.player;

import com.example.gameofthree.model.Message;
import com.example.gameofthree.util.GameRules;
import java.util.Observer;
import java.util.Scanner;

public abstract class Player implements Observer {

    private Message currentMessage;
    private int currentNumber;

    private boolean autoReplyOn;

    private Scanner scanner = new Scanner(System.in);

    public Player(final int initialNumber, boolean autoReplyOn){
        currentNumber = initialNumber;
        currentMessage = new Message(0, false);
        currentMessage.addObserver(this);

        this.autoReplyOn = autoReplyOn;
    }

    public void chooseReplyingNumber(){
        if(autoReplyOn) {
            currentNumber = GameRules.getRandomReplyingNumber();
            return;
        }

        getNumberFromUser();
    }

    private void getNumberFromUser() {
        int selectedNumber;
        do {
            System.out.println("Please select your move. Your options => " + GameRules.getResponseOptions());
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number!");
                scanner.next();
            }
            selectedNumber = scanner.nextInt();
        } while (!GameRules.isValidResponse(selectedNumber));

        currentNumber = selectedNumber;
    }

    public void setCurrentMessage(Message currentMessage) {
        this.currentMessage.setCurrentMessage(currentMessage.getCurrentMessage());
    }

    public Message getCurrentMessage() {
        return this.currentMessage;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    protected void processMessage(Message message){
        if (!isValidMessage(message)) {
            throw new RuntimeException(String.format("Invalid Message received: %s", message));
        }

        chooseReplyingNumber();
        getCurrentMessage().calculate(getCurrentNumber());

        if(GameRules.gameShouldEnd(getCurrentMessage().getCalculatedNumber())){
            this.currentMessage.setGameEnded(true);
            getCurrentMessage().setGameEnded(true);
            System.out.println("You won the game!");
        }
    }

    public boolean isValidMessage(Message messageFromClient) {
        if(messageFromClient.hasError()){
            System.err.println("Error has been occurred from peer, returning..");
            return false;
        }
        return true;
    }
}
