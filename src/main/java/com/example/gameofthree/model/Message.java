package com.example.gameofthree.model;

import java.io.Serializable;
import java.util.Observable;

public class Message extends Observable implements Serializable {

    private static final long serialVersionUID = 2658120334042475828L;

    private int currentMessage = -1;

    private int calculatedMessage = -1;

    private boolean error = false;

    private boolean isGameEnded = false;

    public Message(final int message, final boolean isGameEnded){
        this.currentMessage = message;
        this.error = false;
        this.isGameEnded = isGameEnded;
    }

    public Message(final boolean hasError){
        this.error = true;
    }

    public void calculate(final int selectedNumber){
        calculatedMessage = ( currentMessage + selectedNumber ) / 3;
    }

    public int getCalculatedNumber(){
        return this.calculatedMessage;
    }

    public void setCurrentMessage(int currentMessage) {
        this.currentMessage = currentMessage;

        setChanged();
        notifyObservers(this);
    }

    public int getCurrentMessage() {
        return currentMessage;
    }

    public void setGameEnded(boolean gameEnded) {
        this.isGameEnded = gameEnded;
    }

    public boolean isGameEnded() {
        return isGameEnded;
    }

    public boolean hasError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Message{" +
            "currentMessage=" + currentMessage +
            ", calculatedMessage=" + calculatedMessage +
            ", error=" + error +
            ", isGameEnded=" + isGameEnded +
            '}';
    }
}
