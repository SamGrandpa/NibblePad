package comp3350.nibblepad.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.nibblepad.tests.business.AccessFoodsTest;
import comp3350.nibblepad.tests.business.AccessRestaurantsTest;
import comp3350.nibblepad.tests.business.RandomFoodTest;
import comp3350.nibblepad.tests.objects.FoodTest;
import comp3350.nibblepad.tests.objects.RestaurantTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        FoodTest.class,
        RestaurantTest.class,
        AccessFoodsTest.class,
        AccessRestaurantsTest.class,
        RandomFoodTest.class
})
public class AllUnitTests {
}
