package comp3350.nibblepad;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        FoodsAcceptanceTests.class,
        RestaurantsAcceptanceTests.class,
        ShareAcceptanceTests.class
})
public class AllAcceptanceTests {
}
