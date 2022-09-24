package comp3350.nibblepad.presentation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import comp3350.nibblepad.business.AccessRestaurants;
import comp3350.nibblepad.objects.Restaurant;

public class RestaurantsFragment extends Fragment{
    private AccessRestaurants restaurantAccessor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.restaurantAccessor = new AccessRestaurants();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurants, container, false);

        ListView listView = view.findViewById(R.id.restaurants_list);

        List<Restaurant> restaurants = restaurantAccessor.getRestaurants();

        List<String> restaurantNames = new ArrayList<>();
        for (Restaurant restaurant: restaurants) {
            restaurantNames.add(restaurant.getName());
        }

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.list_row,
                restaurantNames
        );

        listView.setAdapter(listViewAdapter);

        listView.setOnItemLongClickListener((adapterView, view13, itemIndex, l) -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                    .setTitle("Do You want to remove " + restaurantNames.get(itemIndex) + " from list?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        // Remove from DB
                        Restaurant removingRestaurant = restaurants.get(itemIndex);
                        restaurantAccessor.deleteRestaurant(removingRestaurant);

                        // Remove from UI
                        restaurantNames.remove(itemIndex);
                        listViewAdapter.notifyDataSetChanged();
                    }).setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        });

        listView.setOnItemClickListener((adapterView, view12, itemIndex, l) -> {
            DialogRestaurantNotes restaurantNotesDialog = new DialogRestaurantNotes(restaurantAccessor, itemIndex);
            restaurantNotesDialog.show(getParentFragmentManager(), "restaurant dialog notes");
        });

        FloatingActionButton add_restaurant_button = view.findViewById(R.id.add_restaurant);

        add_restaurant_button.setOnClickListener(view1 -> {
            DialogRestaurant restaurantDialog = new DialogRestaurant(restaurants, restaurantNames, listViewAdapter);
            restaurantDialog.show(getParentFragmentManager(), "restaurant dialog");
        });

        return view;
    }
}

