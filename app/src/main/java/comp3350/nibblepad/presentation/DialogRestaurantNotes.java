package comp3350.nibblepad.presentation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import comp3350.nibblepad.business.AccessRestaurants;
import comp3350.nibblepad.objects.Restaurant;

public class DialogRestaurantNotes extends AppCompatDialogFragment {
    private AccessRestaurants restaurantsAccessor;
    private Restaurant targetRestaurant;
    private int restaurantIndex;

    public DialogRestaurantNotes(AccessRestaurants restaurantsAccessor, int restaurantIndex) {
        this.restaurantsAccessor = restaurantsAccessor;
        this.restaurantIndex = restaurantIndex;
        this.targetRestaurant = restaurantsAccessor.getRestaurants().get(restaurantIndex);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.notes_restaurant_layout, null);

        TextView toDisplay = view.findViewById(R.id.note_restaurant_text);
        String contents = targetRestaurant.getNotes();

        if(contents != null && contents.length() > 0) {
            toDisplay.setText(contents);
        }

        builder.setView(view)
                .setTitle("Notes for '" + targetRestaurant.getName() + "'")
                .setPositiveButton("Edit", (dialogInterface, i) -> {
                    EditRestaurantNotes restaurantEditNotes = new EditRestaurantNotes(restaurantsAccessor, restaurantIndex);
                    restaurantEditNotes.show(getParentFragmentManager(), "edit restaurant notes");
                })
                .setNegativeButton("Exit", (dialogInterface, i) -> dialogInterface.dismiss());

        return builder.create();
    }
}

