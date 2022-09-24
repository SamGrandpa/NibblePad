package comp3350.nibblepad.tests.business;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.content.Context;

import junit.framework.TestCase;

import org.mockito.Mockito;

import comp3350.nibblepad.business.RandomFood;
import comp3350.nibblepad.business.api.NibblepadAPI;
import comp3350.nibblepad.business.api.OnNibblepadAPIResponseListener;

public class RandomFoodTest  extends TestCase {
    public void testRandomFoodSuccess() {
        String apiMockRandomFoodResponse = "Hello";

        Context context = mock(Context.class);
        NibblepadAPI api = mock(NibblepadAPI.class);
        OnNibblepadAPIResponseListener listener = mock(OnNibblepadAPIResponseListener.class);

        // Make the mock api respond with apiMockRandomFoodResponse as the random food
        doAnswer(i -> {
            OnNibblepadAPIResponseListener callingListener = (OnNibblepadAPIResponseListener) (i.getArguments()[1]);
            callingListener.onResponse(apiMockRandomFoodResponse);
            return null;
        }).when(api).getRandomFoodName(any(Context.class), any(OnNibblepadAPIResponseListener.class));


        RandomFood.getRandomFood(context, api, listener);

        // getRandomFoodName from our api should be called exactly once
        verify(api, times(1)).getRandomFoodName(isNotNull(Context.class), isNotNull(OnNibblepadAPIResponseListener.class));

        // Response listener on our listener should be called exactly once with the expected value from our mock api
        Mockito.verify(listener, times(1)).onResponse(apiMockRandomFoodResponse);

        // Error should not be invoked on the listener
        Mockito.verify(listener, never()).onError(any(String.class));
    }

    public void testRandomFoodError() {
        String apiMockRandomErrorMessage = "Oh no";

        Context context = mock(Context.class);
        NibblepadAPI api = mock(NibblepadAPI.class);
        OnNibblepadAPIResponseListener listener = mock(OnNibblepadAPIResponseListener.class);

        // Make the mock api respond with apiMockRandomFoodResponse as the error message
        doAnswer(i -> {
            OnNibblepadAPIResponseListener callingListener = (OnNibblepadAPIResponseListener) (i.getArguments()[1]);
            callingListener.onError(apiMockRandomErrorMessage);
            return null;
        }).when(api).getRandomFoodName(any(Context.class), any(OnNibblepadAPIResponseListener.class));


        RandomFood.getRandomFood(context, api, listener);

        // getRandomFoodName from our api should be called exactly once
        verify(api, times(1)).getRandomFoodName(isNotNull(Context.class), isNotNull(OnNibblepadAPIResponseListener.class));

        // Error listener on our listener should be called exactly once with the expected value from our mock api
        Mockito.verify(listener, times(1)).onError(apiMockRandomErrorMessage);

        // We shouldn't get back any successful response
        Mockito.verify(listener, never()).onResponse(any(String.class));
    }
}
