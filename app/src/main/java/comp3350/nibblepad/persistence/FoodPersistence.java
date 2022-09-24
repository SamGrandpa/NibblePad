package comp3350.nibblepad.persistence;

import java.util.List;

import comp3350.nibblepad.objects.Food;

public interface FoodPersistence {
    List<Food> getFoodSequential();

    Food insertFood(Food currentFood);

    Food updateFood(Food currentFood);

    void deleteFood(Food currentFood);
}
