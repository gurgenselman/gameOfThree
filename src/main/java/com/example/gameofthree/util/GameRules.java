package com.example.gameofthree.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class GameRules {

    private static int gameEndLimit = 1;

    private static Integer[] responseOptions = {1, 0, -1};
    private static final HashSet<Integer> responseOptionsAsSet = new HashSet<>(Arrays.asList(responseOptions));

    private static Integer[] replyModeOptions = {1, 2};
    private static final HashSet<Integer> replyModeOptionsAsSet = new HashSet<>(Arrays.asList(replyModeOptions));

    private static Random random = new Random();

    public static boolean gameShouldEnd(int message){
        return gameEndLimit >= message;
    }

    public static int getRandomReplyingNumber(){

        int randomIndex = random.nextInt(responseOptions.length);

        return responseOptions[randomIndex];
    }

    public static int getRandomStartingNumber(){
        return random.nextInt(100);
    }

    public static HashSet<Integer> getResponseOptions(){
        return responseOptionsAsSet;
    }

    public static boolean isValidResponse(int number){
        return responseOptionsAsSet.contains(number);
    }

    public static boolean isValidReplyMode(int number){
        return replyModeOptionsAsSet.contains(number);
    }

    public static void setGameEndLimit(int gameEndLimit) {
        GameRules.gameEndLimit = gameEndLimit;
    }

    public static boolean isAutoReplyOn() {
        int selectedNumber;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Please select reply mode. 1 for auto-reply 2 for manual-reply mode");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number!");
                scanner.next();
            }
            selectedNumber = scanner.nextInt();
        } while (!isValidReplyMode(selectedNumber));

        scanner = null;

        return selectedNumber == 1;
    }
}
