package comp3350.nibblepad.persistence;

import java.util.List;

import comp3350.nibblepad.objects.Restaurant;

public interface RestaurantPersistence {
    List<Restaurant> getRestaurantSequential();

    Restaurant insertRestaurant(Restaurant currentRestaurant);

    Restaurant updateRestaurant(Restaurant currentRestaurant);

    void deleteRestaurant(Restaurant currentRestaurant);
}
