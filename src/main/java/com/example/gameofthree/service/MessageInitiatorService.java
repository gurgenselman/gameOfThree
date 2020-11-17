package com.example.gameofthree.service;

import com.example.gameofthree.model.Message;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class MessageInitiatorService {

    private ObjectOutputStream outToServer;
    private ObjectInputStream inFromServer;
    private Socket clientSocket;
    private String host;
    private int port;
    private boolean isConnected = false;

    public MessageInitiatorService(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void getReadyConnections(){
        try {
            clientSocket = new Socket(host, port);
            clientSocket.setKeepAlive(true);
            isConnected = true;
            this.outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            this.inFromServer = new ObjectInputStream(clientSocket.getInputStream());
        }catch (Exception e) {
            System.err.println("Client Error: " + e.getMessage());
            System.err.println("Stack Trace: " + Arrays.toString(e.getStackTrace()));
        }
    }

    public void sendMessage(Message message) {
        try {
            outToServer.writeObject(message);
        }catch (Exception e) {
            System.err.println("Client Error: " + e.getMessage());
            System.err.println("Stack Trace: " + Arrays.toString(e.getStackTrace()));
        }
    }

    public void closeConnections() {
        try {
            clientSocket.close();
            isConnected = false;
        }catch (Exception e) {
            System.err.println("Client Error: " + e.getMessage());
            System.err.println("Stack Trace: " + Arrays.toString(e.getStackTrace()));
        }finally {
            isConnected = false;
        }
    }

    public Message getMessageFromServer() {
        try {
            return  (Message)inFromServer.readObject();
        }catch (Exception e) {
            System.err.println("Client Error: " + e.getMessage());
            System.err.println("Stack Trace: " + Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
