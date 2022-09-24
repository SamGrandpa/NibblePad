package comp3350.nibblepad.persistence.stubs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import comp3350.nibblepad.objects.Restaurant;
import comp3350.nibblepad.persistence.RestaurantPersistence;

public class RestaurantPersistenceStub implements RestaurantPersistence {
    private List<Restaurant> restaurants;

    public RestaurantPersistenceStub() {
        Restaurant restaurant;

        // Create the restaurants arraylist
        restaurants = new ArrayList<>();

        // Add stub restaurant 1
        restaurant = new Restaurant(1, "Le Continental", "cuisine 1", "address 1", "phone number 1");
        restaurants.add(restaurant);

        // Add stub restaurant 2
        restaurant = new Restaurant(2, "One Great City Brewing Company", "cuisine 2", "address 2", "phone number 2");
        restaurants.add(restaurant);

        // Add stub restaurant 3
        restaurant = new Restaurant(3, "Bistro Dansk Restaurant Ltd", "cuisine 3", "address 3", "phone number 3");
        restaurants.add(restaurant);

        // Add stub restaurant 4
        restaurant = new Restaurant(4, "Close Company", "cuisine 4", "address 4", "phone number 4", "Mhello lmao");
        restaurants.add(restaurant);

        // Add stub restaurant 5
        restaurant = new Restaurant(5, "Sargent Taco Shop", "cuisine 5", "address 5", "phone number 5");
        restaurants.add(restaurant);

        // Add stub restaurant 6
        restaurant = new Restaurant(6, "Harth Mozza & Wine Bar", "cuisine 6", "address 6", "phone number 6");
        restaurants.add(restaurant);

        // Add stub restaurant 7
        restaurant = new Restaurant(7, "Spice Circle East Indian Restaurant", "cuisine 7", "address 7", "phone number 7", "Mhello lmao");
        restaurants.add(restaurant);

        // Add stub restaurant 8
        restaurant = new Restaurant(8, "Enoteca", "cuisine 8", "address 8", "phone number 8");
        restaurants.add(restaurant);

        // Add stub restaurant 9
        restaurant = new Restaurant(9, "BMC Market", "cuisine 9", "address 9", "phone number 9");
        restaurants.add(restaurant);

        // Add stub restaurant 10
        restaurant = new Restaurant(10, "Portage Meat & Sausage Deli", "cuisine 10", "address 10", "phone number 10", "Mhello lmao");
        restaurants.add(restaurant);

        // Add stub restaurant 11
        restaurant = new Restaurant(11, "Thom Bargen Coffee and Tea", "cuisine 11", "address 11", "phone number 11");
        restaurants.add(restaurant);

        // Add stub restaurant 12
        restaurant = new Restaurant(12, "Clementine", "cuisine 12", "address 12", "phone number 12", "Mhello lmao");
        restaurants.add(restaurant);

        // Add stub restaurant 13
        restaurant = new Restaurant(13, "Luda's Deli", "cuisine 13", "address 13", "phone number 13");
        restaurants.add(restaurant);

        // Add stub restaurant 14
        restaurant = new Restaurant(14, "Roughage Eatery", "cuisine 14", "address 14", "phone number 14");
        restaurants.add(restaurant);

        // Add stub restaurant 15
        restaurant = new Restaurant(15, "Feast Cafe Bistro", "cuisine 15", "address 15", "phone number 15");
        restaurants.add(restaurant);

        // Add stub restaurant 16
        restaurant = new Restaurant(16, "Baraka Pita Bakery & Restaurant", "cuisine 16", "address 16", "phone number 16", "Mhello lmao");
        restaurants.add(restaurant);

        // Add stub restaurant 17
        restaurant = new Restaurant(17, "Junction 59 Roadhouse", "cuisine 17", "address 17", "phone number 17");
        restaurants.add(restaurant);

        // Add stub restaurant 18
        restaurant = new Restaurant(18, "Quiznos", "cuisine 18", "address 18", "phone number 18");
        restaurants.add(restaurant);

        // Add stub restaurant 19
        restaurant = new Restaurant(19, "Famenas Famous Roti & Curry", "cuisine 19", "address 19", "phone number 19", "Mhello lmao");
        restaurants.add(restaurant);

        // Add stub restaurant 20
        restaurant = new Restaurant(20, "Myer's Delicatessen", "cuisine 20", "address 20", "phone number 20");
        restaurants.add(restaurant);

        // Add stub restaurant 21
        restaurant = new Restaurant(21, "Cindy's Burgers & Subs", "cuisine 21", "address 21", "phone number 21");
        restaurants.add(restaurant);

        // Add stub restaurant 22
        restaurant = new Restaurant(22, "Wanabees Diner", "cuisine 22", "address 22", "phone number 22");
        restaurants.add(restaurant);

        // Add stub restaurant 23
        restaurant = new Restaurant(23, "King + Bannatyne: The Sandwich Shop On The Corner", "cuisine 23", "address 23", "phone number 23");
        restaurants.add(restaurant);

        // Add stub restaurant 24
        restaurant = new Restaurant(24, "Mercadito Latino", "cuisine 24", "address 24", "phone number 24", "Mhello lmao");
        restaurants.add(restaurant);

        // Add stub restaurant 25
        restaurant = new Restaurant(25, "Shaba Thai Cuisine", "cuisine 25", "address 25", "phone number 25");
        restaurants.add(restaurant);
    }

    @Override
    public List<Restaurant> getRestaurantSequential() {
        return Collections.unmodifiableList(restaurants);
    }

    @Override
    public Restaurant insertRestaurant(Restaurant currentRestaurant) {
        // don't bother checking for duplicates
        restaurants.add(currentRestaurant);
        return currentRestaurant;
    }

    @Override
    public Restaurant updateRestaurant(Restaurant currentRestaurant) {
        int index;

        index = restaurants.indexOf(currentRestaurant);
        if (index >= 0) {
            restaurants.set(index, currentRestaurant);
        }
        return currentRestaurant;
    }

    @Override
    public void deleteRestaurant(Restaurant currentRestaurant) {
        int index;

        index = restaurants.indexOf(currentRestaurant);
        if (index >= 0) {
            restaurants.remove(index);
        }
    }
}
