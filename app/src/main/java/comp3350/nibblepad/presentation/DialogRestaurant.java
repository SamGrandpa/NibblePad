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

import comp3350.nibblepad.objects.Restaurant;

public class DialogRestaurant extends AppCompatDialogFragment {
    private RestaurantDialogListener listener;
    private EditText editRestaurantName, editCuisineType, editAddress, editPhoneNumber;

    private List<Restaurant> restaurantItems;
    private List<String> restaurantNames;
    private ArrayAdapter<String> restaurantAdapter;

    public DialogRestaurant(List<Restaurant> restaurants, List<String> restaurantNames, ArrayAdapter<String> listViewAdapter) {
        this.restaurantItems = restaurants;
        this.restaurantNames = restaurantNames;
        this.restaurantAdapter = listViewAdapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_restaurant_layout, null);

        builder.setView(view)
                .setTitle("Add restaurant info")
                .setPositiveButton("Confirm", (dialogInterface, i) -> {
                    String name = editRestaurantName.getText().toString();
                    String cuisine = editCuisineType.getText().toString();
                    String address = editAddress.getText().toString();
                    String number = editPhoneNumber.getText().toString();

                    restaurantNames.add(name);
                    restaurantAdapter.notifyDataSetChanged();

                    listener.getRestaurantFields(name, cuisine, address, number, restaurantItems);
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        editRestaurantName = view.findViewById(R.id.edit_restaurant_name);
        editCuisineType = view.findViewById(R.id.edit_cuisine_type);
        editAddress = view.findViewById(R.id.edit_address);
        editPhoneNumber = view.findViewById(R.id.edit_phone_number);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (RestaurantDialogListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context + " must implement RestaurantDialogListener");
        }
    }

    public interface RestaurantDialogListener {
        void getRestaurantFields(String resName, String resCuisine, String resAddress, String resNumber, List<Restaurant> allRestaurants);
    }
}
