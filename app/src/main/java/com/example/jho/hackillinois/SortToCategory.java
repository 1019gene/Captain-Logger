package com.example.jho.hackillinois;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SortToCategory {

    ReceiptData passedThing;
    HashMap<String, Double> data;

    SortToCategory(ReceiptData setPassedThing) {
        this.passedThing = setPassedThing;

        this.data = passedThing.getHashMap();
    }

    /*
    Clothing
    Food
    Gas
    Books
    Travel Expense
    Entertainment
     */

    private ArrayList<String> clothingKeywords = new ArrayList<>(Arrays.asList("shirt", "fleece", "hood", "sock"
            , "dress", "pant", "coat", "jacket", "underwear", "garment", "skirt", "suit", "bra"));
    private ArrayList<String> foodKeywords = new ArrayList<>(Arrays.asList("burrito", "taco"));
    private ArrayList<String> gasKeywords = new ArrayList<>(Arrays.asList("fillup", "shell", "chevron", "gallons"));
    private ArrayList<String> entertainmentKeywords = new ArrayList<>(Arrays.asList("movie"));



    public HashMap<Categories, ArrayList<Item>> sort() {
        if (data == null) {
            System.err.println("Wack, null data");
            return null;
        }

        ArrayList<Item> clothingList = new ArrayList<>();
        ArrayList<Item> foodList = new ArrayList<>();
        ArrayList<Item> gasList = new ArrayList<>();
        ArrayList<Item> entertainmentList = new ArrayList<>();
        ArrayList<Item> unknownList = new ArrayList<>();

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            Item toAdd = new Item(entry.getKey(), entry.getValue());

            for (String keyword : clothingKeywords) {
                if (entry.getKey().contains(keyword)) {
                    clothingList.add(toAdd);
                    break;
                }
            }

            for (String keyword : foodKeywords) {
                if (entry.getKey().contains(keyword)) {
                    foodList.add(toAdd);
                    break;
                }
            }

            for (String keyword : gasKeywords) {
                if (entry.getKey().contains(keyword)) {
                    gasList.add(toAdd);
                    break;
                }
            }

            for (String keyword : entertainmentKeywords) {
                if (entry.getKey().contains(keyword)) {
                    entertainmentList.add(toAdd);
                    break;
                }
            }

            unknownList.add(toAdd);
        }

        HashMap<Categories, ArrayList<Item>> toReturn = new HashMap<>();
        toReturn.put(Categories.Clothing, clothingList);
        toReturn.put(Categories.Food, foodList);
        toReturn.put(Categories.Gas, gasList);
        toReturn.put(Categories.Entertainment, entertainmentList);
        toReturn.put(Categories.Unknown, unknownList);


        return toReturn;
    }
}
