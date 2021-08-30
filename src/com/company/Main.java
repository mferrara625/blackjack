package com.company;

import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        boolean gameIsActive = true, dealersTurn = false, calcResults = false, playerSplit = false;
        Scanner scan = new Scanner(System.in);
        String input = "";
        Player dealer = new Player();
        Player player = new Player();
//        deck.createDeck();
//        for (Card card : deck.fullDeck) {
//            System.out.println(card.value + " of " + card.suit);
//        }
//        Collections.shuffle(deck.fullDeck);
//        System.out.println("SHUFFLED DECK");
//        for (Card card : deck.fullDeck) {
//            System.out.println(card.value + " of " + card.suit);
//        }



        while (gameIsActive) {
            Deck deck = new Deck();

            deck.createDeck();
            Collections.shuffle(deck.fullDeck);
            Player activePlayer = player;
            Player split = new Player();

//            for (Card card : deck.fullDeck) {
//            System.out.println(card.value + " of " + card.suit);
//        }


            System.out.println("##################################");



            dealer.hand.add(deck.fullDeck.get(deck.fullDeck.size() - 1));
            deck.fullDeck.remove(deck.fullDeck.size() - 1);
            dealer.hand.add(deck.fullDeck.get(deck.fullDeck.size() - 1));
            deck.fullDeck.remove(deck.fullDeck.size() - 1);

            activePlayer.hand.add(deck.fullDeck.get(deck.fullDeck.size() - 1));
            deck.fullDeck.remove(deck.fullDeck.size() - 1);
            activePlayer.hand.add(deck.fullDeck.get(deck.fullDeck.size() - 1));
            deck.fullDeck.remove(deck.fullDeck.size() - 1);

            while (activePlayer.isActive) {
                System.out.println("Dealers Hand");

                System.out.println(dealer.hand.get(1).name + " of " + dealer.hand.get(1).suit);

                System.out.println("________________\n");
                System.out.println("Players Hand");

                displayPlayerHand(activePlayer);
                activePlayer.totalCardValue = 0;
                for (Card card : activePlayer.hand) {
                    if (card.value > 10) {
                        card.value = 10;
                    }
                    if(card.value == 1 && activePlayer.totalCardValue + 11 <= 21)
                        card.value = 11;
                    activePlayer.totalCardValue += card.value;
                }
                for(Card card : activePlayer.hand){
                    if (card.name.equals("Ace") && activePlayer.totalCardValue > 21){
                        card.value = 1;
                        activePlayer.totalCardValue -= 10;
                    }
                }
                if (activePlayer.totalCardValue == 21) {
                    System.out.println("BLACKJACK!");
                    if(!playerSplit){
                        activePlayer.isActive = false;
                        break;
                    }
                } else if (activePlayer.totalCardValue > 21) {

                    System.out.println("BUST!");
                    System.out.println("_______________");
                    System.out.println("Dealer wins!");
                    if(playerSplit){
                        activePlayer = split;
                        continue;
                    }
                    activePlayer.isActive = false;
                    break;
                } else {
                    System.out.println("\nWhat would you like to do?");
                    System.out.println("1. Hit");
                    System.out.println("2. Stand");
                    if (activePlayer.hand.get(0).value == activePlayer.hand.get(1).value && !playerSplit) {
                        System.out.println("3. Split");
                    }
                }
                input = scan.nextLine();
                if (input.equals("1")) {
                    activePlayer.hand.add(deck.fullDeck.get(deck.fullDeck.size() - 1));
                    deck.fullDeck.remove(deck.fullDeck.size() - 1);
                } else if (input.equals("2")) {
                    if(activePlayer == split){
                        playerSplit = false;
                    }
                    if(playerSplit){
                        activePlayer = split;

                    } else if(!playerSplit){
                        activePlayer.isActive = false;
                        dealersTurn = true;
                    }

                } else if (input.equals("3") && activePlayer.hand.get(0).value == activePlayer.hand.get(1).value) {
                    playerSplit = true;
                    split.hand.add(player.hand.get(1));
                    split.hand.add(deck.fullDeck.get(deck.fullDeck.size() - 1));
                    deck.fullDeck.remove(deck.fullDeck.size() - 1);
                    player.hand.remove(player.hand.get(1));
                    player.hand.add(deck.fullDeck.get(deck.fullDeck.size() - 1));
                    deck.fullDeck.remove(deck.fullDeck.size() - 1);

                }
            }
            while(dealersTurn){
                System.out.println("Dealers Turn");
                dealer.totalCardValue = 0;
                for (Card card : dealer.hand) {
                    if (card.value > 10) {
                        card.value = 10;
                    }
                    if(card.value == 1 && dealer.totalCardValue + 11 <= 21)
                        card.value = 11;
                    dealer.totalCardValue += card.value;
                }
                for(Card card : dealer.hand){
                    if (card.name.equals("Ace") && dealer.totalCardValue > 21){
                        card.value = 1;
                        dealer.totalCardValue -= 10;
                    }
                }
                if (dealer.totalCardValue < 17) {
                        dealer.hand.add(deck.fullDeck.get(deck.fullDeck.size() - 1));
                        deck.fullDeck.remove(deck.fullDeck.size() - 1);
                    }

                if(dealer.totalCardValue >= 17)
                    dealersTurn = false;
            }
            if(activePlayer == split)
                calculateResults(dealer, player);
            calculateResults(dealer, activePlayer);

            player.hand.clear();
            split.hand.clear();
            dealer.hand.clear();

            System.out.println("Would you like to continue? (Y/N)");
            String input2 = scan.nextLine();
            if(!input2.equalsIgnoreCase("Y")){
                gameIsActive = false;
            } else {
                activePlayer = player;
                activePlayer.isActive = true;
            }
        }
    }

    private static void calculateResults(Player dealer, Player activePlayer) {
        System.out.println("##################");
        displayPlayerHand(dealer);
        System.out.println("________________");
        displayPlayerHand(activePlayer);
        System.out.println("________________");

        if ((dealer.totalCardValue <= 21 && dealer.totalCardValue > activePlayer.totalCardValue) || activePlayer.totalCardValue > 21) {
            System.out.println("Dealer wins!");
        } else if(dealer.totalCardValue == activePlayer.totalCardValue) {
            System.out.println("Push!");
        } else {
            System.out.println("Player wins!");
        }
    }

    private static void displayPlayerHand(Player player) {
        for (Card card : player.hand) {
            System.out.println(card.name + " of " + card.suit);
        }
    }
}
