package comp3350.nibblepad;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasType;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import android.app.Instrumentation;
import android.content.Intent;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import junit.framework.TestCase;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import comp3350.nibblepad.application.Services;
import comp3350.nibblepad.business.AccessFoods;
import comp3350.nibblepad.business.AccessRestaurants;
import comp3350.nibblepad.objects.Food;
import comp3350.nibblepad.objects.Restaurant;
import comp3350.nibblepad.presentation.HomeActivity;
import comp3350.nibblepad.presentation.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShareAcceptanceTests extends TestCase {
    private static AccessFoods foodsAccessor = null;
    private static AccessRestaurants restaurantsAccessor = null;

    @Rule
    public IntentsTestRule<HomeActivity> intentsTestRule =
            new IntentsTestRule<>(HomeActivity.class);

    @Before
    public void startUp() {
        // Singletons
        if (foodsAccessor == null && restaurantsAccessor == null) {
            Services.getFoodPersistence();
            foodsAccessor = new AccessFoods();
            restaurantsAccessor = new AccessRestaurants();
        }
    }

    @Test
    public void testShare() {
        Matcher<Intent> expectedInnerIntent = allOf(
                hasAction(Intent.ACTION_SEND),
                hasType("text/plain")
        );

        // Add the food names as strings we expect to see in the intent's text
        List<Food> foods = foodsAccessor.getFoods();
        for (Food food: foods) {
            expectedInnerIntent = allOf(
                    hasExtra(equalTo(Intent.EXTRA_TEXT), containsString(food.getFoodName())),
                    expectedInnerIntent
            );
        }

        // Add the restaurant names as strings we expect to see in the intent's text
        List<Restaurant> restaurants = restaurantsAccessor.getRestaurants();
        for (Restaurant restaurant: restaurants) {
            expectedInnerIntent = allOf(
                    hasExtra(equalTo(Intent.EXTRA_TEXT), containsString(restaurant.getName())),
                    expectedInnerIntent
            );
        }

        Matcher<Intent> expectedIntent = allOf(
                hasAction(Intent.ACTION_CHOOSER),
                hasExtra(equalTo(Intent.EXTRA_INTENT), expectedInnerIntent),
                hasExtra(equalTo(Intent.EXTRA_TITLE), containsString("Share With"))
        );


        // Close all existing intents
        intending(anyIntent()).respondWith(new Instrumentation.ActivityResult(0, null));

        onView(withId(R.id.option_share)).perform(click());
        intended(expectedIntent);
    }
}