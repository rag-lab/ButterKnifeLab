package com.example.rodrigoaugusto.butterknife;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Ingredients_activity extends AppCompatActivity {

    Recipes recipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        //get the data (recipe class) from the parent activity
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("recipe");

        setTitle(recipe.getName() + " - Ingredients");


        FragmentIngredients mFragmentListIngredients = new FragmentIngredients();

        //send data to fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable("Recipe", recipe);
        mFragmentListIngredients.setArguments(bundle);

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.gridIngredients, mFragmentListIngredients).commit();


    }
}
