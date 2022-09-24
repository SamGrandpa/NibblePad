package comp3350.nibblepad.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.nibblepad.application.Services;
import comp3350.nibblepad.objects.Restaurant;
import comp3350.nibblepad.persistence.RestaurantPersistence;

public class AccessRestaurants {
    private RestaurantPersistence restaurantPersistence;
    private List<Restaurant> restaurants;
    private Restaurant restaurant;
    private int currentRestaurant;

    public AccessRestaurants() {
        this(Services.getRestaurantPersistence());
    }

    public AccessRestaurants(final RestaurantPersistence restaurantPersistence) {
        this.restaurantPersistence = restaurantPersistence;
        restaurants = null;
        restaurant = null;
        currentRestaurant = 0;
    }

    public List<Restaurant> getRestaurants() {
        restaurants = restaurantPersistence.getRestaurantSequential();
        List<Restaurant> restaurantsClone = new ArrayList<>(restaurants);
        return restaurantsClone;
    }

    public Restaurant getSequential() {
        if (restaurants == null) {
            restaurants = restaurantPersistence.getRestaurantSequential();
            currentRestaurant = 0;
        }
        if (currentRestaurant < restaurants.size()) {
            restaurant = restaurants.get(currentRestaurant);
            currentRestaurant++;
        }
        else {
            restaurants = null;
            restaurant = null;
            currentRestaurant = 0;
        }
        return restaurant;
    }

    public Restaurant insertRestaurant(Restaurant currentRestaurant) {
        return restaurantPersistence.insertRestaurant(currentRestaurant);
    }

    public Restaurant updateRestaurant(Restaurant currentRestaurant) {
        return restaurantPersistence.updateRestaurant(currentRestaurant);
    }

    public void deleteRestaurant(Restaurant currentRestaurant) {
        restaurantPersistence.deleteRestaurant(currentRestaurant);
    }
}
