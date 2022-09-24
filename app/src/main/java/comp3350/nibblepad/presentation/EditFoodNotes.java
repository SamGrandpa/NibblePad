package comp3350.nibblepad.presentation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import comp3350.nibblepad.business.AccessFoods;
import comp3350.nibblepad.objects.Food;

public class EditFoodNotes extends AppCompatDialogFragment {
    private AccessFoods foodAccessor;
    private Food targetFood;
    private int foodIndex;

    public EditFoodNotes(AccessFoods foodAccessor, int foodIndex) {
        this.foodAccessor = foodAccessor;
        this.foodIndex = foodIndex;
        this.targetFood = foodAccessor.getFoods().get(foodIndex);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_food_notes, null);

        EditText editNotes = view.findViewById(R.id.note_food_edit);
        String contents = targetFood.getNotes();

        if(contents != null && contents.length() > 0)
            editNotes.setText(contents);

        builder.setView(view)
                .setTitle("Notes for '" + targetFood.getFoodName() + "'")
                .setPositiveButton("Save", (dialogInterface, i) -> {
                    String newContent = editNotes.getText().toString();

                    if (newContent.length() > 0) {
                        targetFood.setNotes(newContent);
                        foodAccessor.updateFood(targetFood);
                    }
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        return builder.create();
    }
}
