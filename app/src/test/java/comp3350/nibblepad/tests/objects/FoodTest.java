package comp3350.nibblepad.tests.objects;

import junit.framework.TestCase;

import comp3350.nibblepad.objects.Food;

public class FoodTest extends TestCase {
    public void testFoodGetters() {
        Food food1 = new Food(20, "avocado", "not bad");
        assertNotNull(food1);
        assertSame(20, food1.getFoodID());
        assertSame("avocado", food1.getFoodName());
        assertSame("not bad", food1.getNotes());

        Food food2 = new Food(21);
        assertNotNull(food2);
        assertSame(21, food2.getFoodID());
        assertNull(food2.getFoodName());
    }

    public void testFoodGetNameException() {
        Food food1 = new Food(20, null);

        // Food can be created with a null foodName. Can't get a food if its name is null
        assertNull(food1.getFoodName());
    }

    public void testFoodSetters() {
        String originalFoodName = "avacado";
        String newFoodName = "big mac";
        String originalNote = "okay-ish";
        String newNote = "reeeeaaallly goooood";
        Food food = new Food(20, originalFoodName, originalNote);

        assertSame(food.getFoodName(), originalFoodName);
        assertSame(food.getNotes(), originalNote);
        food.setFoodName(newFoodName);
        food.setNotes(newNote);
        assertSame(food.getFoodName(), newFoodName);
        assertSame(food.getNotes(), newNote);
    }

    public void testFoodEquals() {
        Food food1;
        Food food2;

        food1 = new Food(20, "avocado", "mayhaps");
        food2 = new Food(21, "blueberry", "quite possibly");
        assertFalse(food1.equals(food2));

        food1 = new Food(20, "avocado", "one might say so");
        food2 = new Food(21, "avocado", "sounds reasonable to me");
        assertFalse(food1.equals(food2));

        food1 = new Food(20, "avocado");
        food2 = new Food(20, "avocado");
        assertTrue(food1.equals(food2));

        food1 = new Food(20, null, "definitely");
        food2 = new Food(20, null, "definitely");
        assertTrue(food1.equals(food2));
    }
}
