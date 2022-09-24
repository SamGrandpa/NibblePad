package comp3350.nibblepad.business.api.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import comp3350.nibblepad.business.api.NibblepadAPI;
import comp3350.nibblepad.business.api.OnNibblepadAPIResponseListener;

public class NibblepadHTTPAPI implements NibblepadAPI {
    private static final String API_URL = "https://manzik.com/nibblepad-api/";
    private static final String TAG = "nibblepad-api";

    // https://manzik.com/nibblepad-api/random-food.php
    public void getRandomFoodName(Context context, OnNibblepadAPIResponseListener apiListener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String requestUrl = API_URL + "random-food.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl,
                response -> {
                    apiListener.onResponse(response);
                },
                error -> {
                    String errorStr = error.toString();
                    Log.e(TAG, errorStr);
                    apiListener.onError(errorStr);
                });

        queue.add(stringRequest);
    }
}
