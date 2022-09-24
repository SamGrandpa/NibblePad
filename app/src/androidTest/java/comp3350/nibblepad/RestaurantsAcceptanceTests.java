package comp3350.nibblepad;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.anything;
import static comp3350.nibblepad.utils.AcceptanceTestUtils.withListSize;
import static comp3350.nibblepad.utils.AcceptanceTestUtils.withValue;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import comp3350.nibblepad.application.Services;
import comp3350.nibblepad.business.AccessRestaurants;
import comp3350.nibblepad.presentation.HomeActivity;
import comp3350.nibblepad.presentation.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RestaurantsAcceptanceTests extends TestCase {
    private static AccessRestaurants restaurantsAccessor = null;

    @Rule
    public ActivityScenarioRule<HomeActivity> activityRule = new ActivityScenarioRule<>(HomeActivity.class);

    @Before
    public void startUp() {
        // Singleton
        if (restaurantsAccessor == null) {
            Services.getRestaurantPersistence();
            restaurantsAccessor = new AccessRestaurants();
        }
        onView(withId(R.id.nav_restaurants)).perform(click());
    }

    private void addRestaurant(String restaurantName, String restaurantCuisine, String restaurantAddress, String restaurantNumber, String dialogueChoice) {
        // Click the add (+) button
        onView(withId(R.id.add_restaurant)).perform(click());

        // Type in the restaurant's name
        onView(withId(R.id.edit_restaurant_name)).perform(typeText(restaurantName));

        // Type in the restaurant's cuisine
        onView(withId(R.id.edit_cuisine_type)).perform(typeText(restaurantCuisine));

        // Type in the restaurant's address
        onView(withId(R.id.edit_address)).perform(typeText(restaurantAddress));

        // Type in the restaurant's phone number
        onView(withId(R.id.edit_phone_number)).perform(typeText(restaurantNumber));

        // Click "confirm"
        onView(withText(dialogueChoice)).perform(click());
    }

    private void addRestaurant(String restaurantName, String restaurantCuisine, String restaurantAddress, String restaurantNumber) {
        addRestaurant(restaurantName, restaurantCuisine, restaurantAddress, restaurantNumber, "Confirm");
    }

    @Test
    public void testAddRestaurantConfirm() {
        int initialRestaurantsCount = restaurantsAccessor.getRestaurants().size();
        String testRestaurantName = "Delta9 " + initialRestaurantsCount;
        String testRestaurantCuisine = "Fun";
        String testRestaurantAddress = "827 Dakota St Unit 1";
        String testRestaurantNumber = "2042247390";

        addRestaurant(testRestaurantName, testRestaurantCuisine, testRestaurantAddress, testRestaurantNumber);

        // Verify that the restaurant is there
        onView(withId(R.id.restaurants_list)).check(matches(withValue(testRestaurantName)));
        onView(withId(R.id.restaurants_list)).check(matches(withListSize(initialRestaurantsCount + 1)));

        restaurantsAccessor.deleteRestaurant(restaurantsAccessor.getRestaurants().get(initialRestaurantsCount));
    }

    @Test
    public void testAddRestaurantCancel() {
        int initialRestaurantsCount = restaurantsAccessor.getRestaurants().size();
        String testRestaurantName = "Delta9 " + initialRestaurantsCount;
        String testRestaurantCuisine = "Fun";
        String testRestaurantAddress = "827 Dakota St Unit 1";
        String testRestaurantNumber = "2042247390";

        addRestaurant(testRestaurantName, testRestaurantCuisine, testRestaurantAddress, testRestaurantNumber, "Cancel");

        // Verify that the restaurant is not there
        onView(withId(R.id.restaurants_list)).check(matches(not(withValue(testRestaurantName))));
        onView(withId(R.id.restaurants_list)).check(matches(withListSize(initialRestaurantsCount)));
    }

    @Test
    public void testRemoveRestaurantConfirm() {
        int initialRestaurantsCount = restaurantsAccessor.getRestaurants().size();
        String testRestaurantName = "Delta9 " + initialRestaurantsCount;
        String testRestaurantCuisine = "Fun";
        String testRestaurantAddress = "827 Dakota St Unit 1";
        String testRestaurantNumber = "2042247390";

        addRestaurant(testRestaurantName, testRestaurantCuisine, testRestaurantAddress, testRestaurantNumber);

        // Verify that the restaurant is there
        onView(withId(R.id.restaurants_list)).check(matches(withValue(testRestaurantName)));
        onView(withId(R.id.restaurants_list)).check(matches(withListSize(initialRestaurantsCount + 1)));

        // Long press the restaurant
        onData(anything()).inAdapterView(withId(R.id.restaurants_list))
                .atPosition(initialRestaurantsCount)
                .perform(longClick());

        // Click "yes"
        onView(withText("Yes")).perform(click());

        // Verify that the restaurant is not there anymore
        onView(withId(R.id.restaurants_list)).check(matches(not(withValue(testRestaurantName))));
        onView(withId(R.id.restaurants_list)).check(matches(withListSize(initialRestaurantsCount)));
    }

    @Test
    public void testRemoveRestaurantCancel() {
        int initialRestaurantsCount = restaurantsAccessor.getRestaurants().size();
        String testRestaurantName = "Delta9 " + initialRestaurantsCount;
        String testRestaurantCuisine = "Fun";
        String testRestaurantAddress = "827 Dakota St Unit 1";
        String testRestaurantNumber = "2042247390";

        addRestaurant(testRestaurantName, testRestaurantCuisine, testRestaurantAddress, testRestaurantNumber, "Confirm");

        // Verify that the restaurant is there
        onView(withId(R.id.restaurants_list)).check(matches(withValue(testRestaurantName)));
        onView(withId(R.id.restaurants_list)).check(matches(withListSize(initialRestaurantsCount + 1)));

        // Long press the restaurant
        onData(anything()).inAdapterView(withId(R.id.restaurants_list))
                .atPosition(initialRestaurantsCount)
                .perform(longClick());

        // Click "no"
        onView(withText("No")).perform(click());

        // Verify that the restaurant is still there
        onView(withId(R.id.restaurants_list)).check(matches(withValue(testRestaurantName)));
        onView(withId(R.id.restaurants_list)).check(matches(withListSize(initialRestaurantsCount + 1)));

        restaurantsAccessor.deleteRestaurant(restaurantsAccessor.getRestaurants().get(initialRestaurantsCount));
    }

    @Test
    public void testRestaurantNoteEditSave() {
        int initialRestaurantsCount = restaurantsAccessor.getRestaurants().size();
        String testRestaurantName = "Delta9 " + initialRestaurantsCount;
        String testRestaurantCuisine = "Fun";
        String testRestaurantAddress = "827 Dakota St Unit 1";
        String testRestaurantNumber = "2042247390";
        String testNote = "Gets you really far along the Z axis.";

        addRestaurant(testRestaurantName, testRestaurantCuisine, testRestaurantAddress, testRestaurantNumber);

        // Verify that the restaurant is there
        onView(withId(R.id.restaurants_list)).check(matches(withValue(testRestaurantName)));
        onView(withId(R.id.restaurants_list)).check(matches(withListSize(initialRestaurantsCount + 1)));

        // Click on the restaurant
        onData(anything()).inAdapterView(withId(R.id.restaurants_list))
                .atPosition(initialRestaurantsCount)
                .perform(click());

        // There shouldn't be any note initially (the text should be the placeholder)
        onView(withId(R.id.note_restaurant_text)).check(matches(withText("No notes for this restaurant")));

        // Click "edit"
        onView(withText("Edit")).perform(click());

        // Type in the note
        onView(withId(R.id.note_restaurant_edit)).perform(typeTextIntoFocusedView(testNote));

        // Click "save"
        onView(withText("Save")).perform(click());

        // Click on the restaurant again
        onData(anything()).inAdapterView(withId(R.id.restaurants_list))
                .atPosition(initialRestaurantsCount)
                .perform(click());

        // Make sure the note matches the one we entered
        onView(withId(R.id.note_restaurant_text)).check(matches(withText(testNote)));

        restaurantsAccessor.deleteRestaurant(restaurantsAccessor.getRestaurants().get(initialRestaurantsCount));
    }

    @Test
    public void testRestaurantNoteExit() {
        int initialRestaurantsCount = restaurantsAccessor.getRestaurants().size();
        String testRestaurantName = "Delta9 " + initialRestaurantsCount;
        String testRestaurantCuisine = "Fun";
        String testRestaurantAddress = "827 Dakota St Unit 1";
        String testRestaurantNumber = "2042247390";

        addRestaurant(testRestaurantName, testRestaurantCuisine, testRestaurantAddress, testRestaurantNumber);

        // Verify that the restaurant is there
        onView(withId(R.id.restaurants_list)).check(matches(withValue(testRestaurantName)));
        onView(withId(R.id.restaurants_list)).check(matches(withListSize(initialRestaurantsCount + 1)));

        // Click on the restaurant
        onData(anything()).inAdapterView(withId(R.id.restaurants_list))
                .atPosition(initialRestaurantsCount)
                .perform(click());

        // There shouldn't be any note initially (the text should be the placeholder)
        onView(withId(R.id.note_restaurant_text)).check(matches(withText("No notes for this restaurant")));

        // Click "exit"
        onView(withText("Exit")).perform(click());

        // Click on the restaurant
        onData(anything()).inAdapterView(withId(R.id.restaurants_list))
                .atPosition(initialRestaurantsCount)
                .perform(click());

        // There still shouldn't be any note (the text should be the placeholder)
        onView(withId(R.id.note_restaurant_text)).check(matches(withText("No notes for this restaurant")));

        restaurantsAccessor.deleteRestaurant(restaurantsAccessor.getRestaurants().get(initialRestaurantsCount));
    }

    @Test
    public void testRestaurantNoteEditCancel() {
        int initialRestaurantsCount = restaurantsAccessor.getRestaurants().size();
        String testRestaurantName = "Delta9 " + initialRestaurantsCount;
        String testRestaurantCuisine = "Fun";
        String testRestaurantAddress = "827 Dakota St Unit 1";
        String testRestaurantNumber = "2042247390";
        String testNote = "Gets you really far along the Z axis.";

        addRestaurant(testRestaurantName, testRestaurantCuisine, testRestaurantAddress, testRestaurantNumber);

        // Verify that the restaurant is there
        onView(withId(R.id.restaurants_list)).check(matches(withValue(testRestaurantName)));
        onView(withId(R.id.restaurants_list)).check(matches(withListSize(initialRestaurantsCount + 1)));

        // Click on the restaurant
        onData(anything()).inAdapterView(withId(R.id.restaurants_list))
                .atPosition(initialRestaurantsCount)
                .perform(click());

        // There shouldn't be any note (the text should be the placeholder)
        onView(withId(R.id.note_restaurant_text)).check(matches(withText("No notes for this restaurant")));

        // Click "edit"
        onView(withText("Edit")).perform(click());

        // Type in the note
        onView(withId(R.id.note_restaurant_edit)).perform(typeTextIntoFocusedView(testNote));

        // Click "cancel"
        onView(withText("Cancel")).perform(click());

        // Click on the restaurant
        onData(anything()).inAdapterView(withId(R.id.restaurants_list))
                .atPosition(initialRestaurantsCount)
                .perform(click());

        // There still shouldn't be any note (the text should be the placeholder)
        onView(withId(R.id.note_restaurant_text)).check(matches(withText("No notes for this restaurant")));

        restaurantsAccessor.deleteRestaurant(restaurantsAccessor.getRestaurants().get(initialRestaurantsCount));
    }
}