package comp3350.nibblepad.presentation;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import comp3350.nibblepad.business.AccessFoods;
import comp3350.nibblepad.objects.Food;

public class FoodsFragment extends Fragment {
    private AccessFoods foodAccessor;

    public FoodsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.foodAccessor = new AccessFoods();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foods, container, false);

        ListView listView = view.findViewById(R.id.foods_list);

        List<Food> foods = foodAccessor.getFoods();

        List<String> foodNames = new ArrayList<>();
        for (Food food: foods) {
            foodNames.add(food.getFoodName());
        }

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.list_row,
                foodNames
        );

        listView.setAdapter(listViewAdapter);

        listView.setOnItemLongClickListener((adapterView, view12, itemIndex, l) -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                    .setTitle("Do You want to remove " + foodNames.get(itemIndex) + " from list?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        // Remove from DB
                        Food removingFood = foods.get(itemIndex);
                        foodAccessor.deleteFood(removingFood);

                        // Remove from UI
                        foodNames.remove(itemIndex);
                        listViewAdapter.notifyDataSetChanged();
                    }).setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        });

        listView.setOnItemClickListener((adapterView, view1, itemIndex, l) -> {
            DialogFoodNotes foodNotesDialog = new DialogFoodNotes(foodAccessor, itemIndex);
            foodNotesDialog.show(getParentFragmentManager(), "food dialog notes");
        });

        FloatingActionButton add_restaurant_button = view.findViewById(R.id.add_food);

        add_restaurant_button.setOnClickListener(view13 -> {
            DialogFood foodDialog = new DialogFood(foods, foodNames, listViewAdapter);
            foodDialog.show(getParentFragmentManager(), "food dialog");
        });

        return view;
    }

}

