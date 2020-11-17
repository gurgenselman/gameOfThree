package com.example.gameofthree.service;

import com.example.gameofthree.model.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class MessageReceiverService {
    private Socket clientSocket;
    private ObjectOutputStream outToClient;
    private ObjectInputStream inFromClient;

    public MessageReceiverService(Socket socket){

        try{
            this.clientSocket = socket;
            System.out.println("Socket Extablished...");
            outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
            inFromClient = new ObjectInputStream(clientSocket.getInputStream());

        }catch (Exception e) {
            System.err.println("Server Error: " + e.getMessage());
            System.err.println("To String: " + e.toString());
        }

    }

    public Message getMessageFromClient() {
        try{
            return (Message)inFromClient.readObject();
        }catch (Exception e) {
            System.err.println("Server Error: " + e.getMessage());
            System.err.println("Localized: " + e.getLocalizedMessage());
            System.err.println("Stack Trace: " + Arrays.toString(e.getStackTrace()));
            System.err.println("To String: " + e.toString());
        }

        return new Message(true);
    }

    public void sendMessageBackToClient(Message message) {
        try{
            outToClient.writeObject(message);
        }catch (Exception e) {
            System.err.println("Server Error: " + e.getMessage());
            System.err.println("To String: " + e.toString());
        }
    }

    public void finalizeConnections(){
        try{
            clientSocket.close();

        }catch (Exception e) {
            System.err.println("Server Error: " + e.getMessage());
            System.err.println("To String: " + e.toString());
        }finally {
            try { clientSocket.close(); } catch (IOException e) { e.printStackTrace(); }
        }
    }
}
