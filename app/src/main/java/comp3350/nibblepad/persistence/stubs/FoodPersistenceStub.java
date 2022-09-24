package comp3350.nibblepad.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.nibblepad.objects.Food;
import comp3350.nibblepad.persistence.FoodPersistence;

public class FoodPersistenceStub implements FoodPersistence {
    private List<Food> foods;

    public FoodPersistenceStub() {
        Food food;

        // Create the foods arraylist
        foods = new ArrayList<>();

        // Add stub food 1
        food = new Food(1, "Bluefish");
        foods.add(food);

        // Add stub food 2
        food = new Food(2, "Kiwi");
        foods.add(food);

        // Add stub food 3
        food = new Food(3, "Eggrolls", "Num num");
        foods.add(food);

        // Add stub food 4
        food = new Food(4, "Apple juice", "Num num");
        foods.add(food);

        // Add stub food 5
        food = new Food(5, "Chicken");
        foods.add(food);

        // Add stub food 6
        food = new Food(6, "Lasagna");
        foods.add(food);

        // Add stub food 7
        food = new Food(7, "Arugala", "Num num");
        foods.add(food);

        // Add stub food 8
        food = new Food(8, "Ghorme sabzi");
        foods.add(food);

        // Add stub food 9
        food = new Food(9, "Pepperoni");
        foods.add(food);

        // Add stub food 10
        food = new Food(10, "Bagels", "Num num");
        foods.add(food);

        // Add stub food 11
        food = new Food(11, "Chowder");
        foods.add(food);

        // Add stub food 12
        food = new Food(12, "Garlic", "Num num");
        foods.add(food);

        // Add stub food 13
        food = new Food(13, "Pizza");
        foods.add(food);

        // Add stub food 14
        food = new Food(14, "Jalapeño");
        foods.add(food);

        // Add stub food 15
        food = new Food(15, "Cheese", "Num num");
        foods.add(food);

        // Add stub food 16
        food = new Food(16, "Cabbage");
        foods.add(food);

        // Add stub food 17
        food = new Food(17, "Almond");
        foods.add(food);

        // Add stub food 18
        food = new Food(18, "Toast");
        foods.add(food);

        // Add stub food 19
        food = new Food(19, "Gnocchi", "Num num");
        foods.add(food);

        // Add stub food 20
        food = new Food(20, "Chocolate");
        foods.add(food);

        // Add stub food 21
        food = new Food(21, "Bread");
        foods.add(food);

        // Add stub food 22
        food = new Food(22, "Walnuts");
        foods.add(food);

        // Add stub food 23
        food = new Food(23, "Bacon");
        foods.add(food);

        // Add stub food 24
        food = new Food(24, "Ahi tuna", "Num num");
        foods.add(food);

        // Add stub food 25
        food = new Food(25, "Spaghetti");
        foods.add(food);
    }

    @Override
    public List<Food> getFoodSequential() {
        return Collections.unmodifiableList(foods);
    }

    @Override
    public Food insertFood(Food currentFood) {
        // don't bother checking for duplicates
        foods.add(currentFood);
        return currentFood;
    }

    @Override
    public Food updateFood(Food currentFood) {
        int index;

        index = foods.indexOf(currentFood);
        if (index >= 0) {
            foods.set(index, currentFood);
        }
        return currentFood;
    }

    @Override
    public void deleteFood(Food currentFood) {
        int index;

        index = foods.indexOf(currentFood);
        if (index >= 0) {
            foods.remove(index);
        }
    }
}
