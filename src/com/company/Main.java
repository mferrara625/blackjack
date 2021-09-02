package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        boolean gameIsActive = true, dealersTurn = false, calcResults = false, playerIsActive = true, isAce11 = false;
        int splitCount = 0;
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
            List<Player> activePlayersHands = new ArrayList<>();
            Player activePlayer = player;
            Player split = new Player();
            Player split2 = new Player();
            Player split3 = new Player();
            activePlayersHands.add(player);

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

            while (playerIsActive) {
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
                    if(card.value == 1 && activePlayer.totalCardValue + 11 <= 21){
                        card.value = 11;
                        isAce11 = true;
                    }
                    activePlayer.totalCardValue += card.value;
                }
                for(Card card : activePlayer.hand){
                    if (card.name.equals("Ace") && activePlayer.totalCardValue > 21 && isAce11){
                        card.value = 1;
                        activePlayer.totalCardValue -= 10;
                        isAce11 = false;
                    }
                }
                if (activePlayer.totalCardValue == 21) {
                    System.out.println("BLACKJACK!");
                    if(splitCount == 0){
                        playerIsActive = false;
                        dealersTurn = true;
                    } else if(splitCount == 1){
                        if(activePlayer == player)
                            activePlayer = split;
                        else if (activePlayer == split){
                            splitCount--;
                            activePlayer = player;
                        }
                        continue;
                    } else if(splitCount == 2){
                        splitCount--;
                        activePlayer = split;
                        continue;
                    } else if(splitCount == 3){
                        splitCount--;
                        activePlayer = split2;
                        continue;
                    }
                } else if (activePlayer.totalCardValue > 21) {

                    System.out.println("BUST!");
                    System.out.println("_______________");
//                    System.out.println("Dealer wins!");
                    if(splitCount == 0){
                        playerIsActive = false;
                        dealersTurn = true;
                        break;
                    } else if(splitCount == 1){
                        if(activePlayer != player){
                            activePlayer = player;
                        } else{
                            activePlayer = split;
                        }
                        splitCount--;
                        continue;
                    } else if(splitCount == 2){
                        splitCount--;
                        activePlayer = split;
                        continue;
                    } else if(splitCount == 3){
                        splitCount--;
                        activePlayer = split2;
                        continue;
                    }
                } else {
                    System.out.println("\nWhat would you like to do?");
                    System.out.println("1. Hit");
                    System.out.println("2. Stand");
                    if (activePlayer.hand.get(0).value == activePlayer.hand.get(1).value && splitCount < 3) {
                        System.out.println("3. Split");
                    }
                }
                input = scan.nextLine();
                if (input.equals("1")) {
                    activePlayer.hand.add(deck.fullDeck.get(deck.fullDeck.size() - 1));
                    deck.fullDeck.remove(deck.fullDeck.size() - 1);
                } else if (input.equals("2")) {
                    if(splitCount == 0){
                        playerIsActive = false;
                        isAce11 = false;
                        dealersTurn = true;
                    } else if(splitCount == 1){
                            activePlayer = split;
                            splitCount--;
                    } else if(splitCount == 2){
                        activePlayer = split2;
                        splitCount--;
                    } else if(splitCount == 3){
                        activePlayer = split3;
                        splitCount--;
                    }
                } else if (input.equals("3") && activePlayer.hand.get(0).value == activePlayer.hand.get(1).value && splitCount < 3) {
                    splitCount++;

                    if(splitCount == 1) {
                        activePlayersHands.add(split);
                        split.hand.add(activePlayer.hand.get(1));
                        split.hand.add(deck.fullDeck.get(deck.fullDeck.size() - 1));
                        deck.fullDeck.remove(deck.fullDeck.size() - 1);
                        activePlayer.hand.remove(activePlayer.hand.get(1));
                        activePlayer.hand.add(deck.fullDeck.get(deck.fullDeck.size() - 1));
                        deck.fullDeck.remove(deck.fullDeck.size() - 1);
                    } else if(splitCount == 2) {
                        activePlayersHands.add(split2);
                        split2.hand.add(activePlayer.hand.get(1));
                        split2.hand.add(deck.fullDeck.get(deck.fullDeck.size() - 1));
                        deck.fullDeck.remove(deck.fullDeck.size() - 1);
                        activePlayer.hand.remove(activePlayer.hand.get(1));
                        activePlayer.hand.add(deck.fullDeck.get(deck.fullDeck.size() - 1));
                        deck.fullDeck.remove(deck.fullDeck.size() - 1);
                    } else if(splitCount == 3) {
                        activePlayersHands.add(split3);
                        split3.hand.add(activePlayer.hand.get(1));
                        split3.hand.add(deck.fullDeck.get(deck.fullDeck.size() - 1));
                        deck.fullDeck.remove(deck.fullDeck.size() - 1);
                        activePlayer.hand.remove(activePlayer.hand.get(1));
                        activePlayer.hand.add(deck.fullDeck.get(deck.fullDeck.size() - 1));
                        deck.fullDeck.remove(deck.fullDeck.size() - 1);
                    }
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
                    {
                        card.value = 11;
                        isAce11 = true;
                    }
                    dealer.totalCardValue += card.value;
                }
                for(Card card : dealer.hand){
                    if (card.name.equals("Ace") && dealer.totalCardValue > 21 && isAce11){
                        card.value = 1;
                        dealer.totalCardValue -= 10;
                        isAce11 = false;
                    }
                }
                if (dealer.totalCardValue < 17) {
                        dealer.hand.add(deck.fullDeck.get(deck.fullDeck.size() - 1));
                        deck.fullDeck.remove(deck.fullDeck.size() - 1);
                    }

                if(dealer.totalCardValue >= 17)
                    dealersTurn = false;
            }

            for(Player hands : activePlayersHands)
            calculateResults(dealer, hands);


            System.out.println("Would you like to continue? (Y/N)");
            String input2 = scan.nextLine();
            if(!input2.equalsIgnoreCase("Y")){
                gameIsActive = false;
            } else {
                for(Player hands : activePlayersHands)
                    hands.hand.clear();
                dealer.hand.clear();
                activePlayersHands.clear();
                isAce11 = false;
                playerIsActive = true;
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
