package comp3350.nibblepad.tests.business;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.List;

import comp3350.nibblepad.business.AccessRestaurants;
import comp3350.nibblepad.objects.Restaurant;
import comp3350.nibblepad.persistence.hsqldb.RestaurantPersistenceHSQLDB;
import comp3350.nibblepad.tests.utils.TestUtils;

public class AccessRestaurantsIT extends TestCase {
    private AccessRestaurants restaurantsAccessor;
    private Restaurant newRestaurant1;
    private Restaurant newRestaurant2;
    private Restaurant newRestaurant3;

    public void setUp() throws IOException {
        restaurantsAccessor = new AccessRestaurants(new RestaurantPersistenceHSQLDB(TestUtils.getTmpDB().getAbsolutePath()));
        newRestaurant1 = new Restaurant(20, "Stella's", "cuisine 1", "address 1", "phone number 1");
        newRestaurant2 = new Restaurant(21, "Tehran Cafe", "cuisine 2", "address 2", "phone number 2", "tasty");
        newRestaurant3 = new Restaurant(22, "Curry King", "cuisine 3", "address 3", "phone number 3");
    }

    public void testAccessRestaurantsNotNull() {
        assertNotNull(restaurantsAccessor);
    }

    public void testAccessRestaurantsInsert() {
        Restaurant insertRestaurantResult1 = restaurantsAccessor.insertRestaurant(newRestaurant1);
        assertSame(insertRestaurantResult1, newRestaurant1);

        Restaurant insertRestaurantResult2 = restaurantsAccessor.insertRestaurant(newRestaurant2);
        assertSame(insertRestaurantResult2, newRestaurant2);

        Restaurant insertRestaurantResult3 = restaurantsAccessor.insertRestaurant(newRestaurant3);
        assertSame(insertRestaurantResult3, newRestaurant3);
    }

    public void testAccessRestaurantsGet() {
        restaurantsAccessor.insertRestaurant(newRestaurant1);
        restaurantsAccessor.insertRestaurant(newRestaurant2);
        restaurantsAccessor.insertRestaurant(newRestaurant3);

        List<Restaurant> restaurants = restaurantsAccessor.getRestaurants();
        assertTrue(restaurants.contains(newRestaurant1));
        assertTrue(restaurants.contains(newRestaurant2));
        assertTrue(restaurants.contains(newRestaurant3));
    }

    public void testAccessRestaurantsGetSequential() {
        List<Restaurant> restaurants = restaurantsAccessor.getRestaurants();

        for (Restaurant restaurant: restaurants) {
            assertSame(restaurantsAccessor.getSequential(), restaurant);
        }
    }

    public void testAccessRestaurantsUpdate() {
        int restaurantId = newRestaurant1.getRestaurantID();
        String newRestaurantName = "Delta9";

        restaurantsAccessor.insertRestaurant(newRestaurant1);
        newRestaurant1.setName(newRestaurantName);
        restaurantsAccessor.updateRestaurant(newRestaurant1);

        List<Restaurant> restaurants = restaurantsAccessor.getRestaurants();
        int restaurantInd = restaurants.indexOf(newRestaurant1);
        Restaurant updatedRestaurant = restaurants.get(restaurantInd);

        assertEquals(updatedRestaurant.getRestaurantID(), restaurantId);
        assertEquals(updatedRestaurant.getName(), newRestaurantName);
    }

    public void testAccessRestaurantsDelete() {
        Restaurant testRestaurant = new Restaurant(20, "Degrees Restaurant", "cuisine 3", "address 3", "phone number 3", "tasty");

        List<Restaurant> restaurantsBeforeInsert = restaurantsAccessor.getRestaurants();
        assertFalse(restaurantsBeforeInsert.contains(testRestaurant));

        restaurantsAccessor.insertRestaurant(testRestaurant);

        List<Restaurant> restaurantsAfterInsert = restaurantsAccessor.getRestaurants();
        assertTrue(restaurantsAfterInsert.contains(testRestaurant));

        restaurantsAccessor.deleteRestaurant(testRestaurant);

        List<Restaurant> restaurantsAfterDelete = restaurantsAccessor.getRestaurants();
        assertFalse(restaurantsAfterDelete.contains(testRestaurant));
    }

    public void tearDown() {
        List<Restaurant> restaurants = restaurantsAccessor.getRestaurants();
        if (restaurants.contains(newRestaurant1))
            restaurantsAccessor.deleteRestaurant(newRestaurant1);
        if (restaurants.contains(newRestaurant2))
            restaurantsAccessor.deleteRestaurant(newRestaurant2);
        if (restaurants.contains(newRestaurant3))
            restaurantsAccessor.deleteRestaurant(newRestaurant3);
    }
}
