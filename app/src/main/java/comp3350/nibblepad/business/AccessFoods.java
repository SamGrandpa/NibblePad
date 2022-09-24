package comp3350.nibblepad.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.nibblepad.application.Services;
import comp3350.nibblepad.objects.Food;
import comp3350.nibblepad.persistence.FoodPersistence;

public class AccessFoods {
    private FoodPersistence foodPersistence;
    private List<Food> foods;
    private Food food;
    private int currentFood;

    public AccessFoods() {
        this(Services.getFoodPersistence());
    }

    public AccessFoods(final FoodPersistence foodPersistence) {
        this.foodPersistence = foodPersistence;
        foods = null;
        food = null;
        currentFood = 0;
    }

    public List<Food> getFoods() {
        foods = foodPersistence.getFoodSequential();
        List<Food> foodsClone = new ArrayList<>(foods);
        return foodsClone;
    }

    public Food getSequential() {
        if (foods == null) {
            foods = foodPersistence.getFoodSequential();
            currentFood = 0;
        }
        if (currentFood < foods.size()) {
            food = foods.get(currentFood);
            currentFood++;
        }
        else {
            foods = null;
            food = null;
            currentFood = 0;
        }
        return food;
    }

    public Food insertFood(Food currentFood) {
        return foodPersistence.insertFood(currentFood);
    }

    public Food updateFood(Food currentFood) {
        return foodPersistence.updateFood(currentFood);
    }

    public void deleteFood(Food currentFood) {
        foodPersistence.deleteFood(currentFood);
    }
}
