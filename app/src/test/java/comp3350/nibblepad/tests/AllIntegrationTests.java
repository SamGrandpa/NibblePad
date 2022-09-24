package comp3350.nibblepad.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.nibblepad.tests.business.AccessFoodsIT;
import comp3350.nibblepad.tests.business.AccessRestaurantsIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessRestaurantsIT.class,
        AccessFoodsIT.class
})
public class AllIntegrationTests {
}
