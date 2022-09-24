package comp3350.nibblepad.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.nibblepad.objects.Food;
import comp3350.nibblepad.persistence.FoodPersistence;

public class FoodPersistenceHSQLDB implements FoodPersistence {

    private final String dbPath;

    public FoodPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        String finalDBPath = dbPath.substring(0, dbPath.indexOf(".script"));
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:" + finalDBPath + ";shutdown=true", "sa", "");
        return connection;
    }

    private Food fromResultSet(final ResultSet rs) throws SQLException {
        final int foodID = rs.getInt("FOODID");
        final String foodName = rs.getString("name");
        final String foodNotes = rs.getString("notes");
        return new Food(foodID, foodName, foodNotes);
    }

    @Override
    public List<Food> getFoodSequential() {
        final List<Food> foods = new ArrayList<>();

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM FOODS");
            while (rs.next()) {
                final Food food = fromResultSet(rs);

                foods.add(food);
            }
            rs.close();
            st.close();

            return foods;
        }
        catch (final SQLException e) {
            throw new Error("Failed to open the persistence");
        }
    }

    @Override
    public Food insertFood(Food currentFood) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO FOODS VALUES(?, ?, ?)");
            st.setInt(1, currentFood.getFoodID());
            st.setString(2, currentFood.getFoodName());
            st.setString(3, currentFood.getNotes());

            st.executeUpdate();

            return currentFood;
        }
        catch (final SQLException e) {
            throw new Error("Failed to open the persistence");
        }
    }

    @Override
    public Food updateFood(Food currentFood) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE FOODS SET name = ?, notes = ? WHERE FOODID = ?");
            st.setString(1, currentFood.getFoodName());
            st.setString(2, currentFood.getNotes());
            st.setInt(3, currentFood.getFoodID());

            st.executeUpdate();

            return currentFood;
        }
        catch (final SQLException e) {
            throw new Error("Failed to open the persistence");
        }
    }

    @Override
    public void deleteFood(Food currentFood) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM FOODS WHERE FOODID = ?");
            st.setInt(1, currentFood.getFoodID());
            st.executeUpdate();
        }
        catch (final SQLException e) {
            throw new Error("Failed to open the persistence");
        }
    }
}
