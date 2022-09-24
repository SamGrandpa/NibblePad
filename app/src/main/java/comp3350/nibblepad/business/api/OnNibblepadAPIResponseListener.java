package comp3350.nibblepad.business.api;

public interface OnNibblepadAPIResponseListener {
    public void onResponse(String response);
    public void onError(String error);
}
