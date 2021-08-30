package com.company;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    List<Card> fullDeck = new ArrayList<>();


    public void createDeck(){
        String[] suits = {"Hearts", "Spades", "Diamonds", "Clubs"};
        String[] names = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        for(int i = 0; i < suits.length; i++){
            for(int j = 0; j < 13; j++){
                fullDeck.add(new Card(suits[i], names[j], (j +1)));
            }
        }
    }
}
