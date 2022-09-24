package comp3350.nibblepad.tests.objects;

import comp3350.nibblepad.objects.Restaurant;

import junit.framework.TestCase;

public class RestaurantTest  extends TestCase
{
    public void testRestaurantGetters() {
        Restaurant restaurant;

        restaurant = new Restaurant(1, "McDonald's", "American", "1725 Kenaston Blvd", "+1 (204) 949-5128", "Huh");

        assertEquals("The restaurant name should be: McDonald's", "McDonald's", restaurant.getName());

        assertEquals("The restaurant cuisine should be: American", "American", restaurant.getCuisineType());

        assertEquals("The restaurant address should be: 1725 Kenaston Blvd", "1725 Kenaston Blvd", restaurant.getAddress());

        assertEquals("The restaurant phone number should be: +1 (204) 949-5128", "+1 (204) 949-5128", restaurant.getPhoneNumber());

        assertEquals("The restaurant note should be: Huh", "Huh", restaurant.getNotes());
    }

    public void testRestaurantSetters() {
        String newName = "Degrees",
               newCuisineType = "blah",
               newAddress = "30 Maclean Crescent",
               newPhoneNumber = "+15552048",
               noteNote = "Huh";

        Restaurant restaurant = new Restaurant(1, "McDonald's", "American", "1725 Kenaston Blvd", "+1 (204) 949-5128", "Ew");

        assertNotSame(restaurant.getName(), newName);
        restaurant.setName(newName);
        assertSame(restaurant.getName(), newName);

        assertNotSame(restaurant.getCuisineType(), newCuisineType);
        restaurant.setCuisineType(newCuisineType);
        assertSame(restaurant.getCuisineType(), newCuisineType);

        assertNotSame(restaurant.getAddress(), newAddress);
        restaurant.setAddress(newAddress);
        assertSame(restaurant.getAddress(), newAddress);

        assertNotSame(restaurant.getPhoneNumber(), newPhoneNumber);
        restaurant.setPhoneNumber(newPhoneNumber);
        assertSame(restaurant.getPhoneNumber(), newPhoneNumber);

        assertNotSame(restaurant.getNotes(), noteNote);
        restaurant.setNotes(noteNote);
        assertSame(restaurant.getNotes(), noteNote);
    }

    public void testRestaurantEquals() {
        // restaurants to compare
        Restaurant restaurant1;
        Restaurant restaurant2;


        restaurant1 = new Restaurant(1, "res1", "cus1", "loc1", "num1", "Good food");
        restaurant2 = new Restaurant(2, "res2", "cus2", "loc2", "num2", "Ok food");

        assertFalse("Different restaurants should not be equal", restaurant1.equals(restaurant2));

        restaurant1 = new Restaurant(2, "res2", "cus2", "loc2", "num2", "Ok food");

        assertTrue("Identical restaurants should be equal", restaurant1.equals(restaurant2));

        restaurant1 = new Restaurant(1, "res1", "cus1", null, "num1", "Alright");

        assertFalse("If one restaurant has no location, assume the restaurants are not equal", restaurant1.equals(restaurant2));

        restaurant1 = new Restaurant(1, "res1", "cus1", "loc1", "num1", "Noting");
        restaurant2 = new Restaurant(2, "res2", "cus2", null, "num2", "Wowzah");

        assertFalse("If one restaurant has no location, assume the restaurants are not equal", restaurant1.equals(restaurant2));

        restaurant1 = new Restaurant(2, "res2", "cus2", null, "num2", "Wowzah");

        assertTrue("if neither restaurant has a location, assume they are equal", restaurant1.equals(restaurant2));
    }
}
