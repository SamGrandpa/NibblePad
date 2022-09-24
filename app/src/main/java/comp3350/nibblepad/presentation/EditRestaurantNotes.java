package comp3350.nibblepad.presentation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import comp3350.nibblepad.business.AccessRestaurants;
import comp3350.nibblepad.objects.Restaurant;

public class EditRestaurantNotes extends AppCompatDialogFragment {
    private AccessRestaurants restaurantsAccessor;
    private Restaurant targetRestaurant;
    private int restaurantIndex;

    public EditRestaurantNotes(AccessRestaurants restaurantsAccessor, int restaurantIndex) {
        this.restaurantsAccessor = restaurantsAccessor;
        this.restaurantIndex = restaurantIndex;
        this.targetRestaurant = restaurantsAccessor.getRestaurants().get(restaurantIndex);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_restaurant_notes, null);

        EditText editNotes = view.findViewById(R.id.note_restaurant_edit);
        String contents = targetRestaurant.getNotes();

        if(contents != null && contents.length() > 0) {
            editNotes.setText(contents);
        }

        builder.setView(view)
                .setTitle("Notes for '" + targetRestaurant.getName() + "'")
                .setPositiveButton("Save", (dialogInterface, i) -> {
                    String newContent = editNotes.getText().toString();

                    if(newContent.length() > 0) {
                        targetRestaurant.setNotes(newContent);
                        restaurantsAccessor.updateRestaurant(targetRestaurant);
                    }
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        return builder.create();
    }
}

