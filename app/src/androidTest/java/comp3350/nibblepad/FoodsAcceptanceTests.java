package comp3350.nibblepad;

import comp3350.nibblepad.application.Services;
import comp3350.nibblepad.presentation.HomeActivity;
import comp3350.nibblepad.presentation.R;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import comp3350.nibblepad.business.AccessFoods;
import static comp3350.nibblepad.utils.AcceptanceTestUtils.withListSize;
import static comp3350.nibblepad.utils.AcceptanceTestUtils.withValue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.anything;

import junit.framework.TestCase;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FoodsAcceptanceTests extends TestCase {
    private static AccessFoods foodsAccessor = null;

    @Rule
    public ActivityScenarioRule<HomeActivity> activityRule = new ActivityScenarioRule<>(HomeActivity.class);

    @Before
    public void startUp() {
        // Singleton
        if (foodsAccessor == null) {
            Services.getFoodPersistence();
            foodsAccessor = new AccessFoods();
        }
    }

    private void addFood(String foodName, String dialogueChoice) {
        // Click the add (+) button
        onView(withId(R.id.add_food)).perform(click());

        // Type in the food's name
        onView(withId(R.id.edit_food_name)).perform(typeText(foodName));

        // Click "confirm"
        onView(withText(dialogueChoice)).perform(click());
    }

    private void addFood(String foodName) {
        addFood(foodName, "Confirm");
    }

    @Test
    public void testAddFoodConfirm() {
        int initialFoodsCount = foodsAccessor.getFoods().size();
        String testFoodName = "Mandarin Cookies " + initialFoodsCount;

        addFood(testFoodName);

        // Verify that the food is there
        onView(withId(R.id.foods_list)).check(matches(withValue(testFoodName)));
        onView(withId(R.id.foods_list)).check(matches(withListSize(initialFoodsCount + 1)));

        foodsAccessor.deleteFood(foodsAccessor.getFoods().get(initialFoodsCount));
    }

    @Test
    public void testAddFoodCancel() {
        int initialFoodsCount = foodsAccessor.getFoods().size();
        String testFoodName = "Mandarin Cookies " + initialFoodsCount;

        addFood(testFoodName, "Cancel");

        // Verify that the food is not there
        onView(withId(R.id.foods_list)).check(matches(not(withValue(testFoodName))));
        onView(withId(R.id.foods_list)).check(matches(withListSize(initialFoodsCount)));
    }

    @Test
    public void testRemoveFoodConfirm() {
        int initialFoodsCount = foodsAccessor.getFoods().size();
        String testFoodName = "Mandarin Cookies " + initialFoodsCount;

        addFood(testFoodName);

        // Verify that the food is there
        onView(withId(R.id.foods_list)).check(matches(withValue(testFoodName)));
        onView(withId(R.id.foods_list)).check(matches(withListSize(initialFoodsCount + 1)));

        // Long press the food
        onData(anything()).inAdapterView(withId(R.id.foods_list))
                .atPosition(initialFoodsCount)
                .perform(longClick());

        // Click "yes"
        onView(withText("Yes")).perform(click());

        // Verify that the food is not there anymore
        onView(withId(R.id.foods_list)).check(matches(not(withValue(testFoodName))));
        onView(withId(R.id.foods_list)).check(matches(withListSize(initialFoodsCount)));
    }

    @Test
    public void testRemoveFoodCancel() {
        int initialFoodsCount = foodsAccessor.getFoods().size();
        String testFoodName = "Mandarin Cookies " + initialFoodsCount;

        addFood(testFoodName, "Confirm");

        // Verify that the food is there
        onView(withId(R.id.foods_list)).check(matches(withValue(testFoodName)));
        onView(withId(R.id.foods_list)).check(matches(withListSize(initialFoodsCount + 1)));

        // Long press the food
        onData(anything()).inAdapterView(withId(R.id.foods_list))
                .atPosition(initialFoodsCount)
                .perform(longClick());

        // Click "no"
        onView(withText("No")).perform(click());

        // Verify that the food is still there
        onView(withId(R.id.foods_list)).check(matches(withValue(testFoodName)));
        onView(withId(R.id.foods_list)).check(matches(withListSize(initialFoodsCount + 1)));

        foodsAccessor.deleteFood(foodsAccessor.getFoods().get(initialFoodsCount));
    }

    @Test
    public void testFoodNoteEditSave() {
        int initialFoodsCount = foodsAccessor.getFoods().size();
        String testFoodName = "Mandarin Cookies " + initialFoodsCount;
        String testNote = "Gets you really far along the Z axis.";

        addFood(testFoodName);

        // Verify that the food is there
        onView(withId(R.id.foods_list)).check(matches(withValue(testFoodName)));
        onView(withId(R.id.foods_list)).check(matches(withListSize(initialFoodsCount + 1)));

        // Click on the food
        onData(anything()).inAdapterView(withId(R.id.foods_list))
                .atPosition(initialFoodsCount)
                .perform(click());

        // There shouldn't be any note initially (the text should be the placeholder)
        onView(withId(R.id.note_food_text)).check(matches(withText("No notes for this food")));

        // Click "edit"
        onView(withText("Edit")).perform(click());

        // Type in the note
        onView(withId(R.id.note_food_edit)).perform(typeTextIntoFocusedView(testNote));

        // Click "save"
        onView(withText("Save")).perform(click());

        // Click on the food again
        onData(anything()).inAdapterView(withId(R.id.foods_list))
                .atPosition(initialFoodsCount)
                .perform(click());

        // Make sure the note matches the one we entered
        onView(withId(R.id.note_food_text)).check(matches(withText(testNote)));

        foodsAccessor.deleteFood(foodsAccessor.getFoods().get(initialFoodsCount));
    }

    @Test
    public void testFoodNoteExit() {
        int initialFoodsCount = foodsAccessor.getFoods().size();
        String testFoodName = "Mandarin Cookies " + initialFoodsCount;

        addFood(testFoodName);

        // Verify that the food is there
        onView(withId(R.id.foods_list)).check(matches(withValue(testFoodName)));
        onView(withId(R.id.foods_list)).check(matches(withListSize(initialFoodsCount + 1)));

        // Click on the food
        onData(anything()).inAdapterView(withId(R.id.foods_list))
                .atPosition(initialFoodsCount)
                .perform(click());

        // There shouldn't be any note initially (the text should be the placeholder)
        onView(withId(R.id.note_food_text)).check(matches(withText("No notes for this food")));

        // Click "exit"
        onView(withText("Exit")).perform(click());

        // Click on the food
        onData(anything()).inAdapterView(withId(R.id.foods_list))
                .atPosition(initialFoodsCount)
                .perform(click());

        // There still shouldn't be any note (the text should be the placeholder)
        onView(withId(R.id.note_food_text)).check(matches(withText("No notes for this food")));

        foodsAccessor.deleteFood(foodsAccessor.getFoods().get(initialFoodsCount));
    }

    @Test
    public void testFoodNoteEditCancel() {
        int initialFoodsCount = foodsAccessor.getFoods().size();
        String testFoodName = "Mandarin Cookies " + initialFoodsCount;
        String testNote = "Gets you really far along the Z axis.";

        addFood(testFoodName);

        // Verify that the food is there
        onView(withId(R.id.foods_list)).check(matches(withValue(testFoodName)));
        onView(withId(R.id.foods_list)).check(matches(withListSize(initialFoodsCount + 1)));

        // Click on the food
        onData(anything()).inAdapterView(withId(R.id.foods_list))
                .atPosition(initialFoodsCount)
                .perform(click());

        // There shouldn't be any note (the text should be the placeholder)
        onView(withId(R.id.note_food_text)).check(matches(withText("No notes for this food")));

        // Click "edit"
        onView(withText("Edit")).perform(click());

        // Type in the note
        onView(withId(R.id.note_food_edit)).perform(typeTextIntoFocusedView(testNote));

        // Click "cancel"
        onView(withText("Cancel")).perform(click());

        // Click on the food
        onData(anything()).inAdapterView(withId(R.id.foods_list))
                .atPosition(initialFoodsCount)
                .perform(click());

        // There still shouldn't be any note (the text should be the placeholder)
        onView(withId(R.id.note_food_text)).check(matches(withText("No notes for this food")));

        foodsAccessor.deleteFood(foodsAccessor.getFoods().get(initialFoodsCount));
    }
}