package comp3350.nibblepad.utils;

import android.view.View;
import android.widget.ListView;

import androidx.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class AcceptanceTestUtils {
    public static Matcher<Object> withValue(final String value) {
        return new BoundedMatcher<Object, View>(View.class) {
            @Override public void describeTo(Description description) {
                description.appendText("has value " + value);
            }
            @Override public boolean matchesSafely(final View view) {
                boolean matchFound = false;
                ListView listView = (ListView) view;
                int listViewSize = listView.getCount();

                for (int i = 0; i < listViewSize && !matchFound; i++) {
                    String  rowContent = (String) listView.getItemAtPosition(i);
                    if (rowContent.equals(value)) {
                        matchFound = true;
                    }
                }

                return matchFound;
            }
        };
    }
    public static Matcher<View> withListSize(final int size) {
        return new TypeSafeMatcher<View> () {
            @Override public boolean matchesSafely(final View view) {
                ListView listView = (ListView) view;
                return listView.getCount() == size;
            }

            @Override public void describeTo(final Description description) {
                description.appendText("ListView should have " + size + " items");
            }
        };
    }

}
