package comp3350.nibblepad.presentation;

import static comp3350.nibblepad.presentation.Notifications.createNotificationChannel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;


import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import comp3350.nibblepad.application.Services;

import java.util.ArrayList;
import java.util.List;

import comp3350.nibblepad.business.AccessFoods;
import comp3350.nibblepad.business.AccessRestaurants;
import comp3350.nibblepad.business.api.NibblepadAPI;
import comp3350.nibblepad.business.api.OnNibblepadAPIResponseListener;
import comp3350.nibblepad.business.api.http.NibblepadHTTPAPI;
import comp3350.nibblepad.business.RandomFood;
import comp3350.nibblepad.objects.Food;
import comp3350.nibblepad.objects.Restaurant;

public class HomeActivity extends AppCompatActivity implements DialogRestaurant.RestaurantDialogListener, DialogFood.FoodDialogListener {

    private AccessRestaurants restaurantAccessor;
    private AccessFoods foodAccessor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        createNotificationChannel(this);
        copyDatabaseToDevice();

        restaurantAccessor = new AccessRestaurants();
        foodAccessor = new AccessFoods();

        NavigationBarView navigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new FoodsFragment()).commit();
        navigationView.setSelectedItemId(R.id.nav_foods);

        navigationView.setOnItemSelectedListener(item -> {

            Fragment fragment = null;
            switch (item.getItemId()) {

                case R.id.nav_foods:
                    fragment = new FoodsFragment();
                    break;

                case R.id.nav_restaurants:
                    fragment = new RestaurantsFragment();
                    break;

                case R.id.nav_about:
                    fragment = new AboutFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.body_container, fragment).commit();
            return true;
        });


        // Send a reminder notification to try out new foods/restaurant after 10 seconds
        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                // required to synchronise to an object so it won't occur before
                // the previous callback is even if nothing to display in this case
            }

            public void onFinish() {
                sendReminderNotification();
            }
        }.start();
    }

    public void sendReminderNotification() {
        Context appContext = this;
        NibblepadAPI httpAPI = new NibblepadHTTPAPI();

        RandomFood.getRandomFood(appContext, httpAPI, new OnNibblepadAPIResponseListener() {
            @Override
            public void onResponse(String randomFoodName) {
                Notifications.setNotificationTapAction(appContext, "Maybe you can try this new food: " + randomFoodName);
            }

            @Override
            public void onError(String error) {
                // Connection error/Offline phone
                // Just show the user a generic reminder message to write down new foods and restaurants
                Notifications.setNotificationTapAction(appContext, "Write down that new food or restaurant you tried!");
            }
        });
    }



    //Returns a list of strings which contains names of both foods and restaurants in the database
    private String ListCombined() {
        StringBuilder builder = new StringBuilder();
        String result;
        String delim = " ";

        //Get the actual data from database
        List<String> combined = new ArrayList<>();
        List<Food> foods = foodAccessor.getFoods();
        List<Restaurant> restaurants = restaurantAccessor.getRestaurants();

        combined.add("Hey! Checkout the restaurants and foods I've tried!\n\n");

        combined.add("Restaurants:\n\n");
        for (Restaurant restaurant: restaurants) {
            combined.add(restaurant.getName()+"\n");
        }

        combined.add("\nFoods:\n\n");
        for (Food food: foods) {
            combined.add(food.getFoodName()+"\n");
        }

        // Convert StringBuilder to a String
        int i = 0;
        while (i < combined.size()) {
            builder.append(combined.get(i));
            builder.append(delim);
            i++;
        }
        result = builder.toString();

        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.option_share:
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT,"Hey! Checkout the restaurants and foods I've tried!");
                    String data = ListCombined();
                    i.putExtra(Intent.EXTRA_TEXT,data);
                    startActivity(Intent.createChooser(i,"Share With"));
                }
                catch (Exception e) {
                    Toast.makeText(this,"Unable to Share.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    private void copyDatabaseToDevice() {
        String[] assetNames;
        Context context = getApplicationContext();
        String DB_PATH = "db";
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();
        String dbPath;

        try {

            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            dbPath = dataDirectory.toString() + "/" + Services.dbName;
            Log.d("dbPath", dbPath);
            Services.setDbPath(dbPath);

        }
        catch (final IOException ioe) {
            throw new RuntimeException("An error occurred while copying over the assets");
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }

    @Override
    public void getRestaurantFields(String resName, String resCuisine, String resAddress, String resNumber, List<Restaurant> allRestaurants) {
        int newRestaurantId = 1;

        if (restaurantAccessor.getRestaurants().size() > 0) {
            newRestaurantId = restaurantAccessor.getRestaurants().get(restaurantAccessor.getRestaurants().size() - 1).getRestaurantID() + 1;
        }

        Restaurant toAdd = new Restaurant(newRestaurantId, resName, resCuisine, resAddress, resNumber);
        allRestaurants.add(toAdd);
        restaurantAccessor.insertRestaurant(toAdd);
    }

    @Override
    public void getFoodFields(String foodName, List<Food> allFoods) {
        int newFoodId = 1;

        if (foodAccessor.getFoods().size() > 0) {
            newFoodId = foodAccessor.getFoods().get(foodAccessor.getFoods().size() - 1).getFoodID() + 1;
        }

        Food toAdd = new Food(newFoodId, foodName);
        allFoods.add(toAdd);
        foodAccessor.insertFood(toAdd);

    }
}
