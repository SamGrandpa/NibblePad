package comp3350.nibblepad.objects;

public class Restaurant
{
    private int id;
    private String name;
    private String cuisineType;
    private String address;
    private String phoneNumber;
    private String notes;


    public Restaurant(int id, String name, String cuisineType, String location, String number) {
        this.id = id;
        this.name = name;
        this.cuisineType = cuisineType;
        this.address = location;
        this.phoneNumber = number;
    }

    public Restaurant(int id, String name, String cuisineType, String location, String number, String notes) {
        this.id = id;
        this.name = name;
        this.cuisineType = cuisineType;
        this.address = location;
        this.phoneNumber = number;
        this.notes = notes;
    }

    public int getRestaurantID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    private boolean equalStrings(String str1, String str2) {
        return (str1 == null && str2 == null) ||
                (str1 != null && str2 != null && str1.equals(str2));
    }

    public boolean equals(Object other) {
        boolean result = false;

        if(other instanceof Restaurant) {
            Restaurant otherRestaurant = (Restaurant) other;

            result = equalStrings(this.getPhoneNumber(), otherRestaurant.getPhoneNumber())
                  && equalStrings(this.getCuisineType(), otherRestaurant.getCuisineType())
                  && equalStrings(this.getAddress(), otherRestaurant.getAddress())
                  && equalStrings(this.getName(), otherRestaurant.getName())
                  && equalStrings(this.getNotes(), otherRestaurant.getNotes());
        }

        return result;
    }
}
