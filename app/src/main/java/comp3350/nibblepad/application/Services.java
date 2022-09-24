package comp3350.nibblepad.application;

import comp3350.nibblepad.persistence.FoodPersistence;
import comp3350.nibblepad.persistence.RestaurantPersistence;
import comp3350.nibblepad.persistence.hsqldb.FoodPersistenceHSQLDB;
import comp3350.nibblepad.persistence.hsqldb.RestaurantPersistenceHSQLDB;

public class Services {
    public static final String dbName = "DB.script";

    private static FoodPersistence foodPersistence = null;
    private static RestaurantPersistence restaurantPersistence = null;
    private static String dbPath = null;

    public static synchronized FoodPersistence getFoodPersistence() {
        if (foodPersistence == null) {
            if (dbPath != null) {
                foodPersistence = new FoodPersistenceHSQLDB(dbPath);
            }
            else {
                throw new RuntimeException("Tried to access the database without setting the database path first");
            }
        }

        return foodPersistence;
    }

    public static synchronized RestaurantPersistence getRestaurantPersistence() {
        if (restaurantPersistence == null) {
            if (dbPath != null) {
                restaurantPersistence = new RestaurantPersistenceHSQLDB(dbPath);
            }
            else {
                throw new RuntimeException("Tried to access the database without setting the database path first");
            }
        }

        return restaurantPersistence;
    }

    public static synchronized void setDbPath(String settingDbPath) {
        if (dbPath == null) {
            dbPath = settingDbPath;
            // Register hsqldb driver
            try {
                Class.forName("org.hsqldb.jdbcDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (Error e) {
                e.printStackTrace();
            }
        }
        else if (!dbPath.equals(settingDbPath)) {
            throw new RuntimeException("The database path is already set. You can't change the database path after setting it.");
        }
    }

}
