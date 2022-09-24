package comp3350.nibblepad.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.nibblepad.objects.Restaurant;
import comp3350.nibblepad.persistence.RestaurantPersistence;

public class RestaurantPersistenceHSQLDB implements RestaurantPersistence {

    private final String dbPath;

    public RestaurantPersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connection() throws SQLException {
        String finalDBPath = dbPath.substring(0, dbPath.indexOf(".script"));
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:" + finalDBPath + ";shutdown=true", "sa", "");
        return connection;
    }

    private Restaurant fromResultSet(final ResultSet rs) throws SQLException {
        final int restaurantID = rs.getInt("RESTAURANTID");
        final String restaurantName = rs.getString("name");
        final String restaurantCuisineType = rs.getString("cuisinetype");
        final String restaurantAddress = rs.getString("address");
        final String restaurantPhoneNumber = rs.getString("phonenumber");
        final String restaurantNotes = rs.getString("notes");
        return new Restaurant(restaurantID, restaurantName, restaurantCuisineType, restaurantAddress, restaurantPhoneNumber, restaurantNotes);
    }

    @Override
    public List<Restaurant> getRestaurantSequential() {
        final List<Restaurant> restaurants = new ArrayList<>();

        try (final Connection c = connection()) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM RESTAURANTS");
            while (rs.next()) {
                final Restaurant restaurant = fromResultSet(rs);

                restaurants.add(restaurant);
            }
            rs.close();
            st.close();

            return restaurants;
        }
        catch (final SQLException e) {
            throw new Error("Failed to open the persistence");
        }
    }

    @Override
    public Restaurant insertRestaurant(Restaurant currentRestaurant) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("INSERT INTO RESTAURANTS VALUES(?, ?, ?, ?, ?, ?)");
            st.setInt(1, currentRestaurant.getRestaurantID());
            st.setString(2, currentRestaurant.getName());
            st.setString(3, currentRestaurant.getCuisineType());
            st.setString(4, currentRestaurant.getAddress());
            st.setString(5, currentRestaurant.getPhoneNumber());
            st.setString(6, currentRestaurant.getNotes());


            st.executeUpdate();

            return currentRestaurant;
        } catch (final SQLException e) {
            throw new Error("Failed to open the persistence");
        }
    }

    @Override
    public Restaurant updateRestaurant(Restaurant currentRestaurant) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("UPDATE RESTAURANTS SET name = ?, cuisinetype = ?, address = ?, phonenumber = ?, notes = ? WHERE RESTAURANTID = ?");
            st.setString(1, currentRestaurant.getName());
            st.setString(2, currentRestaurant.getCuisineType());
            st.setString(3, currentRestaurant.getAddress());
            st.setString(4, currentRestaurant.getPhoneNumber());
            st.setString(5, currentRestaurant.getNotes());
            st.setInt(6, currentRestaurant.getRestaurantID());

            st.executeUpdate();

            return currentRestaurant;
        } catch (final SQLException e) {
            throw new Error("Failed to open the persistence");
        }
    }

    @Override
    public void deleteRestaurant(Restaurant currentRestaurant) {

        try (final Connection c = connection()) {
            final PreparedStatement st = c.prepareStatement("DELETE FROM RESTAURANTS WHERE RESTAURANTID = ?");
            st.setInt(1, currentRestaurant.getRestaurantID());
            st.executeUpdate();
        } catch (final SQLException e) {
            throw new Error("Failed to open the persistence");
        }
    }
}
