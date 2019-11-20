package edu.csuci.appaca.data;

import java.util.Stack;

import edu.csuci.appaca.data.statics.StaticFoodItem;

public class FoodToEat {

    private enum FoodStack {
        INSTANCE;
        Stack<StaticFoodItem> foodStack;
        FoodStack() {
            foodStack = new Stack<>();
        }
    }

    public static void push(StaticFoodItem foodItem) {
        FoodStack.INSTANCE.foodStack.push(foodItem);
    }

    public static StaticFoodItem pop() {
        return FoodStack.INSTANCE.foodStack.pop();
    }

    public static boolean isEmpty() {
        return FoodStack.INSTANCE.foodStack.isEmpty();
    }

}
