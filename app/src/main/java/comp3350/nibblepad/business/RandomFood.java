package comp3350.nibblepad.business;

import android.content.Context;

import comp3350.nibblepad.business.api.NibblepadAPI;
import comp3350.nibblepad.business.api.OnNibblepadAPIResponseListener;

public class RandomFood {
    public static void getRandomFood(Context context, NibblepadAPI api, OnNibblepadAPIResponseListener listener) {
        api.getRandomFoodName(context, new OnNibblepadAPIResponseListener() {
            @Override
            public void onResponse(String response) {
                listener.onResponse(response);
            }

            @Override
            public void onError(String error) {
                listener.onError(error);
            }
        });
    }
}
