package comp3350.nibblepad.tests.business;

import junit.framework.TestCase;

import java.io.IOException;
import java.util.List;

import comp3350.nibblepad.business.AccessFoods;
import comp3350.nibblepad.objects.Food;
import comp3350.nibblepad.persistence.hsqldb.FoodPersistenceHSQLDB;
import comp3350.nibblepad.tests.utils.TestUtils;

public class AccessFoodsIT extends TestCase {
    private AccessFoods foodsAccessor;
    private Food newFood1;
    private Food newFood2;
    private Food newFood3;
    private Food newFood4;

    public void setUp() throws IOException {
        foodsAccessor = new AccessFoods(new FoodPersistenceHSQLDB(TestUtils.getTmpDB().getAbsolutePath()));
        newFood1 = new Food(1000, "Pizza");
        newFood2 = new Food(1001, "Pasta", "tasty");
        newFood3 = new Food(1002, "Croissant");
        newFood4 = new Food(1003);
    }

    public void testAccessFoodsNotNull() {
        assertNotNull(foodsAccessor);
    }

    public void testAccessFoodsInsert() {
        Food insertFoodResult1 = foodsAccessor.insertFood(newFood1);
        assertSame(insertFoodResult1, newFood1);

        Food insertFoodResult2 = foodsAccessor.insertFood(newFood2);
        assertSame(insertFoodResult2, newFood2);

        Food insertFoodResult3 = foodsAccessor.insertFood(newFood3);
        assertSame(insertFoodResult3, newFood3);
    }

    public void testAccessFoodsGet() {
        foodsAccessor.insertFood(newFood1);
        foodsAccessor.insertFood(newFood2);
        foodsAccessor.insertFood(newFood3);

        List<Food> foods = foodsAccessor.getFoods();
        assertTrue(foods.contains(newFood1));
        assertTrue(foods.contains(newFood2));
        assertTrue(foods.contains(newFood3));
    }

    public void testAccessFoodsGetSequential() {
        List<Food> foods = foodsAccessor.getFoods();

        for (Food food: foods) {
            assertSame(foodsAccessor.getSequential(), food);
        }
    }

    public void testAccessFoodsUpdate() {
        int foodId = newFood1.getFoodID();
        String newFoodName = "Ghorme sabzi";

        foodsAccessor.insertFood(newFood1);
        newFood1.setFoodName(newFoodName);
        foodsAccessor.updateFood(newFood1);

        boolean updatedFoodMatch = false;
        List<Food> foods = foodsAccessor.getFoods();
        for (Food food: foods) {
            if (food.getFoodID() == foodId && food.getFoodName().equals(newFoodName)) {
                updatedFoodMatch = true;
            }
        }

        assertTrue(updatedFoodMatch);
    }

    public void testAccessFoodsDelete() {
        Food testFood = new Food(30, "Poutine");

        List<Food> foodsBeforeInsert = foodsAccessor.getFoods();
        assertFalse(foodsBeforeInsert.contains(testFood));

        foodsAccessor.insertFood(testFood);

        List<Food> foodsAfterInsert = foodsAccessor.getFoods();
        assertTrue(foodsAfterInsert.contains(testFood));

        foodsAccessor.deleteFood(testFood);

        List<Food> foodsAfterDelete = foodsAccessor.getFoods();
        assertFalse(foodsAfterDelete.contains(testFood));
    }

    public void tearDown() {
        List<Food> foods = foodsAccessor.getFoods();
        if (foods.contains(newFood1))
            foodsAccessor.deleteFood(newFood1);
        if (foods.contains(newFood2))
            foodsAccessor.deleteFood(newFood2);
        if (foods.contains(newFood3))
            foodsAccessor.deleteFood(newFood3);
    }
}
