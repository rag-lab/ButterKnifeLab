package com.example.rodrigoaugusto.butterknife;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipes implements Parcelable {



    //@Serialize
    private String id;
    private String servings;
    private String name;
    private String image;
    private Steps[] steps;
    private Ingredients[] ingredients;

    public Recipes(String id, String servings, String name, String image, Steps[] steps, Ingredients[] ingredients) {
        this.id = id;
        this.servings = servings;
        this.name = name;
        this.image = image;
        this.steps = steps;
        this.ingredients = ingredients;
    }

    public Ingredients[] getIngredients ()
    {
        return ingredients;
    }

    public void setIngredients (Ingredients[] ingredients)
    {
        this.ingredients = ingredients;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getServings ()
    {
        return servings;
    }

    public void setServings (String servings)
    {
        this.servings = servings;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public Steps[] getSteps ()
    {
        return steps;
    }

    public void setSteps (Steps[] steps)
    {
        this.steps = steps;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ingredients = "+ingredients+", id = "+id+", servings = "+servings+", name = "+name+", image = "+image+", steps = "+steps+"]";
    }


    //
    //PARCELABLE SHIT
    //
    protected Recipes(Parcel in) {
        id = in.readString();
        servings = in.readString();
        name = in.readString();
        image = in.readString();
        steps = in.createTypedArray(Steps.CREATOR);
        ingredients = in.createTypedArray(Ingredients.CREATOR);
    }


    public static final Creator<Recipes> CREATOR = new Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel in) {
            return new Recipes(in);
        }

        @Override
        public Recipes[] newArray(int size) {
            return new Recipes[size];
        }

    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(servings);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeTypedArray(steps, 0);
        dest.writeTypedArray(ingredients, 0);
    }
    //
    //END PARCELABLE SHIT
    //



}
