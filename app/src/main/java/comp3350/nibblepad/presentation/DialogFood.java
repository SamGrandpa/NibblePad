package comp3350.nibblepad.presentation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.List;

import comp3350.nibblepad.objects.Food;

public class DialogFood extends AppCompatDialogFragment {
    private FoodDialogListener listener;
    private EditText editFoodName;

    private List<Food> foodItems;
    private List<String> foodNames;
    private ArrayAdapter<String> foodAdapter;

    public DialogFood(List<Food> foods, List<String> foodsStr, ArrayAdapter<String> listViewAdapter) {
        this.foodItems = foods;
        this.foodNames = foodsStr;
        this.foodAdapter = listViewAdapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_food_layout, null);

        builder.setView(view)
                .setTitle("Add food info")
                .setPositiveButton("Confirm", (dialogInterface, i) -> {
                    String name = editFoodName.getText().toString();

                    foodNames.add(name);
                    foodAdapter.notifyDataSetChanged();

                    listener.getFoodFields(name, foodItems);
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        editFoodName = view.findViewById(R.id.edit_food_name);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (FoodDialogListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement FoodDialogListener");
        }
    }

    public interface FoodDialogListener {
        void getFoodFields(String foodName, List<Food> allFoods);
    }
}

