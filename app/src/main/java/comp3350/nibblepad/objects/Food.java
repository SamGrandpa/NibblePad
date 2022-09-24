package comp3350.nibblepad.objects;

public class Food {
    private int id;
    private String foodName;
    private String notes;

    public Food(int id) {
        this.id = id;
        this.foodName = null;
    }

    public Food(int newID, String newFoodName) {
        this.id = newID;
        this.foodName = newFoodName;
    }

    public Food(int newID, String newFoodName, String newFoodNotes) {
        this.id = newID;
        this.foodName = newFoodName;
        this.notes = newFoodNotes;
    }

    public int getFoodID() {
        return id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String newFoodName) {
        this.foodName = newFoodName;
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

        if (other instanceof Food) {
            Food otherFood = (Food) other;

            result = this.getFoodID() == otherFood.getFoodID()
                     && equalStrings(this.getFoodName(), otherFood.getFoodName())
                     && equalStrings(this.getNotes(), otherFood.getNotes());
        }

        return result;
    }
}
