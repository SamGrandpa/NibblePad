package comp3350.nibblepad.presentation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import comp3350.nibblepad.business.AccessFoods;
import comp3350.nibblepad.objects.Food;

public class DialogFoodNotes extends AppCompatDialogFragment {
    private AccessFoods foodAccessor;
    private Food targetFood;
    private int foodIndex;

    public DialogFoodNotes(AccessFoods foodAccessor, int foodIndex) {
        this.foodAccessor = foodAccessor;
        this.foodIndex = foodIndex;
        this.targetFood = foodAccessor.getFoods().get(foodIndex);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.notes_food_layout, null);

        TextView toDisplay = view.findViewById(R.id.note_food_text);
        String contents = targetFood.getNotes();

        if(contents != null && contents.length() > 0) {
            toDisplay.setText(contents);
        }

        builder.setView(view)
                .setTitle("Notes for '" + targetFood.getFoodName() + "'")
                .setPositiveButton("Edit", (dialogInterface, i) -> {
                    EditFoodNotes foodEditNotes = new EditFoodNotes(foodAccessor, foodIndex);
                    foodEditNotes.show(getParentFragmentManager(), "edit food notes");
                })
                .setNegativeButton("Exit", (dialogInterface, i) -> dialogInterface.dismiss());

        return builder.create();
    }
}

