package comp3350.nibblepad.business.api;

import android.content.Context;

public interface NibblepadAPI {
    // https://manzik.com/nibblepad-api/random-food.php
    public void getRandomFoodName(Context context, OnNibblepadAPIResponseListener apiListener);
}
